package hello.core;

import hello.core.discountpolicy.DiscountPollicy;
import hello.core.discountpolicy.FixedDiscountPolicy;
import hello.core.discountpolicy.RatedDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImple;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 이제 스프링 시작!! : @Configuration, @Bean → @Bean 붙은 애들이 Spring Container에 등록이 됨!!
@Configuration
// 공연 기획자 : 구현체를 클라이언트 코드를 직접 만지지 않고 주입해줌!
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println(" call AppConfig.memberService");
        return new MemberServiceImple(memberRepository());
    }

    // Ctrl + Alt + m → replace 가능 (extract methods)
    // for Refactoring : 중복 제거 + 나 MemberRepository(역할)을 MemoryMemberRepository로 구현할거야!
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPollicy());
    }

    // for Refactoring : 중복 제거 + 나 DiscountPolicy(역할)을 FixedDiscountPolicy로 구현하럭야!
    @Bean
    public DiscountPollicy discountPollicy() {
//        return new FixedDiscountPolicy();
        // 정액할인을 정률할인으로 바꾸고 싶다? 이 부분만 바꿔주면 됨 ㄷㄷ;;
        return new RatedDiscountPolicy();
    }
}
