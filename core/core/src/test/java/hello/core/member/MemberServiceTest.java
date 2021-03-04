package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImple();

    @Test
    void memberTest() {
        // 1. given : 이러이러한 환경에서
        Member member = new Member(1L, "Minchang", Grade.BASIC);

        // 2. when : 이럴 때
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // 3. then : 이렇게 된다

        // assertj.core.api
        Assertions.assertThat(member).isEqualTo(findMember);
        // Run 결과가 녹색불이 뜨면서 exit code 0이면 성공!!
        // 실패하면 에러가 뜨고, 그 이유? 원인?이 밑에 뜸
    }
}
