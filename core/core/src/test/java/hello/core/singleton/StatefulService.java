package hello.core.singleton;

public class StatefulService {

    private int price; // 상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.println("name = " + name + " orders " + price + "won.");
        this.price = price; // 여기가 문제다!!!
        // ▶ statefulService2.order("Mindoyiyi", 99000); 여기에서 바꿔치기 되버림!! (단 하나의 객체만 존재!!;;)
    }

    public int getPrice() {
        return this.price;
    }
}
