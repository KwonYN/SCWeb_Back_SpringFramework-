package hello.core.discountpolicy;

import hello.core.member.Grade;
import hello.core.member.Member;

public class RatedDiscountPolicy implements DiscountPollicy {

    private int discountRate = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP) {
            return price * discountRate / 100;
            // But.. 이게 정말 괜찮은 코드일까?
        }
        return 0;
    }
}
