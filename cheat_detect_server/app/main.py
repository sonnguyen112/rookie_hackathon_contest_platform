from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from . import schemas
import base64
from PIL import Image
from io import BytesIO
from .utils import check_cheat_util
import numpy as np
import cv2

app = FastAPI()

origins = ["*"]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
async def root():
    return {"message": "Hello FastAPI"}

@app.post("/api/v1/quiz/check_cheat", status_code=200, response_model=schemas.CheckCheatResponse)
async def check_cheat(request: schemas.CheckCheatRequest):
    img_base64 = request.image
    #Remove base64 header
    img_base64 = img_base64[img_base64.index("base64,") + 7:]
    img_bytes = base64.b64decode(img_base64)
    np_data = np.frombuffer(img_bytes, np.uint8)
    img = cv2.imdecode(np_data, cv2.IMREAD_COLOR)
    is_cheat, type_cheat = check_cheat_util(img)
    return {
        "is_cheat": is_cheat,
        "type_cheat": type_cheat
    }