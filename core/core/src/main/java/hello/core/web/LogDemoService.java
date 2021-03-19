package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final MyLogger myLogger; // proxyMode 설정해주면 됨!!
//    private final ObjectProvider<MyLogger> myLoggerObjectProvider;

    public void logic(String id) {
//        MyLogger myLogger = myLoggerObjectProvider.getObject();
        myLogger.log("service id : "+id);
    }
}
