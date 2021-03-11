package hello.core.order;

import hello.core.discountpolicy.DiscountPollicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {

    // 메모리 or DB에서 회원정보를 불러내야 하기 때문에
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // 회원정보에 따라 할인정책을 반영해야 하기 때문에
//    private final DiscountPollicy discountPollicy = new FixedDiscountPolicy();
//    private final DiscountPollicy discountPollicy = new RatedDiscountPolicy();
    private final DiscountPollicy discountPollicy;    // final 써주면 처음 선언할 때 무조건 할당해주어야 함!! (C언어에서 const 같이;;)

    // 생성자 주입 → DIP 위반을 해결
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPollicy discountPollicy) {
        this.memberRepository = memberRepository;
        this.discountPollicy = discountPollicy;
    }
    // 이후 구현객체를 대신 주입해주면 됨!! → DIP 적용
    // 그런데 누가 그걸 해주지!?!?!?!? →

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 주문생성 요청이 들어오면
        // 1. 회원 정보를 조회
        Member member = memberRepository.findById(memberId);
        // 2. 회원 정보에 따라 할인정책 적용
        int discountPrice = discountPollicy.discount(member, itemPrice);

        // 3. 생성된 주문 결과 리턴
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
