package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("1. No Spring (스프링 없는 순수 DI 컨테이너)")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 조회 : 호출할 때마다 객체를 생성하는지? (Ex. 두 번 호출)
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();
        // 참조값이 다른지 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        // ▶ Ex. hello.core.member.MemberServiceImple@7cc0cdad, MemberServiceImple@7c7b252e 서로 다른 객체 생성

        // memberService1과 memberService2가 다르면 통과 → 호출할 때마다 객체가 새롭게 생성된다는 뜻!
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
        /*
        +α : memberService 호출할 때, MemberServiceImpl뿐만 아니라 MemoryMemberRepository까지 생성하기 때문에
             두 번 호출할 때, 객체는 총 4개나 새로 생성됨!!;;
             → Singleton Pattern : 해당 객체 딱 하나만 생성 && 그것을 공유
        */
    }

    @Test
    @DisplayName("2. Singleton Pattern을 적용한 객체 사용")
    void singletonServiceTest() {
//        new SingletonService(); // SingletonService() has private access ...
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        // 싱글톤 패턴 적용 → 과연 같은 객체가 호출될까?
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        // ▶ 샷!! 둘 다 hello.core.singleton.SingletonService@15ca788로 같은 객체네!!

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("3. Spring Container와 Singleton")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 조회
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 참조값: 같은 값 반환되나?
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        Assertions.assertThat(memberService1).isSameAs(memberService2);

        // ▶ 조회할 때마다 스프링 컨테이너가 Bean에 등록한 객체를 반환(같은 놈)
        // ▶ 봐봐라. MemberServiceImple 코드에 싱글톤 패턴이 적용되었나? 놉!!
    }
}
