package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.response.ClubIntroductionInformationResDto;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubIntroductionInformationService {

  private final ClubRepository clubRepository;
  private final ClubMemberRepository clubMemberRepository;
  private final MemberRepository memberRepository;
  private final ImageService imageService;

  public ClubIntroductionInformationResDto getClubIntroductionInformationResDto(final Long clubId) {
    Club club = clubRepository.findById(clubId).orElseThrow(EntityNotFoundException::new);
    ClubMember leaderClubMember = clubMemberRepository.findLeaderByClubIdAndLeaderId(clubId,
        CLUB_GRADE.LEADER.getId());
    Member leaderMember = memberRepository.findById(leaderClubMember.getMemberId()).orElseThrow();

    List<byte[]> clubInformationImages = imageService.getClubInformationImages(club.getClubName());

    return ClubIntroductionInformationResDto.builder()
        .clubName(club.getClubName())
        .clubIntroduction(club.getClubIntroduce())
        .clubLeaderName(leaderMember.getFullName())
        .clubInformationImages(clubInformationImages)
        .build();
  }
}
