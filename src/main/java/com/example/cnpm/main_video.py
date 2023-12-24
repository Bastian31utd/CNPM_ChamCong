import cv2
import time

from simple_facerec import SimpleFacerec

# Encode faces from a folder
sfr = SimpleFacerec()
# sfr.load_encoding_images("images\\")
sfr.load_encoding_images("images")

# Load Camera
cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()

    # Detect Faces
    face_locations, face_names = sfr.detect_known_faces(frame)
    for face_loc, name in zip(face_locations, face_names):
        y1, x2, y2, x1 = face_loc[0], face_loc[1], face_loc[2], face_loc[3]

        cv2.putText(frame, name, (x1, y1 - 10), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 200), 2)
        cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 0, 200), 4)

    cv2.imshow("Frame", frame)

    key = cv2.waitKey(1)
    if key == 27:
        if len(face_names) == 0:
            print("Khong phat hien khuon mat nao da duoc dang ky")
        else:
            for rname in face_names:
                print("Check-in thanh cong: " + rname)
                checkinTime = time.time()
                pTime = time.strftime("%H:%M:%S %d-%m-%Y", time.localtime(checkinTime))
                print("Thoi gian check-in: " + pTime)
        break

cap.release()
cv2.destroyAllWindows()
