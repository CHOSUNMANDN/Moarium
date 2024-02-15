package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "user_utill")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUtill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, updatable = false, unique = true)
    private Long userId;

    @Column(name = "name", length = 10, nullable = false, updatable = false)
    private String name;

    @Column(name = "cnt_vacation", nullable = false, updatable = false)
    private int cntVacation;

    @Column(name = "nuri_king", nullable = false, updatable = false)
    private boolean isNuriKing;

    @Column(name = "general_affairs", nullable = false, updatable = false)
    private boolean isGeneralAffairs;

    @Builder
    public UserUtill(Long userId, String name, int cntVacation, boolean isNuriKing, boolean isGeneralAffairs) {
        this.userId = userId;
        this.name = name;
        this.cntVacation = cntVacation;
        this.isNuriKing = isNuriKing;
        this.isGeneralAffairs = isGeneralAffairs;
    }

    public void updateVacationNum(int resultVacationNum) {
        this.cntVacation = resultVacationNum;
    }
}
