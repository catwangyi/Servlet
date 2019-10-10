import cv2
import sys
import gc
from face_train import Model

def recog(path):
    # 加载模型
    model = Model()
    model.load_model(file_path='./model/ZH.face.model.h5')

    # 框住人脸的矩形边框颜色
    color = (0, 255, 0)

    # 捕获指定摄像头的实时视频流
    cap = cv2.VideoCapture(0)

    # 人脸识别分类器本地存储路径
    cascade_path = "E:\\JavaTest\\FaceID\\src\\haarcascade_frontalface_alt2.xml"

    # 循环检测识别人脸
    frame1 = cv2.imread(path)

    # 图像灰化，降低计算复杂度
    frame_gray = cv2.cvtColor(frame1, cv2.COLOR_BGR2GRAY)

    # 使用人脸识别分类器，读入分类器
    cascade = cv2.CascadeClassifier(cascade_path)

    # 利用分类器识别出哪个区域为人脸
    faceRects = cascade.detectMultiScale(frame_gray, scaleFactor=1.2, minNeighbors=3, minSize=(32, 32))
    if len(faceRects) > 0:
        for faceRect in faceRects:
            x, y, w, h = faceRect

            # 截取脸部图像提交给模型识别这是谁
            image = frame1[y - 10: y + h + 10, x - 10: x + w + 10]
            faceID = model.face_predict(image)

            # 如果是“我”
            if faceID == 0:
                result = "success"
            else:
                result = "wrong"
            return result


if __name__ == '__main__':
    path = sys.argv[1]
    result = recog(path)
    print(result)


