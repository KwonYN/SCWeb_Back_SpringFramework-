package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    // 1. prototype bean
    @Test
    void prototpyeBeanFind() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init() : " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy()");
        }
    }

    // 2. singleton client가 prototype bean 요청 (이렇게 사용하지 말아라)
    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(ClientBean.class,
                                                         PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);    // 마치 stateful;;
    }

    // default(생략 가능) : Singleton Scope
    @RequiredArgsConstructor
    static class ClientBean {

        private final PrototypeBean prototypeBean;

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    // 3. singleton client가 prototype bean 요청 (DL)
    @Test
    void singletonClientUsePrototype2() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(ClientBean2.class,
                PrototypeBean.class);

        ClientBean2 clientBean1 = ac.getBean(ClientBean2.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean2 clientBean2 = ac.getBean(ClientBean2.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
        // 새로 반환해서 주기 때문에 똑같이 1이 되어야 함!
    }

    static class ClientBean2 {

        @Autowired
        ApplicationContext ac;

        public int logic() {
            // 이렇게 .logic을 요청할 때마다
            // 스프링 컨테이너에게 "프로토타입 빈 내놔!" 요청해서 새로 반환 받는;;
            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        } // 하지만 너무 지저분해 코드가;; → 해결방법 "Provider"
    }

    // 4. ObjectProvider
    @Test
    void singletonClientUsePrototype3() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(ClientBean3.class,
                PrototypeBean.class);

        ClientBean3 clientBean1 = ac.getBean(ClientBean3.class);
        // hello.core.scope.SingletonWithPrototypeTest1$PrototypeBean@7e5c856f
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean3 clientBean2 = ac.getBean(ClientBean3.class);
        // hello.core.scope.SingletonWithPrototypeTest1$PrototypeBean@30af5b6b
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    } // 서로 다른 prototype bean임!!

    static class ClientBean3 {

        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;

        @Autowired
        public ClientBean3(ObjectProvider<PrototypeBean> prototypeBeanObjectProvider) {
            this.prototypeBeanObjectProvider = prototypeBeanObjectProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    // 5. javax.inject.Provider
    @Test
    void singletonClientUsePrototype4() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(ClientBean4.class,
                                                         PrototypeBean.class);

        ClientBean4 clientBean1 = ac.getBean(ClientBean4.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean4 clientBean2 = ac.getBean(ClientBean4.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    } // 서로 다른 prototype bean임!!

    static class ClientBean4 {

        private Provider<PrototypeBean> prototypeBeanObjectProvider;

        @Autowired
        public ClientBean4(Provider<PrototypeBean> prototypeBeanObjectProvider) {
            this.prototypeBeanObjectProvider = prototypeBeanObjectProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }
}
