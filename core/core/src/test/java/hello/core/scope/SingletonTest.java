package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonTest {

    @Test
    void singletonBeanFind() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(SingletonBean.class);
        // 참고
        // 이 때, SingletonBean 클래스에 @Component 명시해주지 않아도
        // 스프링에서 알아서 컴포넌트 스캔으로 읽어 빈 등록해준다고 함!!
        // 그리고 이 때 Bean이 생성/등록되므로 "init"(@PostConstruct) 콜백 호출

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        System.out.println("singletonBean1 = " + singletonBean1);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean3 = ac.getBean(SingletonBean.class);
        System.out.println("singletonBean1 = " + singletonBean2);
        System.out.println("singletonBean1 = " + singletonBean3);
        // 싱글톤이니까 이 세 개의 빈이 모두 같겠지!?!? (ex. hello.core.scope.SingletonTest$SingletonBean@409c54f)
        Assertions.assertThat(singletonBean1).isSameAs(singletonBean3);

        ac.close(); // 스프링 컨테이너 종료 메서드
        // 그리고 이 때 스프링 컨테이너 종료되기 직전 "destroy"(@PreDestroy) 콜백 호출
        // 즉, 스프링 컨테이너 LifeCycle과 스프링 Bean의 LifeCycle이 같이 감 (싱글톤!!!)
    }

    // Scope의 default는 singleton이기에 지정 안해주어도 되지만, 학습을 위해 명시해줐음!
    @Scope("singleton")
    static class SingletonBean {

        @PostConstruct
        public void init() {
            System.out.println("Call SingletonBean.init()");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("Call SingletonBean.destroy()");
        }
    }
}
