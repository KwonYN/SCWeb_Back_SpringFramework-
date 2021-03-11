package hello.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        // Bean 객체 호출
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // Thread : A 사용자(민짱)가 9000원 주문
        statefulService1.order("MinZzang", 9000);
        // Thread : B 사용자(민도이)가 99000원 주문
        statefulService2.order("Mindoyiyi", 99000);

        // 사용자 A가 주문 금액 조회? → 당연히 9000원 나와야... → 아니 왜 99000원이!?!?
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);
        // A가 주문하고 금액 조회하는 사이에 B가 껴들었다!!!(실제 Thread를 사용하게 되면! 이 예시에서는 스레드 사용 X. 그냥 덮어버림)

        assertThat(statefulService1.getPrice()).isEqualTo(99000);
    }

    @Test
    void statelessServiceSingleton() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        // Bean 객체 호출
        StatelessService statelessService1 = ac.getBean(StatelessService.class);
        StatelessService statelessService2 = ac.getBean(StatelessService.class);

        // Thread : A 사용자(민짱)가 9000원 주문
        int minZzang
                = statelessService1.order("MinZzang", 9000);
        // Thread : B 사용자(민도이)가 99000원 주문
        int mindoyiyi
                = statelessService2.order("Mindoyiyi", 99000);

        // 민짱 금액 조회? 이번에도 도이랑 같을까!?!?
        // → StatelessService 클래스에 지역변수가 사용되었고, Stateless이기 때문에 9000원이 제대로 찍힘!!
        System.out.println("minZzang = " + minZzang);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }

        @Bean
        public StatelessService statelessService() {
            return new StatelessService();
        }
    }

}