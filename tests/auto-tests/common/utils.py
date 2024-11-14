import datetime
import os
import sys

from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager


class Driver:
    driver = ""  # 浏览器驱动器

    def __init__(self):
        options = webdriver.ChromeOptions()
        self.driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
        self.driver.implicitly_wait(2)

    def getScreenShot(self):
        # 创建屏幕截图
        # 图片文件名称：./2024-05-08-173456.png
        dirname = datetime.datetime.now().strftime("%Y-%m-%d")
        # 判断dirname文件夹是否已经存在，若不存在则创建文件夹
        # ../images/2024-05-08
        if not os.path.exists("../images/" + dirname):
            os.mkdir("../images/" + dirname)
        # 2024-05-08-173456.png
        # 图片路径:../images/调用方法-2024-05-08/2024-05-08-173456.png
        # 图片路径:../images/LoginSucTest-2024-05-08/2024-05-08-173456.png
        # 图片路径:../images/LoginFailTest-2024-05-08/2024-05-08-173456.png
        filename = sys._getframe().f_back.f_code.co_name + "-" + datetime.datetime.now().strftime(
            "%Y-%m-%d-%H%M%S") + ".png"
        self.driver.save_screenshot("../images/" + dirname + "/" + filename)


BlogDriver = Driver()
