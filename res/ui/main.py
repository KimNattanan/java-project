import cv2  as cv

imgs = {
  "home_btn.png",
  "home_btn_hover.png",
  "home_btn_active.png",
  "retry_btn.png",
  "retry_btn_hover.png",
  "retry_btn_active.png",
}

for e in imgs:
  print(1)
  img = cv.imread(e,cv.IMREAD_UNCHANGED)
  i_min = img.shape[0]
  i_max = 0
  j_min = img.shape[1]
  j_max = 0
  for i in range(img.shape[0]):
    for j in range(img.shape[1]):
      if(img[i,j,3]!=0):
        i_min = min(i_min, i)
        i_max = max(i_max, i)
        j_min = min(j_min, j)
        j_max = max(j_max, j)
  cropped_img = img[i_min:i_max+1,j_min:j_max+1]
  cv.imwrite(e,cropped_img)