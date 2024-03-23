package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubGrade;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubMemberService {

  private final ClubMemberRepository clubMemberRepository;

  /**
   * 주어진 Club과 Member에 대해 이미 Club에 가입 신청을 했는지 여부를 확인합니다.
   *
   * @param targetClub  가입 신청 여부를 확인할 Club 객체
   * @param loginMember 가입 신청 여부를 확인할 Member 객체
   * @return 이미 가입 신청을 했으면 true, 아니면 false
   */
  public Boolean isAlreadyApply2Club(final Club targetClub, final Member loginMember,
      final CLUB_GRADE clubGrade) {
    // 해당 Club과 Member에 대한 가입 신청 정보를 데이터베이스에서 조회하여 결과를 반환합니다.
    ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberIdAndClubGradeId(
        targetClub.getClubId(),
        loginMember.getMemberId(), clubGrade.getId()).orElse(null);
    return clubMember != null;
  }

  /**
   * 주어진 Club과 Member가 이미 Club에 등록되어 있는지를 확인합니다.
   *
   * @param targetClub  등록 여부를 확인할 Club 객체
   * @param loginMember 등록 여부를 확인할 Member 객체
   * @return 이미 등록되어 있으면 true, 아니면 false
   */
  public Boolean isAlreadyRegistration2Club(final Club targetClub, final Member loginMember) {
    // ClubMember를 조회하여 가져옵니다. TEMP_MEMBER를 제외합니다.
    ClubMember clubMember = clubMemberRepository.findByClubIdExcludeTempMember(
            targetClub.getClubId(), loginMember.getMemberId(), CLUB_GRADE.TEMP_MEMBER.getId())
        .orElse(null);

    // ClubMember가 null이 아니면 이미 등록되어 있는 상태입니다.
    return clubMember != null;
  }
}
