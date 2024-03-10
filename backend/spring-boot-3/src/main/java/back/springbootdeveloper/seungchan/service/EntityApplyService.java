package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.ANONYMITY;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.request.ApplyMemberToClubReqDto;
import back.springbootdeveloper.seungchan.dto.request.CustomInformationReqForm;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.*;
import jakarta.validation.Valid;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Club 등록, Club 지원, ClubArticle 생성을 담당하는 클래스입니다.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EntityApplyService {

  private final ClubRepository clubRepository;
  private final AttendanceStateRepository attendanceSateRepository;
  private final ClubMemberRepository clubMemberRepository;
  private final ClubMemberInformationRepository clubMemberInformationRepository;
  private final ClubGradeRepository clubGradeRepository;
  private final ClubArticleRepository clubArticleRepository;
  private final ImageService imageService;
  private final ClubMemberCustomInformationRepository clubMemberCustomInformationRepository;

  public void applyMember2Club(final @Valid ApplyMemberToClubReqDto applyMemberToClubReqDto,
      final Long clubId, final Long memberId) {
    // clubMemberInformation
    ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId).get();
    ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(
        clubMember.getClubMemberInformationId()).get();

    // club
    Club club = clubRepository.findById(clubId).get();
    ClubControl clubControl = club.getClubControl();
    List<CustomClubApplyInformation> customClubApplyInformations = clubControl.getCustomClubApplyInformations();

    List<ClubMemberCustomInformation> clubMemberCustomInformations = new ArrayList<>();
    for (int i = 0; i < customClubApplyInformations.size(); i++) {
      clubMemberCustomInformations.add(ClubMemberCustomInformation.builder()
          .customContent("커스텀에 대한 답변")
          .build());
    }

    for (int i = 0; i < customClubApplyInformations.size(); i++) {
      clubMemberCustomInformations.get(i).setClubMemberInformation(clubMemberInformation);
      customClubApplyInformations.get(i)
          .addClubMemberCustomInformations(clubMemberCustomInformations.get(i));
    }
    clubRepository.save(club);

    for (int i = 0; i < customClubApplyInformations.size(); i++) {
      clubMemberInformation
          .addClubMemberCustomInformations(clubMemberCustomInformations.get(i));
    }

    clubMemberInformationRepository.save(clubMemberInformation);
  }

  /**
   * 기밀 클럽 게시글을 적용합니다.
   *
   * @param title        게시글 제목
   * @param content      게시글 내용
   * @param clubMemberId 클럽 회원 ID
   * @param anonymity    익명성 (ANONYMITY.ANONYMOUS 또는 ANONYMITY.REAL_NAME)
   * @return 새로운 클럽 게시글이 저장된 Optional 객체
   */
  @Transactional
  public Optional<ClubArticle> applyClubArticleConfidential(String title, String content,
      Long clubMemberId, String anonymity) {
    // 주어진 익명성에 따라 클럽 게시글 생성
    if (ANONYMITY.ANONYMOUS.is(anonymity)) {
      return Optional.of(clubArticleRepository.save(
          createClubArticle(title, content, CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL, clubMemberId,
              ANONYMITY.ANONYMOUS)));
    } else {
      return Optional.of(clubArticleRepository.save(
          createClubArticle(title, content, CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL, clubMemberId,
              ANONYMITY.REAL_NAME)));
    }
  }

  /**
   * 자유 게시글 또는 제안 게시글을 적용합니다.
   *
   * @param title          게시글 제목
   * @param content        게시글 내용
   * @param clubMemberId   클럽 회원 ID
   * @param classification 게시글 분류 (CLUB_ARTICLE_CLASSIFICATION.SUGGESTION 또는
   *                       CLUB_ARTICLE_CLASSIFICATION.FREEDOM)
   * @return 새로운 클럽 게시글이 저장된 Optional 객체
   */
  @Transactional
  public Optional<ClubArticle> applyClubArticleFreeAndSuggestion(String title, String content,
      Long clubMemberId, String classification) {
    // 주어진 익명성에 따라 클럽 게시글 생성
    if (CLUB_ARTICLE_CLASSIFICATION.SUGGESTION.is(classification)) {
      return Optional.of(clubArticleRepository.save(
          createClubArticle(title, content, CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, clubMemberId,
              ANONYMITY.REAL_NAME)));
    }
    if (CLUB_ARTICLE_CLASSIFICATION.FREEDOM.is(classification)) {
      return Optional.of(clubArticleRepository.save(
          createClubArticle(title, content, CLUB_ARTICLE_CLASSIFICATION.FREEDOM, clubMemberId,
              ANONYMITY.REAL_NAME)));
    }

    return null;
  }

  /**
   * 주어진 클럽 게시글 ID, 회원 ID, 댓글 내용 및 익명성을 기반으로 클럽 게시글에 댓글을 추가합니다.
   *
   * @param clubArticleId             클럽 게시글 ID
   * @param memberId                  회원 ID
   * @param clubArticleCommentContent 댓글 내용
   * @param targetAnonymity           익명성
   * @return 클럽 게시글에 댓글이 추가된 Optional<ClubArticle> 객체
   * @throws EntityNotFoundException 엔티티를 찾을 수 없을 때 발생하는 예외
   */
  @Transactional
  public Optional<ClubArticle> addClubArticleComment2ClubArticle(final Long clubArticleId,
      final Long memberId, final String clubArticleCommentContent, final String targetAnonymity) {
    final ClubArticle clubArticle = clubArticleRepository.findById(clubArticleId)
        .orElseThrow(EntityNotFoundException::new);

    ANONYMITY anonymity = getAnonymity(targetAnonymity);

    ClubArticleComment clubArticleComment = ClubArticleComment.builder()
        .content(clubArticleCommentContent)
        .memberId(memberId)
        .anonymity(anonymity)
        .build();

    clubArticle.addClubArticleComment(clubArticleComment);

    return Optional.of(clubArticleRepository.save(clubArticle));
  }

  @Transactional
  public Optional<Club> makeClub(
      final String clubName,
      final String clubIntroduce,
      final MultipartFile clubProfileImageFile,
      final List<MultipartFile> clubIntroduceImageFiles) {

    String clubProfileImageUrl = imageService.getBaseImageUrl();
    if (clubProfileImageFile != null) {
      clubProfileImageUrl = imageService.uploadClubProfileImage(clubName,
          clubProfileImageFile);
    }
    List<String> clubIntroduceImageUrls = new ArrayList<>(List.of(imageService.getBaseImageUrl()));
    if (clubIntroduceImageFiles != null) {
      clubIntroduceImageUrls = imageService.uploadClubIntroduceImageUrls(
          clubName, clubIntroduceImageFiles);
    }
    final Club club = createClub(clubName, clubIntroduce, clubProfileImageUrl);
    final ClubControl clubControl = createClubControl();

    // Club - AttendanceNumber
    club.addAttendanceNumber(new AttendanceNumber());
    // Club - ClubIntroduceImages
    clubIntroduceImageUrls.forEach(
        clubIntroduceImageUrl -> club.addClubIntroduceImage(
            new ClubIntroduceImage(clubIntroduceImageUrl)));
    // Club - ClubControl
    club.setClubControl(clubControl);

    return Optional.of(clubRepository.save(club));
  }

  /**
   * 팀의 리더가 만든 팀에 바로 지원을 합니다.
   *
   * @param member                ClubMember의 회원 정보
   * @param club                  ClubMember
   * @param CLUB_GRADE            ClubMember의 등급
   * @param clubMemberInformation ClubMember의 추가 정보
   * @return 생성된 클럽 멤버
   */
  @Transactional
  public Optional<ClubMember> applyLeaderClub(Member member, Club club, CLUB_GRADE CLUB_GRADE,
      ClubMemberInformation clubMemberInformation) {
    return applyClub(member, club, CLUB_GRADE, clubMemberInformation);
  }

  /**
   * 임시 멤버가 팀에 지원을 합니다.
   *
   * @param member     ClubMember의 회원 정보
   * @param club       ClubMember
   * @param CLUB_GRADE ClubMember의 등급
   * @return 생성된 클럽 멤버
   */
  @Transactional
  public Optional<ClubMember> applyTempMemberClub(Member member, Club club, CLUB_GRADE CLUB_GRADE,
      ApplyMemberToClubReqDto applyMemberToClubReqDto) {
    ClubMemberInformation clubMemberInformation = ClubMemberInformation.builder()
        .introduce(applyMemberToClubReqDto.getSelfIntroduction())
        .build();
    ClubMember clubMember = applyClub(member, club, CLUB_GRADE, clubMemberInformation).orElseThrow(
        EntityNotFoundException::new);
    // club에 해당하는 정보
    ClubControl clubControl = club.getClubControl();
    List<CustomClubApplyInformation> customClubApplyInformations = clubControl.getCustomClubApplyInformations();

    // clubMember에 해당하는 정보
    List<CustomInformationReqForm> customInformations = applyMemberToClubReqDto.getCustomInformations();

    // club, clubMember의 관계 매핑
    for (final CustomClubApplyInformation customClubApplyInformation : customClubApplyInformations) {
      Long customClubApplyInformationId = customClubApplyInformation.getCustomClubApplyInformationId();

      for (final CustomInformationReqForm customInformation : customInformations) {
        Long customInformationId = customInformation.getCustomInformationId();

        // id 확인 검증
        if (customClubApplyInformationId.equals(customInformationId)) {
          ClubMemberCustomInformation clubMemberCustomInformation = ClubMemberCustomInformation.builder()
              .customContent(customInformation.getCustomContent())
              .build();
          customClubApplyInformation.addClubMemberCustomInformations(clubMemberCustomInformation);
          clubMemberInformation.addClubMemberCustomInformations(clubMemberCustomInformation);
          break;
        }
      }
    }

    clubRepository.save(club);
    clubMemberInformationRepository.save(clubMemberInformation);

    return Optional.ofNullable(clubMember);
  }

  /**
   * 주어진 익명성 문자열을 기반으로 ANONYMITY 열거형을 가져옵니다.
   *
   * @param targetAnonymity 대상 익명성 문자열
   * @return 대상 익명성에 해당하는 ANONYMITY 열거형
   */
  private ANONYMITY getAnonymity(String targetAnonymity) {
    ANONYMITY anonymity;
    if (ANONYMITY.REAL_NAME.is(targetAnonymity)) {
      anonymity = ANONYMITY.REAL_NAME;
    } else {
      anonymity = ANONYMITY.ANONYMOUS;
    }

    return anonymity;
  }

  /**
   * 팀에 지원을 합니다.
   *
   * @param member                ClubMember의 회원 정보
   * @param club                  ClubMember
   * @param CLUB_GRADE            ClubMember의 등급
   * @param clubMemberInformation ClubMember의 추가 정보
   * @return 생성된 클럽 멤버
   */
  private Optional<ClubMember> applyClub(final Member member, final Club club,
      final CLUB_GRADE CLUB_GRADE, final ClubMemberInformation clubMemberInformation) {
    AttendanceState attendanceSate = attendanceSateRepository.save(createAttendanceState());
    ClubGrade clubGrade = clubGradeRepository.findByClubGrade(CLUB_GRADE)
        .orElseThrow(EntityNotFoundException::new);
    ClubMemberInformation entityClubMemberInformation = clubMemberInformationRepository.save(
        clubMemberInformation);

    // TODO: 3/7/24 지원 회원들 임시 회원으로 등록
    // ClubMember
    //   - Member, Club, ClubMemberInformation, AttendanceSate, ClubGrade
    ClubMember clubMember = creatClubMember(member, club, entityClubMemberInformation,
        attendanceSate, clubGrade);

    return Optional.of(clubMemberRepository.save(clubMember));
  }

  /**
   * AttendanceState 생성
   *
   * @return AttendanceState 반환
   */
  private AttendanceState createAttendanceState() {
    AttendanceState attendanceSate = new AttendanceState();
    AttendanceCheckTime attendanceCheckTime = new AttendanceCheckTime();
    AttendanceWeekDate attendanceWeekDate = new AttendanceWeekDate();
    VacationToken vacationToken = new VacationToken();

    // attendanceSate - attendanceCheckTime
    attendanceSate.setAttendanceCheckTime(attendanceCheckTime);
    // attendanceSate - attendanceWeekDate
    attendanceSate.addAttendanceWeekDates(attendanceWeekDate);
    // attendanceSate - vacationToken
    attendanceSate.addtVacationToken(vacationToken);

    return attendanceSate;
  }

  /**
   * ClubControl 생성
   *
   * @return ClubControl 반환
   */
  private ClubControl createClubControl() {
    ClubControl clubControl = new ClubControl();
    VacationTokenControl vacationTokenControl = new VacationTokenControl();
    AttendanceWeek attendanceWeek = new AttendanceWeek();

    // ClubControl - VacationTokenControl
    clubControl.setVacationTokenControl(vacationTokenControl);
    // ClubControl - AttendanceWeek
    clubControl.setAttendanceWeek(attendanceWeek);

    return clubControl;
  }

  /**
   * 주어진 정보를 사용하여 Club 객체를 생성합니다.
   *
   * @param clubName         Club 이름
   * @param clubIntroduce    Club 자기소개
   * @param clubProfileImage Club 프로필 이미지 URL
   * @return 생성된 Club 객체
   */
  private Club createClub(String clubName, String clubIntroduce, String clubProfileImage) {
    return Club.builder()
        .clubName(clubName)
        .clubIntroduce(clubIntroduce)
        .clubProfileImage(clubProfileImage)
        .build();
  }

  /**
   * 주어진 정보를 사용하여 클럽 ClubMember 객체를 생성합니다.
   *
   * @param member                ClubMember 멤버의 회원 정보
   * @param club                  Club 정보
   * @param clubMemberInformation Club 멤버의 추가 정보
   * @param attendanceSate        Club 멤버의 출석 상태
   * @param clubGrade             Club 멤버의 등급
   * @return 생성된 ClubMember 멤버 객체
   */
  private ClubMember creatClubMember(Member member, Club club,
      ClubMemberInformation clubMemberInformation, AttendanceState attendanceSate,
      ClubGrade clubGrade) {
    return ClubMember.builder()
        .memberId(member.getMemberId())
        .clubMemberInformationId(clubMemberInformation.getClubMemberInformationId())
        .clubId(club.getClubId())
        .clubGradeId(clubGrade.getClubGradeId())
        .attendanceSateId(attendanceSate.getAttendanceStateId())
        .clubMemberInformationId(clubMemberInformation.getClubMemberInformationId())
        .build();
  }


  /**
   * ClubArticle 생성
   *
   * @param title          ClubArticle 제목
   * @param content        ClubArticle 내용
   * @param classification ClubArticle 분류
   * @param clubMemberId   ClubArticle 작성한 ClubMember ID
   * @return 생성된 ClubArticle 객체 반환
   */
  private ClubArticle createClubArticle(String title, String content,
      CLUB_ARTICLE_CLASSIFICATION classification, Long clubMemberId, ANONYMITY anonymity) {
    return ClubArticle.builder()
        .title(title)
        .content(content)
        .classification(classification)
        .clubMemberId(clubMemberId)
        .anonymity(anonymity)
        .build();
  }
}
