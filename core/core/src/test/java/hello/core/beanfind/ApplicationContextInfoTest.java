package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("Spring Container에 등록된 모든 Bean 출력")
    void findAllBean() {
        String[] beanDefinition = ac.getBeanDefinitionNames();
        for (String s : beanDefinition) {
            Object bean = ac.getBean(s);
            System.out.println("name = " + s + " // object = " + bean);
        }
    }

    @Test
    @DisplayName("Spring Container에 등록된 애플리케이션 Bean(내가 만든 코드 or 사용한 라이브러리) 출력")
    void findApplicationAllBean() {
        String[] beanDefinition = ac.getBeanDefinitionNames();
        for (String s : beanDefinition) {
            BeanDefinition beanDefinition1 = ac.getBeanDefinition(s);

            /*
            BeanDefinition.ROLE_INFRASTRUCTURE: 스프링 내부에서 뭔지는 모르겠지만 자동으로(?) 생성된 빈
            BeanDefinition.ROLE_APPLICATION : 내가 애플리케이션 개발을 위해서 등록한 빈 or 외부 라이브러리
            */
            if(beanDefinition1.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(s);
                System.out.println("name = " + s + " // object = " + bean);
            }
        }
    }
}

