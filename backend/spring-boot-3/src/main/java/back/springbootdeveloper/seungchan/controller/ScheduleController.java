package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.ImageResDto;
import back.springbootdeveloper.seungchan.service.FastApiHttpReqService;
import back.springbootdeveloper.seungchan.service.ImageService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Tag(name = "시간표 자동 등록 페이지 API", description = "시간표 자동 등록을 하여 OCR 적용 한다.")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs")
public class ScheduleController {
    private final ImageService imageService;
    private final FastApiHttpReqService fastApiHttpReqService;

    @Autowired
    public ScheduleController(ImageService imageService, FastApiHttpReqService fastApiHttpReqService) {
        this.imageService = imageService;
        this.fastApiHttpReqService = fastApiHttpReqService;
    }

    // @RequestPart 는 name 속성을 통해 key 값을 지정해 바인딩 해줄 수 있는데,
    // 지정하지 않으면 디폴트로 변수명을 key 값으로 갖기 때문에 특별히 이유가 있지 않다면 따로 지정해주지 않아도 된다.
//    @PostMapping(value = "/{club_id}/schedules/{club_member_id}/manual")
//    public ResponseEntity<BaseResponseBody> RegisterTimeTableImage(@PathVariable(value = "club_id") Long clubId
//            , @PathVariable(value = "club_member_id") Long clubMemberId
//            , @RequestParam(value = "scheduleImage") MultipartFile scheduleImage) {
    @PostMapping(value = "/schedules/manual")
    public BaseResultDTO<ImageResDto> RegisterTimeTableImage(
            HttpServletRequest request,
            @RequestParam(value = "scheduleImage") MultipartFile scheduleImage) {

        ImageResDto imageResDto = imageService.uploadImage(scheduleImage);

        Mono<String> resultMono = fastApiHttpReqService.callInternalApi();
        resultMono.subscribe(result -> System.out.println("Result: " + result));

        return BaseResultDTO.ofSuccessWithMessage(ResponseMessage.REGISTRATION_TIMETABLE_COMPLETE.get(), imageResDto);
    }
}
