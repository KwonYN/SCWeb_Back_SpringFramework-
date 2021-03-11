package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class XmlAppContext {

    @Test
    void xmlAppContext() {
        ApplicationContext ac
                = new GenericXmlApplicationContext("appConfig.xml");
                        // java코드 아닌 것, 여기에서는 xml은 resources 밑에 두면 됨!

        MemberService memberService
                = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService)
                .isInstanceOf(MemberService.class);
    }
}
