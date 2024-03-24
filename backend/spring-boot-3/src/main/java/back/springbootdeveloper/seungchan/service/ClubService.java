package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
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
}
