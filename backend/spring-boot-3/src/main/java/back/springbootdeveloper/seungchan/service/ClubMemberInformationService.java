package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.MESSAGE;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubMemberInformationService {

  /**
   * 클럽의 리더 클럽 멤버 정보를 생성합니다.
   *
   * @param saveClub 클럽 정보
   * @param myMember 현재 멤버 정보
   * @return 생성된 리더 클럽 멤버 정보
   */
  public ClubMemberInformation makeLeaderClubMemberInformation(final Club saveClub,
      final Member myMember) {
    final String leaderClubMemberInformation = MESSAGE.LEADER_CLUB_MEMBER_INFORMATION(
        saveClub.getClubName(), myMember.getFullName());

    return ClubMemberInformation.builder()
        .introduce(leaderClubMemberInformation)
        .build();
  }
}
