package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    //private final MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memorymemberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memorymemberRepository = memberRepository;
    }
    //회원 가입
    public Long join(Member member) { //같은 이름이 있는 중복 회원이 존재해선 안된다를 로직으로 구현.

        /*
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m ->  {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }); 
        

        //위에 있는 코드로도 실행 가능하지만 예쁘지 않다.
        //따라서 memberRepository로 findByName(member.getName())이 반환한 객체를 바로 .ifPresent()로 로직 구현이 가능한다. -> 코드 줄임.
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> { //값이 있으면 특정 로직이 동작한다. optional이기 때문에 optional 메소드 ifPresent() 사용 가능
                            throw new IllegalStateException("이미 존재하는 회원입니다."); //특정 로직
                        });
                        
        //이렇게 줄였지만 findByName뒤로 쭉쭉 나오니 이런것은 메소드로 만들어 주는 것이 좋다.
        //해당 로직 구현한 것을 전부 선택 후 ctrl + art + shift + T 후 method 검색 후 Extract method 선택하면 메소드로 만들어짐
        */
        validateDuplicateMember(member); //중복 회원 검증

        memorymemberRepository.save(member); //통과하면 저장.
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memorymemberRepository.findByName(member.getName()) //참조된 객체의 name이 store의 Map 공간에 있으면(중복되면) 오류검출코드 실행.
                .ifPresent(m -> { //값이 있으면 특정 로직이 동작한다. optional이기 때문에 optional 메소드 ifPresent() 사용 가능
                    throw new IllegalStateException("이미 존재하는 회원입니다."); //특정 로직
                });
    }

    /**
     * 전체 회원 조회 : 서비스는 비즈니스에 관한 로직이 있어야 함으로 비즈니스에 관한 용어를 써야함.
     */
    public List<Member> findMembers() {
        return memorymemberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memorymemberRepository.findById(memberId);
    }
}
