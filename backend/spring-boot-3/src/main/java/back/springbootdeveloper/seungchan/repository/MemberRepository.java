package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}