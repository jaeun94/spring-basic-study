package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        int price1 = statefulService1.order("userA", 10000);
        int price2 = statefulService2.order("userB", 20000);

        // userA 주문금액 조회
        // int price = statefulService1.getPrice();
        System.out.println("price = " + price1); // 20000원이 찍힘 > 중간에 userB가 price를 바꿈, 인스턴스를 공유하기때문

        //Assertions.assertThat(statefulService1.getPrice()).isEqualTo(statefulService2.getPrice());
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}