package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClubFindInformation {

  private Long clubId; // 동아리 DB의 PK
  private String clubProfileImage; // 동아리 프로필 이미지
  private String clubName; // 동아리 이름
  private String clubRepresentativeName; // 동아리 대표
  private String numberMember; // 동아리 원의 수
  private String favoriteCheck; // 즐겨 찾기
}
