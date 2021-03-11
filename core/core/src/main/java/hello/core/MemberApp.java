package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImple;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
        // 서비스 구현체 동적으로 할당
//        MemberService memberService = new MemberServiceImple();

        // AppConfig
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // Spring으로 전환
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = // argument : Spring Container에 @Bean으로 등록된 "메서드 이름", 반환 타입
            applicationContext.getBean("memberService", MemberService.class);

        // 멤버 객체 생성
        // Ctrl + Alt + v
        Member member = new Member(1L, "MC9", Grade.VIP);
        // 가입
        memberService.join(member);

        Member findMember = memberService.findMember(1L);

        System.out.println("findMember = " + findMember.getName());
        System.out.println("member = " + member.getName());
        // 이렇게 하나하나 쳐가면서 눈으로 같은지 아닌지 확인해야 하나!?!?
        // ▶ junit이라는 테스트 프레임워크를 사용할 것이다!!
        //   기본으로 세팅이 되어 있음 (~~~~/src/test/java/hello.core.member)
        // 운영환경에서는 test 밑에 있는 코드들은 배포가 안됨!!!!
    }
}
