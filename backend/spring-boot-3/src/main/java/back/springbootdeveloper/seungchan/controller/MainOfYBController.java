package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import back.springbootdeveloper.seungchan.dto.response.UserOfDetail2MainResponse;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import back.springbootdeveloper.seungchan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class MainOfYBController {
    private final UserService userService;
    private final UserUtilRepository userUtilRepository;

    @GetMapping("/main/ybs/detail")
    public ResponseEntity<UserOfDetail2MainResponse> fetchUserOfDetail2Main() {
//         TODO Token을 받아서 현재 유저의 정보를 가져와야된다.
        // 1. i will take token from front
        // 2. i have to get a user Information from token of user
        // 3. so... resulte that we get a user_ID from token
        Long userTempID = Long.valueOf(2); // user_id is ID from user DB
        User user = userService.findUserById(userTempID);

        Long userId = user.getId();
        UserUtill userUtill = userUtilRepository.findByUserId(userId);

        UserOfDetail2MainResponse response = new UserOfDetail2MainResponse(userUtill, user);

        return ResponseEntity.ok().body(response);
    }
}