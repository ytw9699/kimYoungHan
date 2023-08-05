package hello.core.lifecycle;

import javax.annotation.PostConstruct;//자바표준 스프링 아닌 다른 컨테이너에서도 동작
import javax.annotation.PreDestroy;

public class NetworkClient{
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url){
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect(){
        System.out.println("connect: " + url);
    }

    public void call(String message){
        System.out.println("call : " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("close: " + url);
    }

    @PostConstruct
    public void init(){//의존관계주입이 끝나면 호출!
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close(){//빈이 종료될때 호출
        System.out.println("NetworkClient.close");
        disconnect();
    }
}

