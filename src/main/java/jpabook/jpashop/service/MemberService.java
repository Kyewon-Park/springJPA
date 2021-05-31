package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기에 쓰면 성능 조금 더 나아짐
                //jpa의 모든 데이터 변경과 로직들은 가급적이면 트랜잭션 안에서 실행되야 함
                //스프링이 제공하는 어노테이션임
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    //@RequiredArgsConstructor
//    @Autowired //생성자 인잭션: 테스트케이스 작성할 때 좋음
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원 가입
    @Transactional //(readOnly = false)디폴트
    public Long join(Member member) {
        //같은 아이디로 가입 방지
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
