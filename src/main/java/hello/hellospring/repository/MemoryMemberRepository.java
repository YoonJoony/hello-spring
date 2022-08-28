package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

//구현체
public class MemoryMemberRepository implements MemberRepository{

    //save할 때 메모리를 저장을 할 때
    private static Map<Long, Member> store = new HashMap<>();//HashMap
    private static long sequence = 0L;
    //회원이 Long, 값은 Member

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //member id값 셋팅
        store.put(member.getId(), member); //map 형식 컬렉션으로 만든 store 공간에 키, 값 추가
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) { //id 찾기(지만 객체 형식으로 반환됨)
        return Optional.ofNullable(store.get(id));
        //store에서 save에 입력받은 id인 키에 해당하는 값(value)을 꺼낸다 => id에 해당하는 member객체 반환됨.
        //그냥 store.get(id)로 반환해도 되지만 interface 선언부에 Optional<memver>로 반환 타입이 명시되어 있고
        //꺼낸 id가 널 값일 수 있으므로 요즘에는 널 값을 꺼낼 시 Optional로 감싸서 꺼낸다.
    }

    @Override
    public Optional<Member> findByName(String name) { //String name에 해당하는 객체 반환
        return store.values().stream() //store의 값들을 흐름(stream)에 따라 연산
                .filter(member -> member.getName().equals(name)) //중간 연산 : store의 첫 번째로 넘어온 값(member)에 해당하는 name이 String name과 같은지 filter로 거름.
                .findAny(); //store에서 하나 찾으면 바로 반환
                            //없으면 Optional에 널이 감싸져서 반환
    }

    @Override
    public List<Member> findAll() {  //List 컬렉션 형식으로 객체들 반환됨
        return new ArrayList<>(store.values()); //store의 값(member)들 전부 List 형식으로 공간에 반환됨.
    }

    public void clearStore() {
        store.clear(); //store 싹 비우기
    }
}
