from typing import Callable

from selenium.webdriver.common.by import By

from common.utils import BlogDriver


class ManageCircle:
    driver = ""

    def __init__(self):
        self.driver = BlogDriver.driver

    # 测试创建主题圈
    def creCircle(self):
        # 点击创建主题圈
        self.driver.find_element(By.CSS_SELECTOR, "#crecir").click()
        # 填写主题圈内容
        self.driver.find_element(By.CSS_SELECTOR, "#titile").send_keys("xxxx")
        self.driver.find_element(By.CSS_SELECTOR, "#content").send_keys("xxxx")
        # 发布主题圈
        self.driver.find_element(By.CSS_SELECTOR, "submit").click()
        # 查看发布结果
        restxt = self.driver.find_element(By.CSS_SELECTOR, "body").text
        assert restxt == "主题圈正在被管理员审核"
        BlogDriver.getScreenShot()

    # 关闭主题圈
    def closeCircle(self):
        # 点击目标主题圈
        self.driver.find_element(By.CSS_SELECTOR, "#close").click()
        # 点击弹窗中的确定关闭按钮
        alert = self.driver.switch_to_alert()
        alert.accept()
        # 查看关闭效果
        count = self.driver.find_element(By.CSS_SELECTOR, "#listnumber").text
        assert count == 0

