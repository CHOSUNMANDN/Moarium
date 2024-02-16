package back.springbootdeveloper.seungchan.entity;

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

    @OneToOne(mappedBy = "clubMemberInformation")
    private ClubMember clubMember;

    @Builder
    public ClubMemberInformation(String introduce) {
        this.introduce = introduce;
    }

    public void updateIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setClubMember(final ClubMember clubMember) {
        this.clubMember = clubMember;

        if (clubMember.getClubMemberInformation() != this) { // null 체크 추가
            clubMember.setClubMemberInformation(this);
        }
    }
}
