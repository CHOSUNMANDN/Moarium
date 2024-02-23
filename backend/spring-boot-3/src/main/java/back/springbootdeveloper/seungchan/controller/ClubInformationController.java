package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.response.ClubInformationResponse;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.service.ClubInformationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "동아리 소개 페이지 조회 API", description = "동아리 소개 페이지로 이동한다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class ClubInformationController {
    private final ClubInformationService clubInformationService;

    @GetMapping("/clubs/informations/{club_id}")
    public ResponseEntity<ClubInformationResponse> clubInformation(@PathVariable(name = "club_id") Long club_id) {
        Club club = clubInformationService.findById(club_id);
        return ResponseEntity.ok()
                .body(new ClubInformationResponse(club));
    }

}
