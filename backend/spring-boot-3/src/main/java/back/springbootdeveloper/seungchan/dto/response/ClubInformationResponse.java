package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubIntroduceImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClubInformationResponse {
    private String clubName;
    private String clubIntroduction;
    private List<ClubIntroduceImage> clubInformationImages;

    public ClubInformationResponse(Club club) {
        this.clubName = club.getClubName();
        this.clubIntroduction = club.getClubIntroduce();
        this.clubInformationImages = club.getClubIntroduceImages();
    }

}
