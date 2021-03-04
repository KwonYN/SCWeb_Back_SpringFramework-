package hello.core.order;

import hello.core.discountpolicy.DiscountPollicy;
import hello.core.discountpolicy.FixedDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    // 메모리 or DB에서 회원정보를 불러내야 하기 때문에
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 회원정보에 따라 할인정책을 반영해야 하기 때문에
    private final DiscountPollicy discountPollicy = new FixedDiscountPolicy();

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
}
