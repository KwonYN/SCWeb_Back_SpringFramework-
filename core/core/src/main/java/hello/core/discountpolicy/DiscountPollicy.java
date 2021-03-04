package hello.core.discountpolicy;

import hello.core.member.Member;

public interface DiscountPollicy {

    /** // "/**"입력 후 Enter
     *
     * @param member
     * @param price
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
