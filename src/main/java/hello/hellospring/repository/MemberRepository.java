package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //회원 저장소 저장
    Optional<Member> findById(Long id);
    //Optional : 널이 반환 될 시 감싸서 반환
    Optional<Member> findByName(String name);
    List<Member> findAll(); //지금까지 저장된 모든 회원 리스트 반환
}
