package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discountpolicy.DiscountPollicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor    // final 붙은 필드를 모아서 생성자를 자동으로 만들어줌!!! (final 붙은 필드에 대해서만!! => 수정자 못 씀!)
public class OrderServiceImpl implements OrderService {

    // 메모리 or DB에서 회원정보를 불러내야 하기 때문에
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // 회원정보에 따라 할인정책을 반영해야 하기 때문에
//    private final DiscountPollicy discountPollicy = new FixedDiscountPolicy();
//    private final DiscountPollicy discountPollicy = new RatedDiscountPolicy();
    private final DiscountPollicy discountPollicy;    // final 써주면 처음 선언할 때 무조건 할당해주어야 함!! (C언어에서 const 같이;;)

//    // 수정자 주입 : "수정이 될 수 있"기 때문에 위 필드의 final을 빼주어야 함!!
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("호출순서 2 : 수정자 (@Autowired 붙은 애 찾아서 의존관계 주입)");
//        System.out.println("set memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPollicy(DiscountPollicy discountPollicy) {
//        System.out.println("호출순서 2 : 수정자 (@Autowired 붙은 애 찾아서 의존관계 주입)");
//        System.out.println("set discountPollicy = " + discountPollicy);
//        this.discountPollicy = discountPollicy;
//    }

    // 생성자 주입 → DIP 위반을 해결
//     @Autowired // : 생성자가 딱 하나일 때는 @Autowired 없어도 자동 주입
    // + lombok 라이브러리가 @RequiredArgsConstructor 어노테이션을 보고 생성자 자동 생성!
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPollicy discountPollicy) {   // @Qualifier("notMainDiscountPolicy") DiscountPollicy ratedDiscountPolicy : @Qualifier가 "notMain~~"인 fixedDiscountPolicy가 파라미터로 들어옴!!!
        System.out.println("호출순서 1 : 생성자 (Spring Bean 등록하면서 생성자 호출)");
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("discountPollicy = " + discountPollicy);
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
