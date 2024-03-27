package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.constant.entity.FAVORITE_CHECK;
import back.springbootdeveloper.seungchan.dto.response.ClubFindInformation;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberInformationRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

  private final ClubMemberRepository clubMemberRepository;
  private final ClubRepository clubRepository;
  private final MemberRepository memberRepository;
  private final ImageService imageService;
  private final ClubMemberInformationRepository clubMemberInformationRepository;

  /**
   * 클럽 멤버의 ID를 기반으로 해당 멤버가 속한 클럽의 이름을 반환합니다.
   *
   * @param clubMemberId 클럽 멤버의 고유 식별자
   * @return 클럽 이름
   * @throws EntityNotFoundException 지정된 멤버 또는 클럽을 찾을 수 없을 때 발생하는 예외
   */
  public String getClubName(Long clubMemberId) {
    ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
        .orElseThrow(EntityNotFoundException::new);
    Club club = clubRepository.findById(clubMember.getClubId())
        .orElseThrow(EntityNotFoundException::new);

    return club.getClubName();
  }

  /**
   * 주어진 클럽 이름이 이미 존재하는지 여부를 확인합니다.
   *
   * @param targetClubName 확인할 클럽 이름
   * @return 클럽 이름이 이미 존재하면 true, 그렇지 않으면 false
   */
  public Boolean isDuplicationClubName(final String targetClubName) {
    List<Club> clubs = clubRepository.findAll();

    return clubs.stream().anyMatch(club -> club.isSameName(targetClubName));
  }

  /**
   * 주어진 clubId에 해당하는 Club의 이름을 조회합니다.
   *
   * @param clubId Club의 ID
   * @return Club의 이름
   * @throws EntityNotFoundException 주어진 clubId에 해당하는 Club을 찾을 수 없는 경우
   */
  public String getClubNameByClubId(final Long clubId) {
    Club club = clubRepository.findById(clubId)
        .orElseThrow(EntityNotFoundException::new);

    return club.getClubName();
  }

  /**
   * 주어진 clubId에 해당하는 Club을 조회합니다.
   *
   * @param clubId Club의 ID
   * @return 주어진 clubId에 해당하는 Club 객체
   * @throws EntityNotFoundException 주어진 clubId에 해당하는 Club을 찾을 수 없는 경우
   */
  public Club findByClubId(final Long clubId) {
    return clubRepository.findById(clubId).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * 현재 로그인한 회원이 참여한 클럽에 대한 정보를 가져옵니다.
   *
   * @param loginMemberId 현재 로그인한 회원의 ID
   * @return 현재 로그인한 회원이 참여한 클럽의 정보 목록
   */
  public List<ClubFindInformation> getClubFavoriteFindInformations(final Long loginMemberId) {
    List<ClubMember> joinClubMembers = clubMemberRepository.findAllByMemberId(loginMemberId);
    List<ClubFindInformation> clubFindInformations = new ArrayList<>();

    // loginMember의 참여한 클럽의 리스트
    for (final ClubMember joinClubMember : joinClubMembers) {
      // 클럽의 즐겨찾기 여부 가져오기
      ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(
          joinClubMember.getClubMemberInformationId()).orElseThrow(EntityNotFoundException::new);
      FAVORITE_CHECK favoriteCheck = clubMemberInformation.getFavoriteCheck();

      if (FAVORITE_CHECK.CHECK.is(favoriteCheck)) {
        addClubFindInformations(clubFindInformations, joinClubMember, favoriteCheck);
      }
    }

    return clubFindInformations;
  }

  /**
   * 클럽 찾기 정보 리스트에 클럽 정보를 추가합니다.
   *
   * @param clubFindInformations 클럽 찾기 정보 리스트
   * @param joinClubMember       가입한 클럽 멤버
   * @param favoriteCheck        즐겨찾기 여부
   */
  private void addClubFindInformations(final List<ClubFindInformation> clubFindInformations,
      final ClubMember joinClubMember, final FAVORITE_CHECK favoriteCheck) {
    Club club = clubRepository.findById(joinClubMember.getClubId())
        .orElseThrow(EntityNotFoundException::new);
    ClubMember leaderClubMember = clubMemberRepository.findLeaderByClubIdAndLeaderId(
        club.getClubId(),
        CLUB_GRADE.LEADER.getId()); // 리더의 정보를 조회합니다.
    Member leaderMember = memberRepository.findById(leaderClubMember.getMemberId())
        .orElseThrow(EntityNotFoundException::new);
    // 클럽의 프로필 이미지를 Base64 형식으로 가져옵니다.
    String clubProfileImage = imageService.getClubProfileImagesAsBase64(club.getClubName());
    List<ClubMember> clubMemberIncludeClubs = clubMemberRepository.findAllByClubId(
        club.getClubId());

    // 반환하는 리스트에 클럽의 관련 정보 add
    clubFindInformations.add(
        ClubFindInformation.builder()
            .clubId(club.getClubId())
            .clubProfileImage(clubProfileImage)
            .clubName(club.getClubName())
            .clubLeaderName(leaderMember.getFullName())
            .numberMember(String.valueOf(clubMemberIncludeClubs.size()))
            .favoriteCheck(favoriteCheck.getState())
            .build()
    );
  }
}
