package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImple;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 중요!: @Configuration?
public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac
                = new AnnotationConfigApplicationContext(AppConfig.class);
        // @Bean 호출
        MemberServiceImple memberService = ac.getBean("memberService", MemberServiceImple.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        // memberService 생성할 때, MemoryMemberRepository() 호출되지? (in AppConfig class)
        MemberRepository memberRepository1
                = memberService.getMemberRepository();
        // orderService 생성할 때, MemoryMemberRepository() 호출되지? (in AppConfig class)
        MemberRepository memberRepository2
                = orderService.getMemberRepository();

        // 그럼 각각에서 호출된(?) memberRepository는 다를까? 같을까? (싱글톤 패턴)
        System.out.println("memberService → memberRepository : "
                + memberRepository1);
        System.out.println("orderService → memberRepository : "
                + memberRepository2);

        /*
            왜 이 테스트를 진행?
            → AppConfig 코드를 보면, memberService 생성할 때나 orderService 생성할 때
              두 케이스에서 모두 new MemoryMemberRepository()가 호출되는 플로우를 볼 수 있음!
              물론 싱글톤 패턴 배울 때, JVM에서 단 하나의 객체만을 생성하여 관리해준다고 알고 있지만,
              코드는 아닌걸...?
         */
        // ▶ 결과! : 같은 객체임!! Why? : Singleton!!!
        Assertions.assertThat(memberRepository1).isSameAs(memberRepository2);

        // 그리고 얘는 실제로 @Bean에 등록된 memberRepository 객체
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        System.out.println("@Bean에 등록된 memberRepository : "
                + memberRepository);

        Assertions.assertThat(memberRepository).isSameAs(memberRepository1);
        // ▶ 더 결과!! : 세 개의 Spring Bean이 다 같다!!!

        // 궁금증! : new라는 코드가 분명히 있고, 코드 돌아갈 때 얘 보면 객체 생성할텐데???
        // → 실제 AppConfig에 가서 출력해보자!!
        // ▶ 이미 생성되어 있던 memberRepository @Bean은 다시 호출되지 않음
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac
                = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean.getClass = " + bean.getClass());
        // bean.getClass = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$401869ee
        // → "$$EnhancerBySpringCGLIB$$401869ee" 이건 뭐지!?!?
    }
}
