package www.raven.sw.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
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
				.excludePathPatterns("/api/user/**");
	}
  @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许的请求路径、允许的源、允许的方法等配置
        registry.addMapping("/api/**")  // 允许 /api 路径的跨域请求
                .allowedOrigins("http://10.21.32.192:8080")  // 允许来自该源的请求
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的请求方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true)  // 是否允许发送 cookie
                .maxAge(3600);  // 预检请求缓存时间，单位为秒
    }

}
