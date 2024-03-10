package back.springbootdeveloper.seungchan.database;

import back.springbootdeveloper.seungchan.constant.entity.ANONYMITY;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.constant.entity.CUSTOM_TYPE;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.repository.*;
import back.springbootdeveloper.seungchan.testutil.TestMakeEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest()
class entityMappingTest {

  private final MemberRepository memberRepository;
  private final ClubMemberRepository clubMemberRepository;
  private final ClubRepository clubRepository;
  private final ClubIntroduceImageRepository clubIntroduceImageRepository;
  private final ClubGradeRepository clubGradeRepository;
  private final ClubArticleRepository clubArticleRepository;
  private final AttendanceNumberRepository attendanceNumberRepository;
  private final ClubControlRepository clubControlRepository;
  private final AttendanceWeekDateRepository attendanceWeekDateRepository;
  private final AttendanceStateRepository attendanceSateRepository;
  private final VacationTokenRepository vacationTokenRepository;
  private final ClubMemberInformationRepository clubMemberInformationRepository;
  private final ClubMemberCustomInformationRepository clubMemberCustomInformationRepository;
  CLUB_ARTICLE_CLASSIFICATION SUGGESTION = CLUB_ARTICLE_CLASSIFICATION.SUGGESTION;
  CLUB_ARTICLE_CLASSIFICATION FREEDOM = CLUB_ARTICLE_CLASSIFICATION.FREEDOM;
  CLUB_ARTICLE_CLASSIFICATION CONFIDENTIAL = CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL;

  @Autowired
  public entityMappingTest(final MemberRepository memberRepository,
      final ClubMemberRepository clubMemberRepository, final ClubRepository clubRepository,
      final ClubIntroduceImageRepository clubIntroduceImageRepository,
      final ClubGradeRepository clubGradeRepository,
      final ClubArticleRepository clubArticleRepository,
      final AttendanceNumberRepository attendanceNumberRepository,
      final ClubControlRepository clubControlRepository,
      final AttendanceWeekDateRepository attendanceWeekDateRepository,
      final AttendanceStateRepository attendanceSateRepository,
      final VacationTokenRepository vacationTokenRepository,
      final ClubMemberInformationRepository clubMemberInformationRepository,
      final ClubMemberCustomInformationRepository clubMemberCustomInformationRepository) {
    this.memberRepository = memberRepository;
    this.clubMemberRepository = clubMemberRepository;
    this.clubRepository = clubRepository;
    this.clubIntroduceImageRepository = clubIntroduceImageRepository;
    this.clubGradeRepository = clubGradeRepository;
    this.clubArticleRepository = clubArticleRepository;
    this.attendanceNumberRepository = attendanceNumberRepository;
    this.clubControlRepository = clubControlRepository;
    this.attendanceWeekDateRepository = attendanceWeekDateRepository;
    this.attendanceSateRepository = attendanceSateRepository;
    this.vacationTokenRepository = vacationTokenRepository;
    this.clubMemberInformationRepository = clubMemberInformationRepository;
    this.clubMemberCustomInformationRepository = clubMemberCustomInformationRepository;
  }

  @Test
  @Disabled
  void 매핑_저장_학습_테스트_1() throws Exception {
    this.clubGradeRepository.save(new ClubGrade(CLUB_GRADE.LEADER));
    this.clubGradeRepository.save(new ClubGrade(CLUB_GRADE.DEPUTY_LEADER));
    this.clubGradeRepository.save(new ClubGrade(CLUB_GRADE.MEMBER));
    this.clubGradeRepository.save(new ClubGrade(CLUB_GRADE.DORMANT));

    데베_저장_팀이름_시작멤버수_끝멤버수_휴면멤버수_테스트(
        0, 0, 29, 10);
    데베_저장_팀이름_시작멤버수_끝멤버수_휴면멤버수_테스트(
        1, 30, 49, 5);
    데베_저장_팀이름_시작멤버수_끝멤버수_휴면멤버수_테스트(
        2, 50, 79, 15);
    데베_저장_팀이름_시작멤버수_끝멤버수_휴면멤버수_테스트(
        3, 80, 89, 3);
    데베_저장_팀이름_시작멤버수_끝멤버수_휴면멤버수_테스트(
        4, 90, 99, 4);
  }

