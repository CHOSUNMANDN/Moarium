package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  /**
   * 주어진 이메일에 해당하는 회원을 반환합니다.
   *
   * @param email 이메일
   * @return 주어진 이메일에 해당하는 회원
   */
  Optional<Member> findByEmail(String email);
}