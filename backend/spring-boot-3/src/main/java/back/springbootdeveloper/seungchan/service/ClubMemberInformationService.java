package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.MESSAGE;
import back.springbootdeveloper.seungchan.constant.entity.FAVORITE_CHECK;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberInformationRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubMemberInformationService {

  private final ClubMemberInformationRepository clubMemberInformationRepository;
  private final ClubMemberRepository clubMemberRepository;

  /**
   * 클럽의 리더의 소개 정보 생성
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


  /**
   * 로그인 멤버가 클럽을 즐겨찾기 TOGGLE 기능 CHECK, UN_CHECK
   *
   * @param loginMemberId 로그인 멤버의 ID
   * @param clubId        클럽의 ID
   * @return 즐겨찾기 상태 변경 여부
   */
  @Transactional
  public FAVORITE_CHECK toggleFavorites(final Long loginMemberId, final Long clubId) {
    ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(clubId,
            loginMemberId)
        .orElseThrow(EntityNotFoundException::new);
    ClubMemberInformation targetClubMemberInformation = clubMemberInformationRepository.findById(
        targetClubMember.getClubMemberInformationId()).orElseThrow(EntityNotFoundException::new);
    FAVORITE_CHECK beforeCheck = targetClubMemberInformation.getFavoriteCheck();

    if (FAVORITE_CHECK.CHECK.is(beforeCheck)) {
      targetClubMemberInformation.updateFavoriteCheck(FAVORITE_CHECK.UNCHECK);
      targetClubMemberInformation = clubMemberInformationRepository.save(
          targetClubMemberInformation);
    } else {
      targetClubMemberInformation.updateFavoriteCheck(FAVORITE_CHECK.CHECK);
      targetClubMemberInformation = clubMemberInformationRepository.save(
          targetClubMemberInformation);
    }
    
    return targetClubMemberInformation.getFavoriteCheck();
  }
}
