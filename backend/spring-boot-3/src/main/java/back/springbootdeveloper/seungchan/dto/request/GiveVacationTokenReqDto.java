package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GiveVacationTokenReqDto {
    @NotNull(message = "{validation.vacation.token.notblank}")
    private Integer vacationToken;
}
