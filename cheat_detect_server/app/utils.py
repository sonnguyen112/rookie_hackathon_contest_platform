import pyautogui
import os
from PIL import Image

def check_cheat_util(image:Image):
    # Iter each folder in icons folder
    for folder in os.listdir('app/icons'):
        # Iter each file in icons folder
        for file in os.listdir(f'app/icons/{folder}'):
            # print(folder)
            # Check if file is image
            if file.endswith('.png') or file.endswith('.jpg'):
                # Compare image with file
                # print(f'cheat_detect_server/app/icons/{folder}/{file}')
                try:
                    bbox = pyautogui.locate(f'app/icons/{folder}/{file}', image)
                    return True, folder
                except:
                    pass
    return False, ''