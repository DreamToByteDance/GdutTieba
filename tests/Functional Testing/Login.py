import time

from selenium.webdriver.common.by import By

from common.utils import BlogDriver


# 测试登陆页面

class BlogLogin:
    url = ""
    driver = ""

    def __init__(self):
        self.url = "http://192.168.47.135:8653/blog_system/blog_login.html"
        self.driver = BlogDriver.driver
        self.driver.get(self.url)

    # 用来点击登录按钮并记录登录情况
    def testRegRes(self):
        # 点击登录按钮
        self.driver.find_element(By.CSS_SELECTOR, "#submit").click()
        # 检查登录情况
        res = self.driver.find_element(By.CSS_SELECTOR, "body").text
        self.driver.getScreenShot()
        if res == "登录成功":
            return True
        else:
            return False

    # 通过手机号登录
    def LoginByNum(self):
        self.driver.find_element(By.CSS_SELECTOR, "#number").clear()
        self.driver.find_element(By.CSS_SELECTOR, "#password").clear()
        # 输入有效的手机号和密码
        self.driver.find_element(By.CSS_SELECTOR, "#number").send_keys("")
        self.driver.find_element(By.CSS_SELECTOR, "#password").send_keys("")
        # 检查登录情况
        res = self.testRegRes()
        assert res, "res should be True"

    # 通过邮箱登录
    def LoginByEmail(self):
        self.driver.find_element(By.CSS_SELECTOR, "#email").clear()
        self.driver.find_element(By.CSS_SELECTOR, "#password").clear()
        # 输入有效的手机号和密码
        self.driver.find_element(By.CSS_SELECTOR, "#email").send_keys("")
        self.driver.find_element(By.CSS_SELECTOR, "#password").send_keys("")
        # 检查登录情况
        res = self.testRegRes()
        assert res, "res should be True"
    # #密码找回功能
    # def PassRecoveryByNum(self):
    #     self.driver.find_element(By.CSS_SELECTOR,"#getpassword").click()
    #     self.driver.find_element(By.CSS_SELECTOR,"#choice").click()
    #
    #
    #
    # def PassRecoveryByNum(self):
    #     self.driver.find_element(By.CSS_SELECTOR,"#getpassword").click()
    #     self.driver.find_element(By.CSS_SELECTOR,"#choice").click()
    #     #选择找回方式
    #
    # #记住密码功能
    # def recordPass(self):



