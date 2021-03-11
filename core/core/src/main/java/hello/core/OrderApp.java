package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImple;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImple();
//        OrderService orderService = new OrderServiceImpl(memberRepository, discountPollicy);

        // AppConfig
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        // Spring 전환 : 그래도 그 밑에 있는 기존 코드들은 안변해!
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 2L;
        Member member = new Member(memberId, "MCSeok", Grade.VIP);
        // 회원 가입 및 메모리 or DB에 회원정보 넣음
        memberService.join(member);

        // 주문 생성 및 주문 결과 반환
        Order order = orderService.createOrder(memberId, "M.I.C", 9000);

        System.out.println("order = " + order); // toString()으로 호출 됨!!
        System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}
