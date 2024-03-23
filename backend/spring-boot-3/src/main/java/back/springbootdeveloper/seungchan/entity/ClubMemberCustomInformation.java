package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.CUSTOM_TYPE;
import back.springbootdeveloper.seungchan.constant.entity.FAVORITE_CHECK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_member_custom_information")
public class ClubMemberCustomInformation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_member_custom_information_id")
  private Long clubMemberCustomInformationId;

  @Column(name = "custom_content", length = 1000)
  private String customContent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "club_member_information_id")
  private ClubMemberInformation clubMemberInformation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "custom_club_apply_information_id")
  private CustomClubApplyInformation customClubApplyInformation;

  @Builder
  public ClubMemberCustomInformation(final String customContent) {
    this.customContent = customContent;
  }

  /**
   * 사용자의 클럽 커스텀 지원서 컨텐츠를 업데이트합니다.
   *
   * @param customContent 새로운 내용 컨텐츠
   */
  public void updateCustomContent(final String customContent) {
    this.customContent = customContent;
  }

  /**
   * 클럽 멤버 정보를 설정합니다.
   *
   * @param clubMemberInformation 설정할 클럽 멤버 정보
   */
  public void setClubMemberInformation(
      final ClubMemberInformation clubMemberInformation) {
    this.clubMemberInformation = clubMemberInformation;

    // 무한루프에 빠지지 않도록 체크
    if (!clubMemberInformation.getClubMemberCustomInformations().contains(this)) {
      clubMemberInformation.getClubMemberCustomInformations().add(this);
    }
  }

  /**
   * 커스텀 클럽 신청 정보를 설정합니다.
   *
   * @param customClubApplyInformation 설정할 커스텀 클럽 신청 정보
   */
  public void setCustomClubApplyInformation(
      final CustomClubApplyInformation customClubApplyInformation) {
    this.customClubApplyInformation = customClubApplyInformation;

    // 무한루프에 빠지지 않도록 체크
    if (!customClubApplyInformation.getClubMemberCustomInformations().contains(this)) {
      customClubApplyInformation.getClubMemberCustomInformations().add(this);
    }
  }
}
