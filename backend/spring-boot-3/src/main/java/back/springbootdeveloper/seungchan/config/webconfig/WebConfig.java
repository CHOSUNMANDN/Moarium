package back.springbootdeveloper.seungchan.config.webconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${dev-base-url}")
  private String imageBaseUrl;

  @Bean
  public MessageSource validationMessageSource() {
    // MessageSource를 정의하는 곳에 추가적으로 validationMessageSource
    Locale.setDefault(Locale.KOREA); // 위치 한국으로 설정 (한국어로 에러 메세지 나오게)
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:/messages/validation/validation");
//        messageSource.setBasenames("classpath:message/security_message", "classpath:org/springframework/security/messages"); // 커스텀한 properties 파일, security properties 파일 순서대로 설정
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Override
  public Validator getValidator() {
    // getValidator bean을 추가하였다.
    // getValidator라는 녀석은 DTO에서 messageSource를 가지고 올 수 있게 해주는 역할을 해준다.
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(validationMessageSource());
    return bean;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 위 코드는 Spring의 ResourceHandlerRegistry를 사용하여 정적 리소스를 처리하고,
    // "/images/**" URL 패턴에 대한 요청을 로컬 파일 시스템의 특정 디렉토리로 매핑하는 역할을 합니다.

    // addResourceLocations 메서드에 전달되는 인자는 파일 시스템 경로를 나타내며,
    // URL 형식으로 제공해야 합니다. file:// 접두사는 이를 나타냅니다.
    // 그러나 주어진 코드에서 imageDir은 이미 파일 시스템 경로를 나타내므로 file://을 명시할 필요가 없습니다.
    // 대신에 이미지 디렉토리의 절대 경로를 그대로 사용하면 됩니다.
    registry.addResourceHandler("/static/images/**")
        .addResourceLocations(imageBaseUrl);
  }
}
