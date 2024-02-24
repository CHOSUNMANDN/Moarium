package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClubMemberDetailResDto {
    private String clubName;
    private Long myClubMemberId;
    private String myClubGrade;
    private List<ClubMemberResponse> clubMembers;
}
