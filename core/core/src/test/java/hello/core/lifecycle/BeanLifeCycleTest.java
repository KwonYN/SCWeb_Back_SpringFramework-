package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext cac
                = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient bean = cac.getBean(NetworkClient.class);
        cac.close();    // 스프링 컨테이너 종료 메서드
    }

    @Configuration
    static class LifeCycleConfig {

//        @Bean(initMethod = "init", destroyMethod = "close") // 콜백 메서드 이름 지정
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            // 생성자 호출 시점에는 url에 아무 값도 들어가지 않았기 때문에 null!!

            networkClient.setUrl("https://hello-spring.dev");
            // 객체를 생성한 다음에야 setUrl을 통해 외부에서 수정자 주입
            return networkClient;
        }
    }
}
