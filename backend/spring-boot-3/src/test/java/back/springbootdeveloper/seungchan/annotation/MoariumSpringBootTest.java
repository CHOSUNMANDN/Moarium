package back.springbootdeveloper.seungchan.annotation;

import back.springbootdeveloper.seungchan.extension.TestCustomExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

/**
 * MoariumSpringBootTest 애노테이션은 테스트 클래스에 적용되는 사용자 정의 Spring Boot 테스트 애노테이션입니다. 이 애노테이션은 다음 애노테이션들을
 * 결합합니다: - {@link SpringBootTest}: Spring Boot 애플리케이션 컨텍스트를 테스트하기 위한 테스트 클래스임을 나타냅니다. -
 * {@link AutoConfigureMockMvc}: MockMvc 인스턴스를 자동 구성하여 MVC 컨트롤러를 테스트하는 데 사용할 수 있도록 합니다. -
 * {@link ExtendWith}: 테스트를 확장하기 위한 사용자 정의 확장 클래스인 TestCustomExtension을 사용하도록 지정합니다.
 *
 * @see SpringBootTest
 * @see AutoConfigureMockMvc
 * @see TestCustomExtension
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.TYPE)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(TestCustomExtension.class)
public @interface MoariumSpringBootTest {

}
