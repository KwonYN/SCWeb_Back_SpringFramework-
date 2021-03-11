package hello.core.singleton;

public class SingletonService {

    // JVM이 뜰 때, 내부적으로 static 영역에 자기 자신 인스턴스를 생성하여 instance에 넣어둠!
    private static final SingletonService instance
            = new SingletonService();

    /*
        외부(public)에서 static 메서들를 통해 조회는 가능하지만,
        new로 생성은 안됨!!
        && 항상 같은 인스턴스만을 return
    */
    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {
        /*
            권한이 ★"private"이기에 외부에서 못 가져다 씀!!;;; 즉, new 못함
            이를 통해 딱 1개의 인스턴스만 존재하는 것이 가능해짐!!
         */
    }

    public void logic() {
        System.out.println("Singleton 객체 로직 호출");
    }
}