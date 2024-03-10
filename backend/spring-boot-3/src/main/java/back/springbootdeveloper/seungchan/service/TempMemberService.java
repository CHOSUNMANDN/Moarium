package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.response.TempMembersInformation;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
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
}
