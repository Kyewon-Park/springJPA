package jpabook.jpashop;

import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");
        //when
        Long savedId = memberRepository.save(member);
        Member foundMember = memberRepository.find(savedId);
        //then
        Assertions.assertThat(foundMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(foundMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(foundMember).isEqualTo(member);
        //같은 트랜잭션 안에서 조회하면 영속성 컨텍스트가 똑같기 때문에
        //같은 영속성 컨텍스트에서 id가 같으면 같은 entity로 식별함
    }
    
}