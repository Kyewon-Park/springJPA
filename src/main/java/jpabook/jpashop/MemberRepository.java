package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; //em 생성 자동으로 되어 주입됨.

    public Long save(Member member){
        em.persist(member);
        return member.getId(); //객체 말고 id만 반환하는 이유? 커맨드랑 쿼리를 분리하여라.
                            // 저장을 하고나면 가급적이면 리턴값을 만들지 않음
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
