package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discountpolicy.DiscountPollicy;
import hello.core.discountpolicy.FixedDiscountPolicy;
import hello.core.discountpolicy.RatedDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회 시 자식이 둘 이상 있으면, 중복 오류 발생")
    void findBeanByParentTypeDuplicate() {
//        DiscountPollicy bean = ac.getBean(DiscountPollicy.class);
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPollicy.class));
    } // NoUniqueBeanDefinitionException


    @Test
    @DisplayName("자식이 둘 이상 있으면, Bean 이름을 지정하면 됨!")
    void findBeanByParentTypeBeanName() {
        DiscountPollicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPollicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RatedDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회 - 물론 구현에 의존하기에 좋지 않은 방법ㅋ")
    void findBeanBySubType() {
        RatedDiscountPolicy bean = ac.getBean(RatedDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RatedDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 전부 다 조회하기")
    void findAllBeanByParentType() {
        Map<String, DiscountPollicy> beansOfType = ac.getBeansOfType(DiscountPollicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " // value = " + beansOfType.get((key)));
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기- Object")
    void findAllBeanByObjectType() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " // value = " + beansOfType.get(key));
        }
    } // Java의 모든 객체는 Object 타입의 자식이기에 스프링에서 사용하는 눈에 보이지 않는 Bean들도 다 조회됨!


    @Configuration
    static class TestConfig {

        @Bean
        public DiscountPollicy rateDiscountPolicy() {
            return new RatedDiscountPolicy();
        }

        @Bean
        public DiscountPollicy fixDiscountPolicy() {
            return new FixedDiscountPolicy();
        }
    }
}