  @Test
  @Disabled
  void 매핑_저장_학습_테스트() throws Exception {
    this.clubGradeRepository.save(new ClubGrade(CLUB_GRADE.LEADER));
    this.clubGradeRepository.save(new ClubGrade(CLUB_GRADE.DEPUTY_LEADER));
    this.clubGradeRepository.save(new ClubGrade(CLUB_GRADE.MEMBER));
    this.clubGradeRepository.save(new ClubGrade(CLUB_GRADE.DORMANT));
  }

  void 데베_저장_팀이름_시작멤버수_끝멤버수_휴면멤버수_테스트(Integer clubNumber, Integer startMemberNumber,
      Integer endMemberNumber, Integer dormantNumber) throws Exception {
    // ================================== Club 1.Club 등록 ============================
    Integer roofStart = startMemberNumber + 2;
    Integer roofEnd = endMemberNumber;
    Club club = applyClub___AttendanceNumber_ClubIntroduceImage(clubNumber);

    Integer leaderNumber = startMemberNumber;
    Integer deputyLeaderNumber = startMemberNumber + 1;

    // ================================== Club 2.AttendanceSate 등록 ============================
    List<AttendanceState> attendanceSates = new ArrayList<>();
    AttendanceState attendanceSate_1 = mapping_AttendanceSate___AttendanceCheckTime_AttendanceWeekDate_VacationToken();
    AttendanceState attendanceSate_2 = mapping_AttendanceSate___AttendanceCheckTime_AttendanceWeekDate_VacationToken();
    for (int i = roofStart; i <= roofEnd; i++) {
      attendanceSates.add(
          mapping_AttendanceSate___AttendanceCheckTime_AttendanceWeekDate_VacationToken());
    }

    // ================================== Club 3.ClubControl 생성 ============================
    ClubControl clubControl = mapping_ClubControl___VacattionTokenControl_AttendanceWeek();

    // ================================== Club 4.Club - ClubControl 등록 ============================
    club = mappring_Club_ClubControl(club, clubControl);

    // ==================================== Club 5.ClubGrade 찾기 시작 ============================
    ClubGrade clubGradeLeader = clubGradeRepository.findByClubGrade(CLUB_GRADE.LEADER).get();
    ClubGrade clubGradeDeputyLeader = clubGradeRepository.findByClubGrade(CLUB_GRADE.DEPUTY_LEADER)
        .get();
    ClubGrade clubGradeMember = clubGradeRepository.findByClubGrade(CLUB_GRADE.MEMBER).get();
    ClubGrade clubGradeDormant = clubGradeRepository.findByClubGrade(CLUB_GRADE.DORMANT).get();

    // ==================================== Club 6.Club의 커스텀 지원 정보 추가. 찾기 시작 ============================
    club = addCustomClubApplyInformation(club);

    // ================================= Member 20.Member 등록 시작 ============================
    List<Member> members = new ArrayList<>();
    Member leaderMember_1 = applyMember(startMemberNumber); // 대표
    Member deputyLeaderMember_2 = applyMember(startMemberNumber + 1); // 부 대표
    for (int i = roofStart; i <= roofEnd; i++) {
      members.add(applyMember(i));
    }
    // ================================= Member 21.ClubMemberInformation 등록 시작 ============================
    List<ClubMemberInformation> clubMemberInformations = new ArrayList<>();
    ClubMemberInformation clubMemberInformation_1 = applyClubMemberInformation(leaderNumber, club);
    ClubMemberInformation clubMemberInformation_2 = applyClubMemberInformation(dormantNumber, club);
    for (int i = roofStart; i <= roofEnd; i++) {
      clubMemberInformations.add(applyClubMemberInformation(i, club));
    }

    // ================================= Club - Member 30.ClubMember 등록 시작 ============================
    List<ClubMember> clubMembers = new ArrayList<>();
    ClubMember clubMember_1 = applyClubMember(leaderMember_1, club, clubGradeLeader,
        attendanceSate_1,
        clubMemberInformation_1);
    ClubMember clubMember_2 = applyClubMember(deputyLeaderMember_2, club, clubGradeDeputyLeader,
        attendanceSate_2,
        clubMemberInformation_2);

    // 일반 유저 등록
    for (int i = 0; i < members.size() - dormantNumber; i++) {
      clubMembers.add(applyClubMember(members.get(i), club, clubGradeMember, attendanceSates.get(i),
          clubMemberInformations.get(i)));
    }

    // 휴면 계정 등록
    for (int i = members.size() - dormantNumber; i < members.size(); i++) {
      clubMembers.add(
          applyClubMember(members.get(i), club, clubGradeDormant, attendanceSates.get(i),
              clubMemberInformations.get(i)));
    }

    // ================================= custom_club_apply_information - club_member_custom_information 31. 커스텀 자기소개 등록 시작 ============================
    // ================================= club_member_information  ====================================================================================
    addClubMemberCustomInformation_To_CustomClubApplyInformation(leaderNumber + 1, club,
        clubMember_1);
    addClubMemberCustomInformation_To_CustomClubApplyInformation(deputyLeaderNumber + 1, club,
        clubMember_2);

    // 일반 유저 등록
    for (int i = 0; i < members.size() - dormantNumber; i++) {
      addClubMemberCustomInformation_To_CustomClubApplyInformation(i + 2 + 1, club,
          clubMembers.get(i));
    }

    // 휴면 계정 등록
    for (int i = members.size() - dormantNumber; i < members.size(); i++) {
      addClubMemberCustomInformation_To_CustomClubApplyInformation(i + members.size() + 2 + 1, club,
          clubMembers.get(i));
    }
    club = clubRepository.save(club);
    // ========================================= 40.ClubArticle - ClubArticleComment 3개 등록 시작 ========================
    // ================================= Club - Member -- ClubArticle 41.ClubArticle 매핑 시작 ============================
    applyClubArticleCommentToClubArticle(SUGGESTION, leaderNumber, clubMember_1);
    applyClubArticleCommentToClubArticle(SUGGESTION, leaderNumber, clubMember_1);
    applyClubArticleCommentToClubArticle(SUGGESTION, leaderNumber, clubMember_1);
    applyClubArticleCommentToClubArticle(FREEDOM, leaderNumber, clubMember_1);
    applyClubArticleCommentToClubArticle(FREEDOM, leaderNumber, clubMember_1);
    applyClubArticleCommentToClubArticle(FREEDOM, leaderNumber, clubMember_1);
    applyClubArticleCommentToClubArticle(SUGGESTION, deputyLeaderNumber, clubMember_2);
    applyClubArticleCommentToClubArticle(SUGGESTION, deputyLeaderNumber, clubMember_2);
    applyClubArticleCommentToClubArticle(SUGGESTION, deputyLeaderNumber, clubMember_2);
    applyClubArticleCommentToClubArticle(FREEDOM, deputyLeaderNumber, clubMember_2);
    applyClubArticleCommentToClubArticle(FREEDOM, deputyLeaderNumber, clubMember_2);
    applyClubArticleCommentToClubArticle(FREEDOM, deputyLeaderNumber, clubMember_2);
    applyClubArticleCommentToClubArticle(CONFIDENTIAL, deputyLeaderNumber, clubMember_2);
    applyClubArticleCommentToClubArticle(CONFIDENTIAL, deputyLeaderNumber, clubMember_2);
    applyClubArticleCommentToClubArticle(CONFIDENTIAL, deputyLeaderNumber, clubMember_2);

    for (int i = 0; i < members.size(); i++) {
      applyClubArticleCommentToClubArticle(SUGGESTION, i + 3, clubMembers.get(i));
      applyClubArticleCommentToClubArticle(SUGGESTION, i + 3, clubMembers.get(i));
      applyClubArticleCommentToClubArticle(SUGGESTION, i + 3, clubMembers.get(i));
    }

    for (int i = 0; i < members.size(); i++) {
      applyClubArticleCommentToClubArticle(FREEDOM, i + 3, clubMembers.get(i));
      applyClubArticleCommentToClubArticle(FREEDOM, i + 3, clubMembers.get(i));
      applyClubArticleCommentToClubArticle(FREEDOM, i + 3, clubMembers.get(i));
    }

    for (int i = 0; i < members.size(); i++) {
      applyClubArticleCommentToClubArticle(CONFIDENTIAL, i + 3, clubMembers.get(i));
      applyClubArticleCommentToClubArticle(CONFIDENTIAL, i + 3, clubMembers.get(i));
      applyClubArticleCommentToClubArticle(CONFIDENTIAL, i + 3, clubMembers.get(i));
    }
  }

