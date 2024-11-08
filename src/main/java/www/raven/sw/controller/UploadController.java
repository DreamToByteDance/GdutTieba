package www.raven.sw.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

/**
 * 上传文件
 *
 * @author Rawven
 * @date 2024/11/07
 */
@RestController
@RequestMapping("api/")
@ResponseBody
public class UploadController {
	/**
	 * 上传文件 返回base64的数据字符串,
	 * 若业务逻辑需要保存文件,就先从这里获取base64字符串,然后再附带为参数传递给业务接口
	 *
	 * @param file 文件
	 * @return {@link String }
	 */
	@PostMapping("/upload")
	public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
		byte[] imageBytes = file.getBytes();
		return Base64.getEncoder().encodeToString(imageBytes);
	}
}
