package hello.core.discountpolicy;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RatedDiscountPolicyTest {

    RatedDiscountPolicy discountPolicy = new RatedDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 함!")
    void vip_o() {
        // given
        Member member = new Member(1L, "IamVIP", Grade.VIP);

        // when
        int discount = discountPolicy.discount(member, 10000);

        //then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP 아니면 할인 적용 안됨!")
    void vip_x() {
        // given
        Member member = new Member(2L, "IamBASIC", Grade.BASIC);

        // when
        int discount = discountPolicy.discount(member, 9000);

        // then
        assertThat(discount).isEqualTo(0);
        // Alt + Enter → on-demand static import
    }

}