package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.response.ApplyForm;
import back.springbootdeveloper.seungchan.entity.ApplyEntity;
import back.springbootdeveloper.seungchan.service.ApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "동아리 지원서 작성 API", description = "동아리(팀), 신입의 가입 신청한다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApplyPageController {
    private final ApplyService applyService; // 서비스 주입

    @Operation(summary = "팀 지원서 작성", description = "회원이 원하는 팀에 지원서를 작성한다.")
    @PostMapping("/clubs/{club_id}/apply")
    public ResponseEntity<ApplyEntity> clubApply(@PathVariable Long club_id, @RequestBody @Valid ApplyForm applyForm) {
        ApplyEntity clubApplied = applyService.clubApply(applyForm); // 인스턴스를 통한 메소드 호출

        if (clubApplied != null) {
            return ResponseEntity.status(HttpStatus.OK).body(clubApplied);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
