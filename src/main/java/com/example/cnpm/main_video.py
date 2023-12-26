import cv2
import time
import os

# Get the current directory
def get_current_dir():
    return os.path.dirname(os.path.abspath(__file__))

path = get_current_dir() + "\\images\\"

# Encode faces from a folder
from simple_facerec import SimpleFacerec
sfr = SimpleFacerec()
sfr.load_encoding_images(path)

# Load camera
cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()

    # Detect faces
    face_locations, face_names = sfr.detect_known_faces(frame)
    for face_loc, name in zip(face_locations, face_names):
        y1, x2, y2, x1 = face_loc[0], face_loc[1], face_loc[2], face_loc[3]

        cv2.putText(frame, name, (x1, y1 - 10), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 200), 2)
        cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 0, 200), 4)

    # cv2.namedWindow("Frame", cv2.WINDOW_AUTOSIZE)
    # cv2.setWindowProperty("Frame", cv2.WND_PROP_TOPMOST, 1)
    cv2.imshow("Frame", frame)

    key = cv2.waitKey(1)
    if key == 27:
        cv2.destroyWindow("Frame")
        checkinTime = time.time()
        pTime = time.strftime("%H:%M:%S %d-%m-%Y", time.localtime(checkinTime))

        cnt = 0
        for rname in face_names:
            if rname == "Unknown":
                cnt += 1

        if len(face_names) == 0 or len(face_names) == cnt:
            cv2.destroyAllWindows()
            # cv2.namedWindow("Khong phat hien khuon mat da duoc dang ky   ", cv2.WINDOW_AUTOSIZE)
            # cv2.setWindowProperty("Khong phat hien khuon mat da duoc dang ky   ", cv2.WND_PROP_TOPMOST, 1)
            cv2.imshow("Khong phat hien khuon mat da duoc dang ky   " + pTime, frame)

            # Print to terminal
            print("Khong phat hien khuon mat nao da duoc dang ky")
        else:
            cv2.destroyAllWindows()
            # cv2.namedWindow("Check-in thanh cong   ", cv2.WINDOW_AUTOSIZE)
            # cv2.setWindowProperty("Check-in thanh cong   ", cv2.WND_PROP_TOPMOST, 1)
            cv2.imshow("Check-in thanh cong   " + pTime, frame)

            # Print to terminal
            for rname in face_names:
                print("Check-in thanh cong: " + rname)
            print("Thoi gian check-in: " + pTime)

        key2 = cv2.waitKey(0)
        if key2 == 27:
            cv2.destroyAllWindows()
            break

cap.release()
cv2.destroyAllWindows()
