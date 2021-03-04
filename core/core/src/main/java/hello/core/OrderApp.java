package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImple;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImple();
        OrderService orderService = new OrderServiceImpl();

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
