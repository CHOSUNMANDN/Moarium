package back.springbootdeveloper.seungchan.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_introduce_image")
public class ClubIntroduceImage extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_introduce_image_id")
  private Long clubIntroduceImageId;

  @Column(name = "club_introduce_image", length = 1000, nullable = false)
  private String clubIntroduceImageUrl;

  @Builder
  public ClubIntroduceImage(String clubIntroduceImageUrl) {
    this.clubIntroduceImageUrl = clubIntroduceImageUrl;
  }

  /**
   * 클럽 소개 이미지 URL을 업데이트하는 메서드입니다.
   *
   * @param clubIntroduceImageUrl 새로운 클럽 소개 이미지 URL
   */
  public void updateClubIntroduceImageUrl(String clubIntroduceImageUrl) {
    this.clubIntroduceImageUrl = clubIntroduceImageUrl;
  }
}
