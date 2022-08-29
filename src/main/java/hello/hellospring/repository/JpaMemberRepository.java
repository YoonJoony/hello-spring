package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{
    private EntityManager em; //jpa를 라이브러리로 받으면 스프링 부트가 자동으로 entitymanager를 자동으로 생성해줌.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    //entitymager를 주입 받아야 한다.
    @Override
    public Member save(Member member) {
        em.persist(member);//persist : 영구적으로 저장?
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();

    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) //객체를 대상으로 쿼리를 날 m이 sql로 번역됨.
                .getResultList();
    }
}
