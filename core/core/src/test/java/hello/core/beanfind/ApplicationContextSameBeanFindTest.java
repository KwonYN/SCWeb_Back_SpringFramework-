package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discountpolicy.DiscountPollicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회 시, 같은 타입이 둘 이상이면, 중복 오류 발생")
    void findBeanByTypeDuplicate() {
//        MemberRepository bean = ac.getBean(MemberRepository.class);
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
    } // 예외가 터짐!!
    /*NoUniqueBeanDefinitionException :
            No qualifying bean of type 'hello.core.member.MemberRepository' available:
            expected single matching bean
            but found 2: memberRepository1,memberRepository2*/


    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다!")
    void findBeanByName() {
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }


    @Test
    @DisplayName("특정 타입을 모두 조회하기!!")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        System.out.println("beansOfType = " + beansOfType);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " // value = " + beansOfType.get(key));
        }
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    // Test를 위해서 내부에 MemberRepository 타입의 Bean 객체 두 개 만들어줌
    // +α : static으로 선언해주는 이유는 scope 지정을 위해서!!
    //      (이 클래스 내부에서만 사용하겠다!!)
    @Configuration
    static class SameBeanConfig {
        // AppConfig를 테스트용으로 그냥 비슷한 방식으로 만듦
        // 굳이 AppConfig에 손을 대기 싫기 때문!!

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
