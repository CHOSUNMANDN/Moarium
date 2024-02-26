package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.FAVORITE_CHECK;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_member_information")
public class ClubMemberInformation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_member_information_id")
    private Long clubMemberInformationId;

    @Column(name = "introduce", length = 1000, nullable = false)
    private String introduce;

    @Enumerated(EnumType.STRING)
    @Column(name = "favorite_check", length = 15, nullable = false)
    private FAVORITE_CHECK favoriteCheck = FAVORITE_CHECK.UNCHECK;

    @Builder
    public ClubMemberInformation(String introduce) {
        this.introduce = introduce;
    }

    public void updateIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void updateFavoriteCheck(FAVORITE_CHECK favoriteCheck) {
        this.favoriteCheck = favoriteCheck;
    }
}
