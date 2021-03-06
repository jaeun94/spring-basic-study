package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor    // final이 붙은 필드를 모아 생성자를 자동으로 만들어줌, 생성자주입 코드 깔끔하게 사용 가능
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    //private final DiscountPolicy rateDiscountPolicy;    // 필드명을 빈 이름으로 수정하면 빈 중복 오류 해결 가능

    /*
    // 필드 주입
    // DI 프레임워크가 없으면 아무것도 할 수 없음
    // 외부에서 변경이 불가능해 테스트에 불편함 > 테스트를 위해 별도로 setter 만들어서 사용해야함
    @Autowired final MemberRepository memberRepository;
    @Autowired final DiscountPolicy discountPolicy;*/

    /*
    // 수정자 주입(setter 주입)
    // 선택, 변경 가능성이 있는 의존관계
    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    // 메서드 주입
    // 한번에 여러필드 주입 가능
    /*@Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }*/

    /* 생성자 주입 */
    // 빈 등록시 자동주입
    // 생성자 호출시점에 딱 1번만 호출됨
    // 불변, 필수 의존관계
    // final 키워드 사용 가능(컴파일 시점에 오류 파악 가능)
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
