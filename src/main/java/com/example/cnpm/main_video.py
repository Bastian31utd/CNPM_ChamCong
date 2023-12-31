import cv2
import time
import os
import sqlite3

def connect_to_database():
    try:
        conn = sqlite3.connect('/Users/admin/IdeaProjects/CNPM_ChamCong/database.db')
        return conn
    except sqlite3.Error as e:
        print("Lỗi khi kết nối đến cơ sở dữ liệu:", e)
        return None

def insert_attendance(conn, user_id, checkin_time, datetime):
    try:
        cursor = conn.cursor()
        late = 1 if checkin_time >= "08:00:00" else 0
        cursor.execute("INSERT INTO Attendance (userID, WorkDate, CheckInTime,Late) VALUES (?,?,?,?)", (user_id, datetime,checkin_time,late))
        conn.commit()
        print("Đã thêm dữ liệu check-in vào cơ sở dữ liệu.")
    except sqlite3.Error as e:
        print("Lỗi khi thêm dữ liệu check-in:", e)
def get_user_info(conn, user_id):
    try:
        cursor = conn.cursor()
        cursor.execute("SELECT Name FROM Users WHERE UserID = ?", (user_id,))
        user_info = cursor.fetchone()
        return user_info[0]
    except sqlite3.Error as e:
        print("Lỗi khi truy vấn thông tin người dùng:", e)
        return ""
def get_current_dir():
    return os.path.dirname(os.path.abspath(__file__))

path = get_current_dir() + "\\images\\"

from simple_facerec import SimpleFacerec
sfr = SimpleFacerec()
sfr.load_encoding_images(path)

cap = cv2.VideoCapture(0)
conn = connect_to_database()
while True:
    ret, frame = cap.read()

    face_locations, face_names = sfr.detect_known_faces(frame)
    for face_loc, name in zip(face_locations, face_names):
        y1, x2, y2, x1 = face_loc[0], face_loc[1], face_loc[2], face_loc[3]

        cv2.putText(frame, name, (x1, y1 - 10), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 200), 2)
        cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 0, 200), 4)

    cv2.imshow("Camera", frame)

    key = cv2.waitKey(1)
    if key == 27:
        cv2.destroyWindow("Camera")
        checkinTime = time.time()
        pTime = time.strftime("%H:%M:%S", time.localtime(checkinTime))
        dTime = time.strftime("%Y-%m-%d", time.localtime(checkinTime))

        cnt = 0
        for rid in face_names:
            if rid == "Unknown":
                cnt += 1

        if len(face_names) == 0 or len(face_names) == cnt:
            cv2.destroyAllWindows()
            cv2.imshow("Khong phat hien khuon mat da duoc dang ky   " + pTime, frame)

            # Print to terminal
            print("Khong phat hien khuon mat nao da duoc dang ky")
        else:
            cv2.destroyAllWindows()
            cv2.imshow("Check-in thanh cong   " + pTime, frame)

            # Print to terminal
            for rid in face_names:
                if rid != "Unknown":
                    print("Check-in thanh cong: " + get_user_info(conn,rid))
                    print("Thoi gian check-in: " + pTime +" "+dTime)

            # Insert dữ liệu vào cơ sở dữ liệu
            for rid in face_names:
                if rid != "Unknown":
                    user_id = rid
                    if conn is not None:
                        insert_attendance(conn, user_id, pTime,dTime)
                        conn.close()
                    else:
                        print("Không thể kết nối đến cơ sở dữ liệu.")

        key2 = cv2.waitKey(0)
        if key2 == 27:
            cv2.destroyAllWindows()
            break

cap.release()
cv2.destroyAllWindows()
