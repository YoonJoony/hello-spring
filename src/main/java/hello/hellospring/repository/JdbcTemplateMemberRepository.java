package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) { //인젝션을 빈에서 받을 수 있는게 아니고 DataSource를 받아야 함.
        this.jdbcTemplate = new JdbcTemplate(dataSource);        //왜냐? Data Access 템플릿이니 데이타 접근을 필요로 하니까?
    }


    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate); //SimpleJdbcInsert 객체는 jdbcTemplate를 주입받아 생성되는 인스턴스.
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        //usingGeneratedKeyColumns : auto_increment를 통해 생성된 값이, "id"(칼럼명)에 자동으로 입력
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        //테이블 명 : member , pk(primary key) : id, name? 만 있으면 insert문을 만들 수 있다.

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); //MapSql... : Map으로 파라미터 전달
                                //작업 수행과 동시에 자동 생성된 PK를 반환한다.
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select *from member where id = ?", memberRowMapper());//?랑 id맵핑
        return result.stream().findAny(); //stream 하여 List 내부 요소를 전부 꺼낸 뒤, findany로 요소를 담아 리턴
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    private RowMapper<Member> memberRowMapper() { //RowMapper는 ResultSet에서 데이터를 읽어와 Member객체로 변환해 주는 기능
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id")); //id에 rs(db 통신으로 얻은 결과)의 id값 할당
            member.setName(rs.getString("name")); //얘는 그 name 할당
            return member;
        };
    }
}
