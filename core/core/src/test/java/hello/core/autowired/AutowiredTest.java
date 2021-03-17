package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac
                = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        // 우선 Member는 Spring Bean이 아님!!!!
        // MemberServiceImple이나 MemoryMemberRepository가 Spring Bean이지!! (구현 클래스)
        // 우리는 구현 클래스에만 @Component를 붙여 스프링 빈에 등록함!!
        // required = false → 즉, 의존관계를 주입할 것이 없기 때문에 호출 X
        @Autowired(required = false)
        public void setNoBean1(Member member1) {
            System.out.println("member1 = " + member1);
        }
        // 의존 관계 없더라도 호출 됨! 단, Null가 호출됨
        @Autowired
        public void setNoBean2(@Nullable Member member2) {
            System.out.println("member2 = " + member2);
        }
        // 의존 관계 없더라도 호출 됨! 단, Optional.empty가 호출됨
        @Autowired
        public void setNoBean3(Optional<Member> member3) {
            System.out.println("member3 = " + member3);
        }
    }
}
