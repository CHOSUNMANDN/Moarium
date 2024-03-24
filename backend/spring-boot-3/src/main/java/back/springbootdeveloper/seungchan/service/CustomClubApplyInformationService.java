package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.response.CustomInformation;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubControl;
import back.springbootdeveloper.seungchan.entity.CustomClubApplyInformation;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.repository.CustomClubApplyInformationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomClubApplyInformationService {

  private final ClubRepository clubRepository;

  /**
   * 주어진 clubId에 해당하는 Club의 CustomInformation 목록을 조회합니다.
   *
   * @param clubId Club의 ID
   * @return CustomInformation 객체의 리스트
   * @throws EntityNotFoundException 주어진 clubId에 해당하는 Club을 찾을 수 없는 경우
   */
  public List<CustomInformation> findAllCustomInformationByClubId(final Long clubId) {
    Club club = clubRepository.findById(clubId).orElseThrow(EntityNotFoundException::new);
    ClubControl clubControl = club.getClubControl();
    List<CustomClubApplyInformation> customClubApplyInformations = clubControl.getCustomClubApplyInformations();

    return customClubApplyInformations.stream()
        .map(CustomInformation::new)
        .collect(Collectors.toList());
  }
}
