package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient { //implements InitializingBean, DisposableBean {

    private String url;

    // 생성자
    public NetworkClient() {
        System.out.println("생성자 호출 : url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect : url = " + url);
    }

    public void call(String msg) {
        System.out.println("call : " + url + " - msg : " + msg);
    }

    // 서비스 종료 시 호출 : 서비스 안전하게 종료되도록!!
    public void disconnect() {
        System.out.println("disconnect : " + url);
    }
    

    // 스프링 인터페이스, 메서드 지정 다 좋다 이거야!! 하지만 애노테이션 쓰라 이거야!!!!
    @PostConstruct
    public void init() {
        System.out.println("init");
        connect();
        call("초기화 연결 메시지");
    }
    @PreDestroy
    public void close() {
        System.out.println("close");
        disconnect();
    }

//// 인터페이스 사용 : implements InitializingBean, DisposableBean
//    // 초기화 콜백 : 해당 Bean 객체 생성 및 의존관계 주입이 끝나면 호출됨
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("afterPropertiesSet");
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    // 소멸전 콜백 : 해당 Bean 소멸 직전에 호출됨
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("destroy");
//        disconnect();
//    }
}
