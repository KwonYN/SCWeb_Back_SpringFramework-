package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        // Getter & Setter 메서드 자동으로 만들어줌 by Annotation
        helloLombok.setName("lsakdfj;la ksd");
        helloLombok.setAge(11);

        System.out.println("helloLombok.getName() = " + helloLombok.getName());
        System.out.println("helloLombok.getAge() = " + helloLombok.getAge());
        System.out.println("helloLombok.toString() = " + helloLombok.toString());
    }
}
