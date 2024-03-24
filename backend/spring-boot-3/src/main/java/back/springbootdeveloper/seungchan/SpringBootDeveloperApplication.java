package back.springbootdeveloper.seungchan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class SpringBootDeveloperApplication { // spring boot 의 main 메서드를 의미한다.

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDeveloperApplication.class, args);
  }
}