package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {
    
    @Test
    @DisplayName("스프링 없는 순수 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        
        // 1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        // 2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();
        
        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // 웹 어플리케이션의 경우 요청이 많기 때문에 계속 객체를 새로 생성하는것은 효율적이지 않음
        // 싱글톤 패턴으로 생성된 객체 인스턴스를 공유해서 사용 필요

        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        assertThat(singletonService1).isSameAs(singletonService2);

        // 스프링 컨테이너는 직접 싱글톤패턴으로 구현하지 않아도 기본적으로 객체를 싱글톤으로 만들어서 관리해줌
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isSameAs(memberService2);
    }
}
