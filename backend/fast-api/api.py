from fastapi import FastAPI
from PIL import Image
import numpy as np
import pytesseract

app = FastAPI()

# source venv/bin/activate
# pip install fastapi
# 사용 라이브러리
# pip freeze > requirements.txt
# 사용 라이브러리 설치
# pip install -r requirements.txt
# 파이썬 시작
# uvicorn api:app --port 8000 --reload
# https://4sii.tistory.com/601
# kor.traineddata 파일 다운 https://github.com/tesseract-ocr/tessdata/
# https://whoareyouwhoami.github.io/docs/tesseract/tesseract_install.html
# sudo apt install tesseract-ocr
# Tesseract 설치
# $ brew install tesseract
#
# # Tesseract 모든 언어 설치
# $ brew install tesseract-lang
# $ brew install tesseract --all-languages
@app.get("/fast")
async def welcome() -> dict:
    # filename = 'images/시간표_테스트.jpeg'
    filename = 'images/qwe.png'
    # filename = 'images/test.png'
    # filename = 'images/ㄴㄴ.png'
    img = np.array(Image.open(filename))
    text = pytesseract.image_to_string(img, lang='kor')
    print(text)

    # # 이미지 불러오기
    # image = cv2.imread('images/Monosnap.png')
    #
    # # 이미지 전처리 (예: 이미지 이진화)
    # gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    # threshold_img = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)[1]
    #
    # # 전처리된 이미지에서 텍스트 인식
    # text = pytesseract.image_to_string(threshold_img)
    return {
            "message": text
    }