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

    /**
     * 클럽 멤버의 상태를 토글하고, 이전 상태와 현재 상태가 다른지 여부를 반환합니다.
     *
     * @param clubMemberId 클럽 멤버의 고유 식별자
     * @return 이전 상태와 현재 상태가 다른지 여부를 나타내는 Boolean 값
     * @throws EntityNotFoundException 지정된 멤버를 찾을 수 없을 때 발생하는 예외
     */
    @Transactional
    public Boolean toggleMemberAndDormantOfClubGrade(Long clubMemberId) {
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(EntityNotFoundException::new);
        Long beforeClubGradeId = clubMember.getClubGradeId();

        // 토글기능, Member <=> Dormant
        toggleMemberAndDormant(clubMember);
        // 업데이트 적용 및 확인
        ClubMember updateClubMember = clubMemberRepository.save(clubMember);
        // 이전 상태와 현재 상태가 다른지 여부를 반환합니다.
        return isNotSame(beforeClubGradeId, updateClubMember.getClubGradeId());
    }

    /**
     * 클럽 멤버의 상태를 토글합니다.
     *
     * @param clubMember 상태를 변경할 클럽 멤버
     */
    private void toggleMemberAndDormant(ClubMember clubMember) {
        // 클럽 멤버의 현재 상태를 확인하고, MEMBER 상태일 경우 DORMANT로 변경하고, 그렇지 않으면 MEMBER로 변경합니다.
        if (isSame(CLUB_GRADE.MEMBER.getId(), clubMember.getClubGradeId())) {
            clubMember.updateClubGradeId(Long.valueOf(CLUB_GRADE.DORMANT.getId()));
        } else {
            clubMember.updateClubGradeId(Long.valueOf(CLUB_GRADE.MEMBER.getId()));
        }
    }

    private boolean isNotSame(Long longNumber_1, Long longNumber_2) {
        return longNumber_1 != longNumber_2;
    }

    private boolean isSame(Integer clubGradeId, Long updateClubMemberGetClubGradeId) {
        return Long.valueOf(clubGradeId) == updateClubMemberGetClubGradeId;
    }
}