  private void addClubMemberCustomInformation_To_CustomClubApplyInformation(final Integer number,
      Club club, ClubMember clubMember) {
    ClubControl clubControl = club.getClubControl();
    List<CustomClubApplyInformation> customClubApplyInformations = clubControl.getCustomClubApplyInformations();
    ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(
        clubMember.getClubMemberInformationId()).get();

    for (final CustomClubApplyInformation customClubApplyInformation : customClubApplyInformations) {
      ClubMemberCustomInformation clubMemberCustomInformation = clubMemberCustomInformationRepository.save(
          ClubMemberCustomInformation.builder()
              .customContent("클럽에 지원하는 커스텀 지원폼에 대한 답변 " + number)
              .build()
      );
      customClubApplyInformation.addClubMemberCustomInformations(clubMemberCustomInformation);
    }
  }

  private Club addCustomClubApplyInformation(final Club club) {
    ClubControl clubControl = club.getClubControl();

    List<CustomClubApplyInformation> customClubApplyInformations = new ArrayList<>();

    customClubApplyInformations.add(
        CustomClubApplyInformation.builder()
            .customContent("테스트 커스텀 지원서 글자 양식")
            .customType(CUSTOM_TYPE.TEXT)
            .build()
    );
    customClubApplyInformations.add(
        CustomClubApplyInformation.builder()
            .customContent("테스트 커스텀 지원서 체크 양식")
            .customType(CUSTOM_TYPE.CHECK)
            .build()
    );

    for (final CustomClubApplyInformation customClubApplyInformation : customClubApplyInformations) {
      clubControl.addClubMemberCustomInformations(customClubApplyInformation);
    }

    return clubRepository.save(club);
  }

