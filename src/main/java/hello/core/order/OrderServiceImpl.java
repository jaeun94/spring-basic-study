package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

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

    /*
    // 메서드 주입
    // 한번에 여러필드 주입 가능
    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }*/

    /* 생성자 주입 */
    // 빈 등록시 자동주입
    // 생성자 호출시점에 딱 1번만 호출됨
    // 불변, 필수 의존관계
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
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
