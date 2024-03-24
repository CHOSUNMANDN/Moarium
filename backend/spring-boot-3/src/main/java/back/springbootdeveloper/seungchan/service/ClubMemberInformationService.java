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

  public ClubMemberInformation makeLeaderClubMemberInformation(final Club saveClub,
      final Member myMember) {
    final String leaderClubMemberInformation = MESSAGE.LEADER_CLUB_MEMBER_INFORMATION(
        saveClub.getClubName(), myMember.getFullName());

    return ClubMemberInformation.builder()
        .introduce(leaderClubMemberInformation)
        .build();
  }
}
