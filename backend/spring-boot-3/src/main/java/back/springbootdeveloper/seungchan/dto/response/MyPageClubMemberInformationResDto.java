package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MyPageClubMemberInformationResDto {
    private String ClubName;
    private String memberName;
    private String memberStudentId;
    private String memberStatusMessage;
    private String memberProfile;
}
