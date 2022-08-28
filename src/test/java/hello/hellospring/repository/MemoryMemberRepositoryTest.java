package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MemoryMemberRepositoryTest {
//    MemoryMemberRepository repository = new MemoryMemberRepository(); //만들어둔 interface의 구현체를 사용하기 위해 MemoryMemberRepository의 객체 생성
//    MemberService re = new MemberService();
    MemoryMemberRepository repository; //만들어둔 interface의 구현체를 사용하기 위해 MemoryMemberRepository의 객체 생성
    MemberService memberService;


    @AfterEach //아래 메소드들이 끝날 때 마다 AfterEach가 호출이 됨
    public void afterEach() {
        repository.clearStore(); //아래 Test가 끝날 때 마다 저장소를 비운다.
    }
    @Test
    public void save() { //구현체의 save( )메소드가 잘 구현되는지 테스트
        Member member = new Member(); 
        member.setName("spring"); //member객체의 이름을 spring으로 지정

        repository.save(member); //MemoryMemberRepository(구현체)의 save()메소드 실행
                                 //name:spring 이라는 member객체가 Map(store)에 할당됨

        Member result = repository.findById(member.getId()).get(); //구현체 메소드가 잘 작동하는지 테스트하기 위해 임의의 객체를 생성하고 그 객체에 메소드 구현한 것을 반환받음
                                                                   //위에 findById에다 내가 초기화한 member객체의 id를 넣어 그에 해당하는 객체를 반환
        //System.out.println("result = " + (result == member));
        //Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result); //내가 초기화한 member의 객체와 result가 받은 객체가 같은지 비교
    }

    @Test
    public void findByName() { //구현체의 findByName( )메소드가 잘 구현되는지 테스트
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);   //member1,2 객체 생성 후 메모리에 저장

        Member result = repository.findByName("spring1").get(); //name이 "spring1"에 해당하는 객체 반환되어나옴.(Optional 객체니 get()으로 안의 값 꺼냄)
        assertThat(member1).isEqualTo(result); //내가 초기화한 member1의 객체와 result가 받은 객체가 같은지 비교
    }

    @Test
    public void findAll() { //구현체의 findAll( )메소드가 잘 구현되는지 테스트
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);  //member1,2 객체 생성 후 메모리에 저장

        List<Member> result = repository.findAll(); //findAll이 반환되는 타입이 List형식이니 그걸 받는 것도 컬렉션 형식이어야 한다.
                                                    //따라서 받는 객체도 List<Member>로 타입 설정

        assertThat(result.size()).isEqualTo(2); //findAll이 store의 Map에 저장된 값(store.value(member 객체))를 List 형식으로 반환
                                                        //따라서 findAll의 결과들은 List에 저장된다.(객체들)
                                                        //result.size() : List에 저장된 값들의 숫자를 나타냄
                                                        //그 숫자가 2와 같은지 비교
    }
}
