package back.springbootdeveloper.seungchan.testutills;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.entity.*;

public class TestMakeEntity {
    public static Member createSampleMember(Integer number) {
        String firstName = "성_" + number;
        String lastName = "이름_" + number;
        String nickName = "TEST_NICK_NAME_" + number;
        String email = "test" + number + "@example.com";
        String major = "테스트_전공_" + number;
        String studentId = "20242024";

        return Member.builder()
                .firstName(firstName)
                .lastName(lastName)
                .nickName(nickName)
                .email(email)
                .major(major)
                .studentId(studentId)
                .build();
    }

    public static Club createSampleClub(Integer number) {
        String clubName = "테스트_팀_이름_" + number;
        String clubIntroduce = "테스트_팀_자기소개_" + number;
        String clubProfileImage = "테스트_프로필_사진_URL_" + number;

        return Club.builder()
                .clubName(clubName)
                .clubIntroduce(clubIntroduce)
                .clubProfileImage(clubProfileImage)
                .build();
    }

    public static ClubIntroduceImage createSampleClubIntroduceImage(Integer number) {
        String clubIntroduceImageUrl = "테스트_소개_사진_URL_" + number;

        return ClubIntroduceImage.builder()
                .clubIntroduceImageUrl(clubIntroduceImageUrl)
                .build();
    }

    public static ClubArticleComment createSampleClubArticleComment(Integer nuber) {
        return ClubArticleComment.builder()
                .content("테스트_클럽_게시물_댓글_" + nuber)
                .build();
    }

    public static ClubArticle createSampleClubArticle(CLUB_ARTICLE_CLASSIFICATION classification, Integer nuber, ClubMember clubMember) {
        return ClubArticle.builder()
                .title("테스트_클럽_게시물_제목_" + nuber)
                .content("테스트_클럽_게시물_내용_" + nuber)
                .classification(classification)
                .clubMemberId(clubMember.getClubMemberId())
                .build();
    }

    public static ClubMemberInformation createSampleClubMemberInformation(Integer number) {
        return ClubMemberInformation.builder()
                .introduce("테스트_클럽_지원_자기소개_" + number)
                .build();
    }

    public static ClubMember createSampleClubMember(Long memberId, Long clubId, Long clubGradeId, Long attendanceStateId, Long clubMemberInformationId) {
        return ClubMember.builder()
                .memberId(memberId)
                .clubId(clubId)
                .clubGradeId(clubGradeId)
                .attendanceSateId(attendanceStateId)
                .clubMemberInformationId(clubMemberInformationId)
                .build();
    }
}
