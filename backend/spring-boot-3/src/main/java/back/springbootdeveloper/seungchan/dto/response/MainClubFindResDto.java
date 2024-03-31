package back.springbootdeveloper.seungchan.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MainClubFindResDto {

  List<ClubFindInformation> clubFindInformations; // 메인 페이지 조회용 동아리 관련 정보 리스트
}
