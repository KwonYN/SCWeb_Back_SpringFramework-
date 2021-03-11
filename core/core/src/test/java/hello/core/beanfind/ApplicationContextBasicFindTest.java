package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImple;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("Bean 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
//        System.out.println("memberService = " + memberService);
//        System.out.println("memberServiceClass = " + memberService.getClass());
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImple.class);
    }

    @Test
    @DisplayName("Bean 이름없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImple.class);
    }

    @Test
    @DisplayName("구체 타입으로만 조회 (자식은 부모다. 즉, 자식은 부모의 타입으로 캐스팅(?) 가능)")
    void findBeanByType2() {
        MemberService memberService = ac.getBean("memberService", MemberServiceImple.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImple.class);
    }
    /*
    * 물론 구체적인 타입으로 조회하는 것은 좋지 않음!!!
    * 왜냐? => 역할과 구현은 구분해야 하고, 우리는 역할에 의존해야 함!!
    * 구체 타입으로 조회한다는 것은 구현에 의존하는 것임!
    * */

    @Test
    @DisplayName("Bean 이름으로 조회 X")
    void findBeanByNameX() {
//        MemberService memberService = ac.getBean("XXX", MemberService.class);

        // import static org.junit.jupiter.api.Assertions.*;
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("XXX", MemberService.class));
    }
}
