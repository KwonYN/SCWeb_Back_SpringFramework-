package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(PrototypeBean.class);
        // 마찬가지로 AnnotationCon~~에 직접 넣어준 클래스는
        // 마치 @Component가 붙은 것처럼 인식(?)되어 바로 Bean에 등록된다고 함!

        // 어? 싱글톤 스코프에서는 이 시점에 @PostConstruct 콜백 메서드 호출됐었는데!?!?!?
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        // ptototype scope에서는 요청이 들어올 때 Bean 생성 및 등록, 초기화되고
        // @PostConstruct 콜백 메서드가 이 때 호출됨!!

        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        // 다른 요청이기에 Bean 객체 또한 다름!!! 싱글톤처럼 같지 않음!
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        prototypeBean1.destroy();
        prototypeBean2.destroy();

        ac.close(); // 스프링 컨테이너 종료 메서드
        // 어!?!? 그런데 destroy 메서드가 호출되지 않네!?!?
        // 프로토타입 스코프 빈은 스프링 컨테이너가 생성, 의존관계 주입, 초기화까지만 관여하고
        // 그 이후로는 따로 관리하지 않고 내버려둠!!
        // 클라이언트가 직접 Bean 종료 메서드 실행해주어야 함!! (ac.close(); 바로 위의 코드 추가)
    }

    // 프로토타입 스코프의 Bean 생성
    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("Call PrototypeBean.init()");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("Call PrototypeBean.destroy()");
        }
    }
}
