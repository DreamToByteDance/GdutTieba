package www.raven.sw.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import www.raven.sw.aop.TiebaInterceptor;

/**
 * web config
 *
 * @author Rawven
 * @date 2024/11/07
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private TiebaInterceptor tiebaInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tiebaInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/user/**","/api/files/**","/files/**");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许所有域名的跨域请求
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","http://10.21.32.192:8080") // 允许来自 localhost:3000 的请求
                .allowedMethods("GET", "POST", "PUT", "DELETE", HttpMethod.OPTIONS.name()) // 允许的 HTTP 方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true) // 是否允许携带认证信息（如 cookies）
                .maxAge(3600); // 预检请求缓存的最大时间，单位：秒
    }
}
