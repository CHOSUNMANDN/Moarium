package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

  /**
   * 지정된 클럽 ID와 클럽 등급 ID에 해당하는 클럽 멤버를 반환합니다.
   *
   * @param clubId      클럽의 ID
   * @param clubGradeId 클럽 등급의 ID
   * @return 지정된 클럽 ID와 클럽 등급 ID에 해당하는 클럽 멤버의 목록
   */
  List<ClubMember> findAllByClubIdAndClubGradeId(Long clubId, Integer clubGradeId);


  /**
   * 지정된 클럽 ID에 해당하는 모든 클럽 멤버를 검색하되, 임시, 휴면 상태가 아닌 멤버만 반환합니다.
   *
   * @param clubId       검색할 클럽의 ID
   * @param dormantId    휴면 상태를 나타내는 ID
   * @param tempMemberId 임시 유저의 ID
   * @return 휴면 상태가 아닌 모든 클럽 멤버의 목록
   */
  @Query("SELECT cm FROM ClubMember cm WHERE cm.clubId = :clubId AND cm.clubGradeId <> :dormantId AND cm.clubGradeId <> :tempMemberId")
  List<ClubMember> findAllByClubIdExcludeDormantAndTempMember(@Param("clubId") Long clubId,
      @Param("dormantId") Integer dormantId, @Param("tempMemberId") Integer tempMemberId);

  /**
   * 주어진 회원 ID에 해당하는 클럽 멤버를 반환합니다.
   *
   * @param memberId 회원의 ID
   * @return 주어진 회원 ID에 해당하는 클럽 멤버
   */
  Optional<ClubMember> findByMemberId(Long memberId);

  /**
   * 지정된 클럽 ID와 회원 ID에 해당하는 클럽 멤버를 반환합니다.
   *
   * @param clubId   클럽의 ID
   * @param memberId 회원의 ID
   * @return 지정된 클럽 ID와 회원 ID에 해당하는 클럽 멤버
   */
  Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId);

  /**
   * 주어진 회원 ID에 해당하는 모든 클럽 멤버를 반환합니다.
   *
   * @param memberId 회원의 ID
   * @return 주어진 회원 ID에 해당하는 모든 클럽 멤버
   */
  List<ClubMember> findAllByMemberId(Long memberId);

  /**
   * 지정된 클럽 ID에 해당하는 모든 클럽 멤버를 반환합니다.
   *
   * @param clubId 클럽의 ID
   * @return 지정된 클럽 ID에 해당하는 모든 클럽 멤버
   */
  List<ClubMember> findAllByClubId(Long clubId);

  Optional<ClubMember> findByClubIdAndMemberIdAndClubGradeId(Long clubId, Long memberId,
      Integer id);

  /**
   * 특정 Club에 속한 특정 Member를 제외하고, clubGradeId가 임시 멤버 상태가 아닌 ClubMember를 조회합니다.
   *
   * @param clubId       Club의 ID
   * @param memberId     Member의 ID
   * @param tempMemberId 임시 멤버 상태를 나타내는 clubGradeId
   * @return ClubMember 중 휴면 상태나 임시 멤버 상태가 아닌 ClubMember (Optional)
   */
  @Query("SELECT cm FROM ClubMember cm WHERE cm.clubId = :clubId AND cm.memberId = :memberId  AND cm.clubGradeId <> :tempMemberId")
  Optional<ClubMember> findByClubIdExcludeTempMember(@Param("clubId") Long clubId,
      @Param("memberId") Long memberId, @Param("tempMemberId") Integer tempMemberId);
}