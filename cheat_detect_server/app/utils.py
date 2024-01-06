import os
import cv2
import numpy as np

def calculate_color_similarity(imageA, imageB):

    print(imageA.shape, imageB.shape)
    # Convert the images to the HSV color space
    hsvA = cv2.cvtColor(imageA, cv2.COLOR_BGR2HSV)
    hsvB = cv2.cvtColor(imageB, cv2.COLOR_BGR2HSV)

    # Compute the 3D color histogram for the HSV images and
    # normalize the histograms
    histA = cv2.calcHist([hsvA], [0, 1, 2], None, [8, 8, 8], [0, 180, 0, 256, 0, 256])
    histA = cv2.normalize(histA, histA).flatten()

    histB = cv2.calcHist([hsvB], [0, 1, 2], None, [8, 8, 8], [0, 180, 0, 256, 0, 256])
    histB = cv2.normalize(histB, histB).flatten()

    # Compute the correlation between the two histograms
    d = cv2.compareHist(histA, histB, cv2.HISTCMP_CORREL)

    # Return the correlation score
    return d

def process_image(image):
    image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    image = cv2.GaussianBlur(image, (3, 3), 0)
    image = cv2.equalizeHist(image)
    return image

def check_cheat_util(image):
    bins = 26
    bin_size = 256 // bins
    # Iter each folder in icons folder
    for folder in os.listdir('app/icons'):
        # Iter each file in icons folder
        for file in os.listdir(f'app/icons/{folder}'):
            # print(folder)
            # Check if file is image
            print(file)
            if file.endswith('.png') or file.endswith('.jpg'):
                # Compare image with file
                # print(f'cheat_detect_server/app/icons/{folder}/{file}')
                try:
                    icon_color = cv2.imread(f'app/icons/{folder}/{file}')
                    screenshot_color = image
                    screenshot = process_image(screenshot_color)
                    icon = process_image(icon_color)

                    # Perform template matching
                    result = cv2.matchTemplate(screenshot, icon, cv2.TM_CCOEFF_NORMED)
                    min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(result)
                    # The top-left corner of the matched area
                    top_left = max_loc

                    # Dimensions of the icon
                    w, h = icon.shape[::-1]
                    # Calculate the bottom-right corner of the matched area
                    bottom_right = (top_left[0] + w, top_left[1] + h)
                    cropped_color = screenshot_color[top_left[1]:top_left[1] + h, top_left[0]:top_left[0] + w]
                    cv2.imwrite("result/detect.png", cropped_color)
                    similarity = calculate_color_similarity(cropped_color, icon_color)
                    print("Max conf:", max_val)
                    print("Similarity: ", similarity)
                    if similarity > 0.9 or max_val > 0.9:
                        return True, folder
                except Exception as e:
                    print(e)
                    pass
    return False, ''