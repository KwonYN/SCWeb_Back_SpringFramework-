package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(          // 이전 예제코드들을 여기에서 제외하기 위함!
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
                                               classes = Configuration.class)
)
// 컴포넌트 스캔: @Bean 메서드를 쫙 긁어서 스프링 빈으로 올리는 기능
// → '@Component' Annotation이 붙은 클래스를 모두 찾아서 자동으로 Spring Bean에 등록해줌
//   && Filter로 제외해줄 클래스 지정 가능!
//   && @Configuration도 소스코드 열어보면 @Component 붙어있음!
public class AutoAppConfig {

}
