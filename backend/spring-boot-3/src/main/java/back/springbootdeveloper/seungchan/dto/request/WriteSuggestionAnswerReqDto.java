package back.springbootdeveloper.seungchan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WriteSuggestionAnswerReqDto {
    private String clubSuggestionArticleAnswer;
}
