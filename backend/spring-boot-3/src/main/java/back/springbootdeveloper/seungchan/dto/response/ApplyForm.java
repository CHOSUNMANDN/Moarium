package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data //반복작업 줄여줌 getter,setter, tostring과 같이
public class ApplyDto {

    private  Long club_id;
    private  String selfIntroduction; //자기소개
    private  String customQuery;

    public ApplyDto(String selfIntroduction, String customQuery){
        this.selfIntroduction = selfIntroduction;
        this.customQuery = customQuery;
    }
}
