package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.ClubGrade;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubGradeRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubGradeService {
    private final ClubMemberRepository clubMemberRepository;
    private final ClubGradeRepository clubGradeRepository;


    public ClubGrade findByClubIdAndMemberId(Long clubId, Long memberId) {
        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId).orElseThrow(EntityNotFoundException::new);
        return clubGradeRepository.findById(clubMember.getClubGradeId()).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * 주어진 클럽 멤버의 상태가 휴면 상태인지 확인합니다.
     *
     * @param clubMemberId 확인할 클럽 멤버의 ID
     * @param dormant      휴면 상태를 나타내는 CLUB_GRADE enum 값
     * @return 클럽 멤버의 휴면 상태 여부를 나타내는 Boolean 값. 휴면 상태일 경우 true를 반환하고, 아닐 경우 false를 반환합니다.
     * @throws EntityNotFoundException 지정된 멤버를 찾을 수 없을 때 발생하는 예외
     */
    public Boolean isMemberStatus(Long clubMemberId, CLUB_GRADE dormant) {
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(EntityNotFoundException::new);
        return isSame(dormant.getId(), clubMember.getClubGradeId());
    }

    /**
     * 클럽 멤버의 클럽 등급을 업데이트합니다.
     *
     * @param clubMemberId 업데이트할 클럽 멤버의 ID
     * @param clubGrade    새로운 클럽 등급
     * @return 업데이트가 성공적으로 수행되었는지 여부를 나타내는 Boolean 값
     */
    @Transactional
    public Boolean updateClubGradeOfClubMember(Long clubMemberId, CLUB_GRADE clubGrade) {
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(EntityNotFoundException::new);
        clubMember.updateClubGradeId(Long.valueOf(clubGrade.getId()));
        ClubMember updateClubMember = clubMemberRepository.save(clubMember);

        return isSame(clubGrade.getId(), updateClubMember.getClubGradeId());
    }

    private boolean isSame(Integer clubGradeId, Long updateClubMemberGetClubGradeId) {
        return Long.valueOf(clubGradeId) == updateClubMemberGetClubGradeId;
    }
}
