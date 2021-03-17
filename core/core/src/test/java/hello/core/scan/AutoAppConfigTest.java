package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.discountpolicy.DiscountPollicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);

        System.out.println(ac.getBean(AutoAppConfig.class).getClass());
        // hello.core.AutoAppConfig$$EnhancerBySpringCGLIB$$fd5a9b@3381b4fc
        // → AutoAppConfig를 상속받은 클래스가 AutoAppConfig 대신 Spring Bean에 등록됨!!!!
    }
}
