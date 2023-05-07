package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
// Optional - java8에 들어간 기능으로 뒤에서 보충
// null 반환보다 Optional 이용하여 반환 !
