package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.testutills.TestMakeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    private final AttendanceSateRepository attendanceSateRepository;
    private final VacationTokenRepository vacationTokenRepository;

    @Autowired
    entityMappingTest(MemberRepository memberRepository, ClubMemberRepository clubMemberRepository, ClubRepository clubRepository, ClubIntroduceImageRepository clubIntroduceImageRepository, ClubGradeRepository clubGradeRepository, ClubArticleRepository clubArticleRepository, AttendanceNumberRepository attendanceNumberRepository, ClubControlRepository clubControlRepository, AttendanceWeekDateRepository attendanceWeekDateRepository, AttendanceSateRepository attendanceSateRepository, VacationTokenRepository vacationTokenRepository) {
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
    }

    @BeforeEach
    void setUp() {
        this.clubMemberRepository.deleteAll();
        this.memberRepository.deleteAll();
        this.attendanceNumberRepository.deleteAll();
        this.clubRepository.deleteAll();
        this.clubArticleRepository.deleteAll();
        this.clubControlRepository.deleteAll();
        this.attendanceWeekDateRepository.deleteAll();
        this.attendanceSateRepository.deleteAll();
        this.clubControlRepository.deleteAll();
    }

    @Test
    void Member_ClubMember_Club_ClubIntroduceImage_매핑_저장_학습_테스트_1() throws Exception {
        // ============================================ Club 등록 시작 ============================
        Club entityClub_0 = applyClub(0);
        // ============================================ Club 등록 완료 ============================

        // ============================================ 출석 AttendanceNumber 등록 시작 ============================
        // 1:1
        applyAttendanceNumber_mapping_Club_AttendanceNumber(entityClub_0, new AttendanceNumber());
        // ============================================ 출석 AttendanceNumber 등록 완료 ============================

        // ==================================  AttendanceSate  매핑 시작 ============================
        // ==================================        |                  ============================
        // =====================  AttendanceWeekDate - VacationToken -VacationToken ============================
        AttendanceSate attendanceSate = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        // ==================================  AttendanceSate 매핑 완료 ============================


        // ================================ ClubControl - VacationTokenControl - AttendanceWeek 등록 ============================
        // 1:1
        ClubControl clubControl_0 = mapping_ClubControl_VacattionTokenControl_AttendanceWeek();
        // ============================================ ClubControl - VacationTokenControl 등록 완료 ============================

        // ============================================= Club - ClubControl 등록 ============================
        // 1:1
        entityClub_0 = mappring_Club_ClubControl(entityClub_0, clubControl_0);
        // ============================================= Club - ClubControl 등록 완료 ============================

        // ============================================ Member 등록 시작 ============================
        Member entityMember_0 = applyMember(0);
        // ============================================ Member 등록 완료 ============================

        // ============================================ ClubGrade 찾기 시작 ============================
        ClubGrade clubGradeLeader = clubGradeRepository.findByClubGrade(CLUB_GRADE.LEADER);
        // ============================================ ClubGrade 찾기 완료 ============================


        // ========================================= Member와 Club의 관계 설정 시작 ========================
        ClubMember entityClubMember = Mapping_Member_Club(entityClub_0, entityMember_0, clubGradeLeader);
        // ========================================= Member와 Club의 관계 설정 완료 ========================

        // ========================================= ClubArticle 등록 시작 ========================
        ClubArticle entityClubArticle_suggestion = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, 0);
        // ========================================= ClubArticle 등록 완료 ========================


        // ========================================= ClubArticleComment 등록 시작 ========================
        entityClubArticle_suggestion = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion, 0);
        entityClubArticle_suggestion = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion, 1);
        entityClubArticle_suggestion = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion, 2);
        // ========================================= ClubArticleComment 등록 완료 ========================

        // ========================================= Member와 Club와 ClubArticle의 관계 설정 시작 ========================
        entityClubMember = Mapping_Member_Club(entityClubMember, entityClubArticle_suggestion);
        // ========================================= Member와 Club와 ClubArticle의 관계 설정 완료 ========================

        // ==================================  ClubMember - AttendanceState 매핑 시작 ============================
        entityClubMember = mapping_AttendanceState_ClubMember(entityClubMember, attendanceSate);
        // ==================================  ClubMember - AttendanceState 매핑 완료 ============================

        // ==================================  ClubMember - ClubMemberInformation 매핑 시작 ============================
        entityClubMember = mapping_ClubMemberInformation_ClubMember(entityClubMember);
        // ==================================  ClubMember - AttendanceState 매핑 완료 ============================
    }

    private ClubMember mapping_ClubMemberInformation_ClubMember(ClubMember entityClubMember) {
        ClubMemberInformation clubMemberInformation = TestMakeEntity.createSampleClubMemberInformation(0);
        entityClubMember.setClubMemberInformation(clubMemberInformation);
        return clubMemberRepository.save(entityClubMember);
    }

    private ClubMember mapping_AttendanceState_ClubMember(ClubMember entityClubMember, AttendanceSate attendanceSate) {
        entityClubMember.setAttendanceSate(attendanceSate);

        return clubMemberRepository.save(entityClubMember);
    }

    private AttendanceSate mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken() {
        AttendanceSate attendanceSate = new AttendanceSate();
        AttendanceCheckTime attendanceCheckTime = new AttendanceCheckTime();
        AttendanceWeekDate attendanceWeekDate = new AttendanceWeekDate();
        VacationToken vacationToken = new VacationToken();

        attendanceSate.setAttendanceCheckTime(attendanceCheckTime);
        attendanceSate.setAttendanceWeekDate(attendanceWeekDate);
        attendanceSate.setVacationToken(vacationToken);

        return attendanceSateRepository.save(attendanceSate);
    }

    private Club mappring_Club_ClubControl(Club entityClub_0, ClubControl clubControl_0) {
        entityClub_0.setClubControl(clubControl_0);
        entityClub_0 = clubRepository.save(entityClub_0);
        return entityClub_0;
    }

    private ClubControl mapping_ClubControl_VacattionTokenControl_AttendanceWeek() {
        ClubControl clubControl_0 = new ClubControl();
        VacationTokenControl vacationTokenControl_0 = new VacationTokenControl();
        AttendanceWeek attendanceWeek_0 = new AttendanceWeek();

        clubControl_0.setVacationTokenControl(vacationTokenControl_0);
        clubControl_0.setAttendanceWeek(attendanceWeek_0);

        return clubControlRepository.save(clubControl_0);
    }

    private void applyAttendanceNumber_mapping_Club_AttendanceNumber(Club entityClub_0, AttendanceNumber attendanceNumber) {
        AttendanceNumber attendanceNumber_0 = attendanceNumber;
        attendanceNumber_0.setClub(entityClub_0);
        attendanceNumberRepository.save(attendanceNumber_0);
    }

    private ClubMember Mapping_Member_Club(ClubMember entityClubMember, ClubArticle entityClubArticleSuggestion) {
        entityClubMember.setClubArticle(entityClubArticleSuggestion);
        clubArticleRepository.save(entityClubArticleSuggestion);
        return clubMemberRepository.save(entityClubMember);
    }

    private ClubMember Mapping_Member_Club(Club entityClub_0, Member entityMember_0, ClubGrade clubGrade) {
        // ClubMember
        ClubMember clubMember = new ClubMember();

        // Member와 ClubMember의 관계 설정
        clubMember.setMember(entityMember_0);
        clubMember.setClub(entityClub_0);
        clubMember.setClubGrade(clubGrade);

        // Member 엔티티를 다시 저장하여 영속 상태를 유지
        return clubMemberRepository.save(clubMember);
    }

    private Member applyMember(Integer number) {
        Member member = TestMakeEntity.createSampleMember(number);
        Member entityMember = memberRepository.save(member);
        return entityMember;
    }

    private Club applyClub(Integer number) {
        // Club
        Club club = TestMakeEntity.createSampleClub(number);

        // ClubIntroduceImage
        ClubIntroduceImage clubIntroduceImage_0 = TestMakeEntity.createSampleClubIntroduceImage(0);
        ClubIntroduceImage clubIntroduceImage_1 = TestMakeEntity.createSampleClubIntroduceImage(1);
        ClubIntroduceImage clubIntroduceImage_2 = TestMakeEntity.createSampleClubIntroduceImage(2);

        // Club - ClubIntroduceImage의 관계 설정
        club.addClubIntroduceImage(clubIntroduceImage_0);
        club.addClubIntroduceImage(clubIntroduceImage_1);
        club.addClubIntroduceImage(clubIntroduceImage_2);

        Club entityClub = clubRepository.save(club);
        return entityClub;
    }

    @Test
    void sdf() throws Exception {
    }

    private ClubArticle applyClubArticle(CLUB_ARTICLE_CLASSIFICATION clubArticleClassification, Integer number) {
        // ClubArticle
        ClubArticle clubArticle = TestMakeEntity.createSampleClubArticle(clubArticleClassification, number);

        return clubArticleRepository.save(clubArticle);
    }

    private ClubArticle applyClubArticleCommentToClubArticle(ClubArticle entityClubArticle, Integer number) {
        // ClubArticleComment
        ClubArticleComment clubArticleComment = TestMakeEntity.createSampleClubArticleComment(number);

        // ClubArticle - ClubArticleComment
        entityClubArticle.addClubArticleComment(clubArticleComment);
        return clubArticleRepository.save(entityClubArticle);
    }

}