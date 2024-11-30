package www.raven.sw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件接口
 * @author Rawven
 * @date 2024/11/30
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

	// 配置上传的目录
	@Value("${file.upload-dir}")
	private String uploadDir;

	/**
	 * 上传文件接口
	 *
	 * @param file 文件
	 * @return {@link String } 返回文件的 URL 地址
	 * @throws IOException  ioexception
	 */
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		// 获取文件名
		String fileName = file.getOriginalFilename();

		// 文件保存的完整路径
		File dest = new File(uploadDir + "/" + fileName);

		// 将文件保存到本地指定路径
		file.transferTo(dest);

		// 返回文件的完整 URL 路径
		return "http://10.21.32.192:8888/api/files/" + fileName;
	}

	// 通过文件名返回文件
	@GetMapping("/{fileName}")
	public ResponseEntity<Resource> getFile(@PathVariable String fileName) throws MalformedURLException {
		// 文件路径
		Path path = Paths.get(uploadDir + "/" + fileName);
		Resource resource = new UrlResource(path.toUri());

		if (resource.exists() || resource.isReadable()) {
			return ResponseEntity.ok().body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//
//    // 下载文件
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) throws IOException {
//        File file = new File(uploadDir + "/" + fileName);
//        if (!file.exists()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Path path = file.toPath();
//        byte[] content = Files.readAllBytes(path);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
//                .body(content);
//    }
//
//    // 删除文件
//    @DeleteMapping("/delete/{fileName}")
//    public String deleteFile(@PathVariable String fileName) {
//        File file = new File(uploadDir + "/" + fileName);
//        if (file.exists()) {
//            FileUtils.deleteQuietly(file);
//            return "文件删除成功: " + fileName;
//        }
//        return "文件未找到: " + fileName;
//    }
//
//    // 获取文件列表
//    @GetMapping("/list")
//    public String[] listFiles() {
//        File dir = new File(uploadDir);
//        String[] files = dir.list();
//        if (files == null) {
//            return new String[0];
//        }
//        return files;
//    }
}
