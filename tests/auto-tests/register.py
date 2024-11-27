from selenium.webdriver.common.by import By

from common.utils import BlogDriver


# 测试注册页面

class Register:
    driver = ""
    url = ""

    def __init__(self):
        self.driver = BlogDriver.driver
        self.url = "http://192.168.47.135:8653/blog_system/blog_login.html"
        self.driver.get(self.url)
    # 点击注册按钮并截图记录注册情况
    def testRegRes(self):
        # 点击注册按钮
        self.driver.find_element(By.CSS_SELECTOR, "#submit").click()
        # 检查注册情况
        res = self.driver.find_element(By.CSS_SELECTOR, "body").text
        self.driver.getScreenShot()
        if res == "注册成功":
            return True
        else:
            return False

    # 使用有效邮箱注册
    def RegByCorEmail(self):
        # 将邮箱和密码框清空
        self.driver.find_element(By.CSS_SELECTOR, "#email").clear()
        self.driver.find_element(By.CSS_SELECTOR, "#password").clear()
        # 输入有效的邮箱和密码
        self.driver.find_element(By.CSS_SELECTOR, "#email").send_keys("")
        self.driver.find_element(By.CSS_SELECTOR, "#password").send_keys("")
        # 检查注册情况
        res = self.testRegRes()
        assert res, 'res should be True'


    # 使用无效邮箱注册
    def RegByWroEmail(self):
        # 将邮箱和密码框清空
        self.driver.find_element(By.CSS_SELECTOR, "#email").clear()
        self.driver.find_element(By.CSS_SELECTOR, "#password").clear()
        # 输入无效的邮箱和密码
        self.driver.find_element(By.CSS_SELECTOR, "#email").send_keys("")
        self.driver.find_element(By.CSS_SELECTOR, "#password").send_keys("")
        # 检查注册情况
        res = self.testRegRes()
        assert res, 'res should be True'

    # 使用有效手机号注册
    def RegByCorNumber(self):
        self.driver.find_element(By.CSS_SELECTOR, "#number").clear()
        self.driver.find_element(By.CSS_SELECTOR, "#password").clear()
        # 输入有效的手机号和密码
        self.driver.find_element(By.CSS_SELECTOR, "#number").send_keys("")
        self.driver.find_element(By.CSS_SELECTOR, "#password").send_keys("")
        # 检查注册情况
        res = self.testRegRes()
        assert res, 'res should be True'


    # 使用无效手机号注册
    def RegByWroNumber(self):
        self.driver.find_element(By.CSS_SELECTOR, "#number").clear()
        self.driver.find_element(By.CSS_SELECTOR, "#password").clear()
        # 输入无效的手机号和密码
        self.driver.find_element(By.CSS_SELECTOR, "#number").send_keys("")
        self.driver.find_element(By.CSS_SELECTOR, "#password").send_keys("")
        # 检查注册情况
        res = self.testRegRes()
        assert res, 'res should be True'

