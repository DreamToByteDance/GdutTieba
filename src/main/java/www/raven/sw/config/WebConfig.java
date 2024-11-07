package www.raven.sw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

	@Autowired
	private TiebaInterceptor tiebaInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tiebaInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/api/user/**");
	}
}
