package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discountpolicy.DiscountPollicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac                       // 둘 다 Bean에 등록한다는 것!
                = new AnnotationConfigApplicationContext(AutoAppConfig.class,
                                                         DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int fixedDiscountPrice = discountService.discount(member, 3000, "fixedDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(fixedDiscountPrice).isEqualTo(1000);

        int ratedDiscountPrice = discountService.discount(member, 3000, "ratedDiscountPolicy");
        assertThat(ratedDiscountPrice).isEqualTo(300);
    }

    static class DiscountService {
        private final Map<String, DiscountPollicy> pollicyMap;
        private final List<DiscountPollicy> pollicyList;

        @Autowired
        DiscountService(Map<String, DiscountPollicy> pollicyMap, List<DiscountPollicy> pollicyList) {
            this.pollicyMap = pollicyMap;
            this.pollicyList = pollicyList;
            System.out.println("pollicyMap = " + pollicyMap);
            System.out.println("pollicyMap = " + pollicyList);
            /*
            * AutoAppConfig로부터 등록된 Bean
            * pollicyMap = {fixedDiscountPolicy=hello.core.discountpolicy.FixedDiscountPolicy@25a6944c,
            *               ratedDiscountPolicy=hello.core.discountpolicy.RatedDiscountPolicy@5e1fa5b1}
            * pollicyMap = [hello.core.discountpolicy.FixedDiscountPolicy@25a6944c,
            *               hello.core.discountpolicy.RatedDiscountPolicy@5e1fa5b1]
            * */
        }

        // discount 얼마나 되는지?라는 로직
        public int discount(Member member, int price, String discountCode) {
            DiscountPollicy discountPollicy = pollicyMap.get(discountCode);
            return discountPollicy.discount(member, price);
        }
    }
}
