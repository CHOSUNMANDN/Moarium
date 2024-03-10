package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.response.CustomInformation;
import back.springbootdeveloper.seungchan.dto.response.TempMembersDetailInformationResDto;
import back.springbootdeveloper.seungchan.dto.response.TempMembersInformation;
import back.springbootdeveloper.seungchan.dto.response.TempMembersInformationResDto;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberCustomInformation;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.CustomClubApplyInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberInformationRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TempMemberService {

  private final ClubMemberRepository clubMemberRepository;
  private final MemberRepository memberRepository;
  private final ClubMemberInformationRepository clubMemberInformationRepository;

  /**
   * 주어진 클럽 ID에 속하는 모든 임시 멤버들의 정보를 조회합니다.
   *
   * @param clubId 클럽 ID
   * @return 클럽에 속하는 모든 임시 멤버들의 정보를 담은 리스트
   * @throws EntityNotFoundException 멤버를 찾을 수 없는 경우
   */
  public List<TempMembersInformation> findAllTempMemberInformation(final Long clubId) {
    // 클럽에 속하는 모든 임시 멤버들을 가져옵니다.
    List<ClubMember> clubMembersOfTempMembers = clubMemberRepository.findAllByClubIdAndClubGradeId(
        clubId,
        CLUB_GRADE.TEMP_MEMBER.getId());

    // 임시 멤버들의 정보를 담을 리스트를 초기화합니다.
    List<TempMembersInformation> tempMembersInformations = new ArrayList<>();

    // 각 임시 멤버의 정보를 가져와 TempMembersInformation 객체로 매핑한 후 리스트에 추가합니다.
    for (final ClubMember clubMemberOfTempMember : clubMembersOfTempMembers) {
      Member tempMember = memberRepository.findById(clubMemberOfTempMember.getMemberId())
          .orElseThrow(EntityNotFoundException::new);

      tempMembersInformations.add(
          new TempMembersInformation(clubMemberOfTempMember.getClubMemberId(), tempMember));
    }

    return tempMembersInformations;
  }

  /**
   * 주어진 클럽 멤버 ID에 해당하는 임시 멤버의 상세 정보를 조회합니다.
   *
   * @param clubMemberId 클럽 멤버 ID
   * @return 임시 멤버의 상세 정보를 담은 TempMembersDetailInformationResDto 객체
   * @throws EntityNotFoundException 멤버나 클럽 멤버 정보를 찾을 수 없는 경우
   */
  public TempMembersDetailInformationResDto getTempMembersDetailInformationResDto(
      final Long clubMemberId) {
    // 클럽 멤버 ID에 해당하는 ClubMember를 가져옵니다.
    ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
        .orElseThrow(EntityNotFoundException::new);

    // ClubMember에 속하는 Member를 가져옵니다.
    Member member = memberRepository.findById(clubMember.getMemberId())
        .orElseThrow(EntityNotFoundException::new);

    // ClubMember에 속하는 ClubMemberInformation을 가져옵니다.
    ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(
        clubMember.getClubMemberInformationId()).orElseThrow(EntityNotFoundException::new);

    // ClubMemberInformation에 속하는 ClubMemberCustomInformation과 CustomClubApplyInformation을 가져와 CustomInformation으로 매핑합니다.
    List<ClubMemberCustomInformation> clubMemberCustomInformations = clubMemberInformation.getClubMemberCustomInformations();
    List<CustomInformation> customInformations = new ArrayList<>();
    for (final ClubMemberCustomInformation clubMemberCustomInformation : clubMemberCustomInformations) {
      CustomClubApplyInformation customClubApplyInformation = clubMemberCustomInformation.getCustomClubApplyInformation();

      customInformations.add(
          new CustomInformation(clubMemberCustomInformation, customClubApplyInformation));
    }

    // TempMembersDetailInformationResDto 객체를 생성하여 반환합니다.
    return new TempMembersDetailInformationResDto(clubMember.getClubMemberId(), member,
        customInformations);
  }

  /**
   * 주어진 클럽 멤버 ID에 해당하는 임시 멤버를 클럽 멤버로 승인합니다.
   *
   * @param clubMemberId 클럽 멤버 ID
   * @throws EntityNotFoundException 클럽 멤버를 찾을 수 없는 경우
   */
  @Transactional
  public void acceptTempMember(final Long clubMemberId) {
    // 주어진 클럽 멤버 ID에 해당하는 ClubMember를 가져옵니다.
    ClubMember targetClubMember = clubMemberRepository.findById(clubMemberId)
        .orElseThrow(EntityNotFoundException::new);

    // ClubMember의 클럽 등급을 멤버로 업데이트합니다.
    targetClubMember.updateClubGradeId(CLUB_GRADE.MEMBER);
  }

}
