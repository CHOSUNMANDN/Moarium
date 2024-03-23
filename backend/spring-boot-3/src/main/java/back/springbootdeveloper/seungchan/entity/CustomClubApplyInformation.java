package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.CUSTOM_TYPE;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "custom_club_apply_information")
public class CustomClubApplyInformation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "custom_club_apply_information_id")
  private Long customClubApplyInformationId;

  @Column(name = "custom_content", length = 255, nullable = false)
  private String customContent;

  @Enumerated(EnumType.STRING)
  @Column(name = "custom_type", length = 30, nullable = false)
  private CUSTOM_TYPE customType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "club_control_id")
  private ClubControl clubControl;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customClubApplyInformation")
  private List<ClubMemberCustomInformation> clubMemberCustomInformations = new ArrayList<>();

  @Builder
  public CustomClubApplyInformation(final String customContent, final CUSTOM_TYPE customType) {
    this.customContent = customContent.trim();
    this.customType = customType;
  }

  /**
   * 사용자 정의 컨텐츠를 업데이트합니다.
   *
   * @param customContent 새로운 사용자 정의 컨텐츠
   */
  public void updateCustomContent(final String customContent) {
    this.customContent = customContent;
  }

  /**
   * 사용자 정의 타입을 업데이트합니다.
   *
   * @param customType 새로운 사용자 정의 타입
   */
  public void updateCustomType(final CUSTOM_TYPE customType) {
    this.customType = customType;
  }


  /**
   * 클럽 컨트롤을 설정합니다.
   *
   * @param clubControl 설정할 클럽 컨트롤
   */
  public void setClubControl(final ClubControl clubControl) {
    this.clubControl = clubControl;

    // 무한루프에 빠지지 않도록 체크
    if (!clubControl.getCustomClubApplyInformations().contains(this)) {
      clubControl.getCustomClubApplyInformations().add(this);
    }
  }

  /**
   * 클럽 멤버에 커스텀 클럽 신청의 관한 답변 정보를 추가합니다.
   *
   * @param clubMemberCustomInformation 추가할 클럽 멤버의 사용자 정의 정보
   */
  public void addClubMemberCustomInformations(
      final ClubMemberCustomInformation clubMemberCustomInformation) {
    this.clubMemberCustomInformations.add(clubMemberCustomInformation);

    if (clubMemberCustomInformation.getCustomClubApplyInformation() != this) { // 무한루프에 빠지지 않도록 체크
      clubMemberCustomInformation.setCustomClubApplyInformation(this);
    }
  }
}
