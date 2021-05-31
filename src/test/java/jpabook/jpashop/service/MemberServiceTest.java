package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //autowired하기 위해 스프링 부트 띄운상태로 테스트함
@Transactional //테스트에서는  기본적으로 롤백을 하게 됨
class MemberServiceTest {
    //db엮어서 테스트
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Autowired EntityManager em;

    //DB 트랜잭션이 커밋하는 순간 flush되면서 jpa 영속성 컨텍스트에 있는 member객체의 insert문이 만들어짐

    @Test
    //@Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member =new Member();
        member.setName("kim");
        //when
        Long savedId = memberService.join(member);
        //then
        em.flush(); //영속성 컨텍스트에 있는 변경내용을 DB에 반영 : insert문 나가지만 롤백됨
        assertEquals(member, memberRepository.findOne(savedId));
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 =new Member();
        member1.setName("kim1");
        Member member2 =new Member();
        member2.setName("kim2");
        //when
        memberService.join(member1);
        try {
            memberService.join(member2); //예외 터져야 함
        } catch (IllegalStateException e){
            return;
        }

        //then

    }

}