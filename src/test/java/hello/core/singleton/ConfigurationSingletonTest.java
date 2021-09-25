package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemoryMemberRepository memberRepository = ac.getBean("memberRepository", MemoryMemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);
        // 다 같은 인스턴스가 공유되어 사용되고 있음

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        // hello.core.AppConfig$$EnhancerBySpringCGLIB$$94ac8890 > 순수한 클래스의 경우 hello.core.AppConfig
        // AppConfig를 상속받은 임의의 다른 클래스를 만들고, 그 클래스를 스프링 빈으로 등록함 > 싱글톤 보장

        // 바이트코드를 조작하는 CGLIB
        // @Bean 이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈 반환, 없으면 스프링 빈에 등록하는 코드가 동적으로 만들어짐

        // @Configuration 미사용시 싱글톤을 보장하지 않음
    }
}
