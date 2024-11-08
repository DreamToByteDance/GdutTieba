package www.raven.sw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * gdut tieba application
 *
 * @author Rawven
 * @date 2024/11/07
 */
@SpringBootApplication
@EnableAsync
@MapperScan("www.raven.sw.dao")
public class GdutTiebaApplication {
	public static void main(String[] args) {
		SpringApplication.run(GdutTiebaApplication.class, args);
	}
}
