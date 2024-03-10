package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 엔티티에서 컬럼 삭제 담당하는 클래스입니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class EntityDeleteService {

  private final ClubRepository clubRepository;
  private final AttendanceStateRepository attendanceSateRepository;
  private final ClubMemberRepository clubMemberRepository;
  private final ClubMemberInformationRepository clubMemberInformationRepository;
  private final ClubGradeRepository clubGradeRepository;
  private final ClubArticleRepository clubArticleRepository;


  /**
   * 클럽 멤버를 클럽에서 제명합니다.
   *
   * @param clubMemberId 클럽 멤버의 고유 식별자
   * @throws EntityNotFoundException 지정된 멤버, 멤버 정보 또는 출석 상태를 찾을 수 없을 때 발생하는 예외
   */
  public void expulsionMemberFromClub(Long clubMemberId) {
    ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
        .orElseThrow(EntityNotFoundException::new);
    ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(
        clubMember.getClubMemberInformationId()).orElseThrow(EntityNotFoundException::new);
    AttendanceState attendanceState = attendanceSateRepository.findById(
        clubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);

    clubMemberInformationRepository.delete(clubMemberInformation);
    attendanceSateRepository.delete(attendanceState);
    clubMemberRepository.delete(clubMember);
  }
}
