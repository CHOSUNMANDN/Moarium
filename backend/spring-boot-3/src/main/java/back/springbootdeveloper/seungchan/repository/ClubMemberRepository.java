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
    List<ClubMember> findAllByClubIdAndClubGradeId(Long clubId, Integer clubGradeId);


    /**
     * 지정된 클럽 ID에 해당하는 모든 클럽 멤버를 검색하되, 휴면 상태가 아닌 멤버만 반환합니다.
     *
     * @param clubId    검색할 클럽의 ID
     * @param dormantId 휴면 상태를 나타내는 ID
     * @return 휴면 상태가 아닌 모든 클럽 멤버의 목록
     */
    @Query("SELECT cm FROM ClubMember cm WHERE cm.clubId = :clubId AND cm.clubGradeId <> :dormantId")
    List<ClubMember> findAllByClubIdExcludeDormant(@Param("clubId") Long clubId, @Param("dormantId") Integer dormantId);

    Optional<ClubMember> findByMemberId(Long memberId);

    Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId);

    List<ClubMember> findAllByMemberId(Long memberId);
}