  private ClubMember applyClubMember(Member member, Club club, ClubGrade clubGrade,
      AttendanceState attendanceSate, ClubMemberInformation clubMemberInformation) {
    ClubMember clubMember = TestMakeEntity.createSampleClubMember(member.getMemberId(),
        club.getClubId(),
        clubGrade.getClubGradeId(), attendanceSate.getAttendanceStateId(),
        clubMemberInformation.getClubMemberInformationId());

    return clubMemberRepository.save(clubMember);
  }

  private ClubMemberInformation applyClubMemberInformation(Integer number, Club club) {
    ClubMemberInformation clubMemberInformation = TestMakeEntity.createSampleClubMemberInformation(
        number);

    return clubMemberInformationRepository.save(clubMemberInformation);
  }

  /**
   * AttendanceState 매핑 관계
   *
   * @return
   */
  private AttendanceState mapping_AttendanceSate___AttendanceCheckTime_AttendanceWeekDate_VacationToken() {
//        AttendanceState attendanceSate = attendanceSateRepository.save(new AttendanceState());
    AttendanceState attendanceSate = new AttendanceState();
    AttendanceCheckTime attendanceCheckTime = new AttendanceCheckTime();
    AttendanceWeekDate attendanceWeekDate = new AttendanceWeekDate();
    VacationToken vacationToken = new VacationToken();

    attendanceSate.setAttendanceCheckTime(attendanceCheckTime);

    attendanceSate.addAttendanceWeekDates(attendanceWeekDate);
    attendanceSate.addtVacationToken(vacationToken);

    return attendanceSateRepository.save(attendanceSate);
  }

