package back.springbootdeveloper.seungchan.extension;

import back.springbootdeveloper.seungchan.service.DatabaseCleanup;
import back.springbootdeveloper.seungchan.testutil.TestSaveEntity;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Method;

public class TestCustomExtension implements BeforeEachCallback {

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    /**
     * 이 코드는 Spring의 ApplicationContext를 사용하여 "databaseCleanup"이라는 이름의 빈을 가져와서 DatabaseCleanup 클래스의 execute() 메서드를 호출하는 것으로 보입니다.
     * 이 코드는 Spring의 테스트 컨텍스트에서 사용되는 것으로 판단됩니다. 일반적으로 테스트에서는 테스트를 실행하기 전에 데이터베이스를 정리하거나 초기화해야 할 때 사용됩니다.
     * DatabaseCleanup 클래스는 데이터베이스를 정리하고 초기화하는 작업을 담당합니다.
     * 그리고 이 코드는 해당 작업을 수행하기 위해 Spring의 ApplicationContext를 사용하여
     * DatabaseCleanup 빈을 가져온 다음 execute() 메서드를 호출하는 것입니다.
     */
    SpringExtension.getApplicationContext(context)
        .getBean("databaseCleanup", DatabaseCleanup.class)
        .execute();

    SpringExtension.getApplicationContext(context)
        .getBean("testSaveEntity", TestSaveEntity.class)
        .creatEntityTest();

  }
}
