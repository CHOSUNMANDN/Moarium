package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.ApplyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data //반복작업 줄여줌 getter,setter, tostring과 같이
public class ApplyForm {


    private  String selfIntroduction; //자기소개
    private  String clubLeaderQuery;

    public ApplyForm(String selfIntroduction, String clubLeaderQuery){
        this.selfIntroduction = selfIntroduction;
        this.clubLeaderQuery = clubLeaderQuery;
    }

    public ApplyEntity toEntity() {//DTO -> entity로 변경
        return  ApplyEntity.builder()
                .selfIntroduction(selfIntroduction)
                .clubLeaderQuery(clubLeaderQuery)
                .build();

    }
}
