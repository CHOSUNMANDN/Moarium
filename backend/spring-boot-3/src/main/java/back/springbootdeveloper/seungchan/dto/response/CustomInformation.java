package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.ClubMemberCustomInformation;
import back.springbootdeveloper.seungchan.entity.CustomClubApplyInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomInformation {

  private Long customInformationId; // CustomClubApplyInformation의 Id
  private String customContent;
  private String customType;

  public CustomInformation(final CustomClubApplyInformation customClubApplyInformation) {
    this.customInformationId = customClubApplyInformation.getCustomClubApplyInformationId();
    this.customContent = customClubApplyInformation.getCustomContent();
    this.customType = customClubApplyInformation.getCustomType().getType();
  }

  public CustomInformation(final ClubMemberCustomInformation clubMemberCustomInformation,
      final CustomClubApplyInformation customClubApplyInformation) {
    this.customInformationId = customClubApplyInformation.getCustomClubApplyInformationId();
    this.customContent = clubMemberCustomInformation.getCustomContent();
    this.customType = customClubApplyInformation.getCustomType().getType();
  }
}
