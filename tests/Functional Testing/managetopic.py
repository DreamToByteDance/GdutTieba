from selenium.webdriver.common.by import By

from common.utils import BlogDriver


class ManageTopic:
    driver = ""

    def __init__(self):
        self.driver = BlogDriver.driver

    def CreTopic(self):
        # 上传带有图片和文字的话题
        self.driver.find_element(By.CSS_SELECTOR, "#creat").click()
        self.driver.find_element(By.CSS_SELECTOR, "titile").send_keys("title")
        self.driver.find_element(By.CSS_SELECTOR, "#content-text").send_keys("content")
        self.driver.find_element(By.CSS_SELECTOR, "#picture").send_keys("//:路径")
        self.driver.find_element(By.CSS_SELECTOR, "#submit").click()
        # 验证话题上传效果
        self.driver.find_element(By.CSS_SELECTOR, "#topic").click()
        title = self.driver.find_element(By.CSS_SELECTOR, "title").text
        assert title == "title", "title should be written"
        content = self.driver.find_element(By.CSS_SELECTOR, "#content-text").send_keys("content")
        assert content == "content", "content should be written"
        image_element = self.driver.find_element_by_id("image_id")
        assert image_element is not None, "Image element should not be None"
