package www.raven.sw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 上传文件
 *
 * @author Rawven
 * @date 2024/11/07
 */
@RestController
@RequestMapping("api/")
@ResponseBody
public class CorsController {
   @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity.ok().build();  // 返回 HTTP 200 状态，表示允许跨域
    }
}
