package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
//    private DataSource dataSource; //DB에 연결할 수 있는 정보를 만들어 줌
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    } 데이터 소스

    private EntityManager em; //jpa를 활용 할 떄 필요한 엔티티매니저 생성 후 저장소에 주입

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    //MemberService와 MemberRepository를 스프링 빈으로 컨테이너에 넣어준다.
    @Bean //스프링 빈에 등록 하라는 뜻
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    //return new MemberService();만 적으면 오류가 난다.
    //MemberService가 MemberRepository를 사용하니 MemberService에 MemberRepository 추가를 해 줘야 한다.
    //return new MemberService(memberRepository()); 로 수정

    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }


}
