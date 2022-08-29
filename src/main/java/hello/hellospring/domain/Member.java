package hello.hellospring.domain;
import javax.persistence.*;

@Entity//자바에서 관리하는 엔티티가 됨
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 ID를 자동으로 생성해 주는 전략 : IDENTITY
    private Long id;

    //@Column(name = "username") 만약 db에 저장된 name 애트리뷰트의 이름을 username으로 하고싶을 경우 이렇게 지정해준다.
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
