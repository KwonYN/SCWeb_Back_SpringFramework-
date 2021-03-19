package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller // 이 Annotation 안 붙여서 30분 날렸나 슈발;;
@RequiredArgsConstructor // 생성자 자동으로.. → 주입 자동
public class LogDemoController {

    private final LogDemoService logDemoService;

    private final MyLogger myLogger; // proxyMode 설정해주면 됨!!
//    private final ObjectProvider<MyLogger> myLoggerObjectProvider;

    @RequestMapping("log-demo")
    @ResponseBody   // 요청에 대해 반환해줄 페이지가 없어서 이걸 사용했음
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        // 객체에서 실제 요청(.getObject())이 들어올 때, request scope 빈이 정상 처리 됨!
//        MyLogger myLogger = myLoggerObjectProvider.getObject();

        System.out.println("myLogger = " + myLogger.getClass());
        // 출력 결과: class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$15063da

        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);
        System.out.println("requestURL = " + requestURL);

        myLogger.log("controller test");
        Thread.sleep(2000); // 2s간 sleep 했다가 밑에 것이 찍힘
        logDemoService.logic("testId");

        return "OK : Controller에는 @Controller를, Service에는 @Service를 잘 붙이자;;";
    }
}
