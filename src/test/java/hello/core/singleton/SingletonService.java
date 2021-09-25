package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService(){}    // 외부에서 new 키워드를 사용한 객체 생성 방지

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