  private Club mappring_Club_ClubControl(Club entityClub, ClubControl clubControl) {
    entityClub.setClubControl(clubControl);

    return clubRepository.save(entityClub);
  }

  private ClubControl mapping_ClubControl___VacattionTokenControl_AttendanceWeek() {
    ClubControl clubControl = new ClubControl();
    VacationTokenControl vacationTokenControl = new VacationTokenControl();
    AttendanceWeek attendanceWeek = new AttendanceWeek();

    clubControl.setVacationTokenControl(vacationTokenControl);
    clubControl.setAttendanceWeek(attendanceWeek);

    return clubControlRepository.save(clubControl);
  }

  private Member applyMember(Integer number) {
    Member member = TestMakeEntity.createSampleMember(number);
    Member entityMember = memberRepository.save(member);
    return entityMember;
  }

  private Club applyClub___AttendanceNumber_ClubIntroduceImage(Integer number) {
    // Club
    Club club = TestMakeEntity.createSampleClub(number);

    // AttendanceNumber
    AttendanceNumber attendanceNumber = new AttendanceNumber();

    // Club - AttendanceNumber
    club.addAttendanceNumber(attendanceNumber);

    // ClubIntroduceImage
    ClubIntroduceImage clubIntroduceImage_0 = TestMakeEntity.createSampleClubIntroduceImage(0);
    ClubIntroduceImage clubIntroduceImage_1 = TestMakeEntity.createSampleClubIntroduceImage(1);
    ClubIntroduceImage clubIntroduceImage_2 = TestMakeEntity.createSampleClubIntroduceImage(2);

    // Club - ClubIntroduceImage의 관계 설정
    club.addClubIntroduceImage(clubIntroduceImage_0);
    club.addClubIntroduceImage(clubIntroduceImage_1);
    club.addClubIntroduceImage(clubIntroduceImage_2);

    return clubRepository.save(club);

  }

  private ClubArticle applyClubArticleCommentToClubArticle(
      CLUB_ARTICLE_CLASSIFICATION classification, Integer number, ClubMember clubMember) {
    Member member = memberRepository.findById(clubMember.getClubMemberId()).get();
    ClubArticle clubArticle;
    // ClubArticle
    if (classification == CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL) {
      clubArticle = TestMakeEntity.createSampleClubArticle(classification, number, clubMember,
          ANONYMITY.ANONYMOUS);
    } else {
      clubArticle = TestMakeEntity.createSampleClubArticle(classification, number, clubMember,
          ANONYMITY.REAL_NAME);
    }

    // ClubArticleComment
    ClubArticleComment clubArticleComment_0 = TestMakeEntity.createSampleClubArticleComment(0,
        member);
    ClubArticleComment clubArticleComment_1 = TestMakeEntity.createSampleClubArticleComment(1,
        member);
    ClubArticleComment clubArticleComment_2 = TestMakeEntity.createSampleClubArticleComment(2,
        member);

    // ClubArticle - ClubArticleComment
    clubArticle.addClubArticleComment(clubArticleComment_0);
    clubArticle.addClubArticleComment(clubArticleComment_1);
    clubArticle.addClubArticleComment(clubArticleComment_2);

    return clubArticleRepository.save(clubArticle);
  }
}