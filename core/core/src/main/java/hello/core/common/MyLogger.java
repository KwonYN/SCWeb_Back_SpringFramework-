package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String msg) {
        System.out.println("["+uuid+"] ["+requestURL+"] "+msg);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();// 전 세계에서 유일한 UUID 만들어줌(절대 겹치지 않음!)
        System.out.println("["+uuid+"] request scope bean created: "+this);
    }

    @PreDestroy
    public void close() {
        System.out.println("["+uuid+"] request scope bean closed: "+this);
    }
}
