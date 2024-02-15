//package back.springbootdeveloper.seungchan.controller;
//
//import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
//import back.springbootdeveloper.seungchan.constant.filter.CustomHttpStatus;
//import back.springbootdeveloper.seungchan.constant.filter.exception.ExceptionMessage;
//import back.springbootdeveloper.seungchan.dto.request.FindEmailReqDto;
//import back.springbootdeveloper.seungchan.dto.request.FindPasswordReqDto;
//import back.springbootdeveloper.seungchan.testutills.TestSetUp;
//import back.springbootdeveloper.seungchan.testutills.TestUtills;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@SpringBootTest()
//@AutoConfigureMockMvc
//class LookupControllerTest {
//    @Autowired
//    protected MockMvc mockMvc;
//    @Autowired
//    protected ObjectMapper objectMapper;
//    @Autowired
//    private DatabaseService databaseService;
//    @Autowired
//    private TestSetUp testSetUp;
//    @Autowired
//    private UserService userService;
//    private String token;
//    private Long kingUserId;
//
//    @BeforeEach
//    void setUp() {
//        databaseService.deleteAllDatabase();
//        token = testSetUp.getToken(mockMvc);
//        kingUserId = testSetUp.getKingUserId();
//    }
//
//
//    @DisplayName("임시_비밀번호_반환_테스트 : 반환 메세지, PW변경 확인")
//    @Test
//    void 임시_비밀번호_반환_테스트() throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/find/password";
//        String beforePassword = kingUser.getPassword();
//        FindPasswordReqDto reqestDto = FindPasswordReqDto.builder()
//                .email(kingUser.getEmail())
//                .name(kingUser.getName())
//                .authenticationEmail(kingUser.getEmail())
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(reqestDto);
//
//        ResultActions result = mockMvc.perform(post(url)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestBody)
//        );
//
//        String afterPassword = userService.findUserById(kingUserId).getPassword();
//
//        // than
//        result
//                .andExpect(jsonPath("$.message").value(ResponseMessage.TEMP_PASSWORD_MESSAGE.get()))
//                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
//                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
//
//        assertThat(beforePassword).doesNotContain(afterPassword);
//    }
//
//    @DisplayName("임시_이메일_반환_테스트 : 유저가_존제하지_않는다")
//    @ParameterizedTest
//    @CsvSource({
//            "NOTTHING@email,박승찬",
//            "seungchan141414@gmail.com,NAME",
//            "NOTTHING@email,NAME"
//    })
//    void 임시_이메일_반환_예외_유저가_존제하지_않는다_테스트(String email, String name) throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/find/password";
//        FindPasswordReqDto requestDto = FindPasswordReqDto.builder()
//                .email(email)
//                .name(name)
//                .authenticationEmail(kingUser.getEmail())
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.USER_NOT_EXIST.value());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "asdf",
//            "12  aea3",
//            "asdf sadf asdfdsa",
//            "sadf@sdaf@sdf",
//            "23!saf",
//            "invalid_email",
//            "user@.com",
//            "@example.com",
//            "user@.com.",
//            "user@example..com",
//            "user@exa mple.com",
//            "user@example.com.",
//            "user@.example.com",
//            "user@-example.com",
//            "user@example-.com",
//            "user@example.com-",
//            "user@exam@ple.com"
//    })
//    void 임시_이메일_반환_예외_형식에_맞지_않는_이메일_테스트(String input) throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/find/password";
//        FindPasswordReqDto requestDto = FindPasswordReqDto.builder()
//                .email(input)
//                .name(kingUser.getName())
//                .authenticationEmail(kingUser.getEmail())
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "asdf",
//            "12  aea3",
//            "asdf sadf asdfdsa",
//            "sadf@sdaf@sdf",
//            "23!saf",
//            "invalid_email",
//            "user@.com",
//            "@example.com",
//            "user@.com.",
//            "user@example..com",
//            "user@exa mple.com",
//            "user@example.com.",
//            "user@.example.com",
//            "user@-example.com",
//            "user@example-.com",
//            "user@example.com-",
//            "user@exam@ple.com"
//    })
//    void 임시_이메일_반환_예외_형식에_맞지_않는_인증_이메일_테스트(String input) throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/find/password";
//        FindPasswordReqDto requestDto = FindPasswordReqDto.builder()
//                .email(kingUser.getEmail())
//                .name(kingUser.getName())
//                .authenticationEmail(input)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "name",
//            "email",
//            "authenticationEmail",
//            "name_2",
//            "email_2",
//            "authenticationEmail_2",
//            "name_3",
//            "email_3",
//            "authenticationEmail_3",
//    })
//    void 임시_이메일_반환_예외_NOT_BLANK_테스트(String check) throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/find/password";
//        FindPasswordReqDto requestDto = FindPasswordReqDto.builder()
//                .email(kingUser.getEmail())
//                .name(kingUser.getName())
//                .authenticationEmail(kingUser.getEmail())
//                .build();
//
//        switch (check) {
//            case "name":
//                requestDto.setName("");
//                break;
//            case "email":
//                requestDto.setEmail("");
//                break;
//            case "authenticationEmail":
//                requestDto.setAuthenticationEmail("");
//                break;
//            case "name_2":
//                requestDto.setName(" ");
//                break;
//            case "email_2":
//                requestDto.setEmail(" ");
//                break;
//            case "authenticationEmail_2":
//                requestDto.setAuthenticationEmail(" ");
//                break;
//            case "name_3":
//                requestDto.setName(null);
//                break;
//            case "email_3":
//                requestDto.setEmail(null);
//                break;
//            case "authenticationEmail_3":
//                requestDto.setAuthenticationEmail(null);
//                break;
//        }
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
//    }
//
//    @Test
//    void 비밀번호_변경_테스트() throws Exception {
//        // given
//        String password = testSetUp.getKingUserPassword();
//        String updatePassword = "updatePassword1!";
//        String checkPassword = updatePassword;
//        final String url = "/admin/update/password";
//        UpdatePasswordReqDto reqestDto = UpdatePasswordReqDto.builder()
//                .password(password)
//                .updatePassword(updatePassword)
//                .checkUpdatePassword(checkPassword)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(reqestDto);
//
//        ResultActions result = mockMvc.perform(post(url)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestBody)
//                .header("authorization", "Bearer " + token) // token header에 담기
//        );
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        Boolean resultUpdatePassword = TestUtills.checkPassword(updatePassword, kingUser.getPassword());
//
//        // than
//        result
//                .andExpect(jsonPath("$.message").value(ResponseMessage.UPDATE_PASSWORD_MESSAGE.get()))
//                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
//                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
//
//        assertThat(resultUpdatePassword).isTrue();
//    }
//
//    @Test
//    void 비밀번호_변경_예외_Update_PW_와_확인_PW_가_다른_테스트() throws Exception {
//        // given
//        String password = testSetUp.getKingUserPassword();
//        String updatePassword = "updatePassword1!";
//        String checkPassword = "differentPassword1!";
//        final String url = "/admin/update/password";
//        UpdatePasswordReqDto requestDto = UpdatePasswordReqDto.builder()
//                .password(password)
//                .updatePassword(updatePassword)
//                .checkUpdatePassword(checkPassword)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(message).isEqualTo(ExceptionMessage.PASSWORD_CONFIRMATION.get());
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.PASSWORD_CONFIRMATION.value());
//    }
//
//    @Test
//    void 비밀번호_변경_예외_현재_비밀번호와_입력_비밀번호_다를시_예외처리() throws Exception {
//        // given
//        String password = "FALSEPASSWOAR1!";
//        String updatePassword = "updatePassword1!";
//        String checkPassword = updatePassword;
//        final String url = "/admin/update/password";
//        UpdatePasswordReqDto requestDto = UpdatePasswordReqDto.builder()
//                .password(password)
//                .updatePassword(updatePassword)
//                .checkUpdatePassword(checkPassword)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(message).isEqualTo(ExceptionMessage.PASSWORD_MISS_MATCH.get());
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.PASSWORD_MISS_MATCHES.value());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "P@ss1",       // 8자 미만
//            "P",           // 8자 미만
//            "p",           // 8자 미만
//            "123",         // 8자 미만
//            "Password1",   // 특수 문자 누락
//            "P@ssword",    // 숫자 누락
//            "longpasswordwithoutspecialcharacters1234567890", // 특수 문자 누락
//            "P@ssword@@@@"   // 연속된 특수 문자
//            // 추가 테스트 데이터 추가 가능
//    })
//    void 비밀번호_변경_예외_Update_PW_검증_테스트(String badPassword) throws Exception {
//        // given
//        String updatePassword = badPassword;
//        String checkPassword = badPassword;
//        final String url = "/admin/update/password";
//        UpdatePasswordReqDto requestDto = UpdatePasswordReqDto.builder()
//                .updatePassword(updatePassword)
//                .checkUpdatePassword(checkPassword)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
//
//    }
//
//    @DisplayName("이메일_변경_테스트 : 이메일과 업데이트 이메일을 이용한 업데이트")
//    @Test
//    void 이메일_변경_테스트() throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        String updateEmail = "update@Email.com";
//        final String url = "/admin/update/email";
//        String beforeEmail = kingUser.getEmail();
//
//        UpdateEmailReqDto reqestDto = UpdateEmailReqDto.builder()
//                .email(kingUser.getEmail())
//                .updateEmail(updateEmail)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(reqestDto);
//
//        ResultActions result = mockMvc.perform(post(url)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestBody)
//                .header("authorization", "Bearer " + token) // token header에 담기
//        );
//        String afterEmail = userService.findUserById(kingUserId).getEmail();
//
//        // than
//        result
//                .andExpect(jsonPath("$.message").value(ResponseMessage.UPDATE_EMAIL_MESSAGE.get()))
//                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
//                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
//
//        assertThat(beforeEmail).doesNotContain(afterEmail);
//        assertThat(updateEmail).contains(afterEmail);
//    }
//
//    @Test
//    void 이메일_변경_예외_이메일_변경_이메일_같은지_확인_테스트() throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        String updateEmail = kingUser.getEmail();
//        final String url = "/admin/update/email";
//
//        UpdateEmailReqDto requestDto = UpdateEmailReqDto.builder()
//                .email(kingUser.getEmail())
//                .updateEmail(updateEmail)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(message).isEqualTo(ExceptionMessage.EMAIL_SAME_MATCH.get());
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.EMAIL_SAME_MATCH.value());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "asdf",
//            "12  aea3",
//            "asdf sadf asdfdsa",
//            "sadf@sdaf@sdf",
//            "23!saf",
//            "invalid_email",
//            "user@.com",
//            "@example.com",
//            "user@.com.",
//            "user@example..com",
//            "user@exa mple.com",
//            "user@example.com.",
//            "user@.example.com",
//            "user@-example.com",
//            "user@example-.com",
//            "user@example.com-",
//            "user@exam@ple.com"
//    })
//    void 이메일_변경_예외_이메일_검증(String checkEmail) throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        String updateEmail = kingUser.getEmail();
//        final String url = "/admin/update/email";
//
//        UpdateEmailReqDto requestDto = UpdateEmailReqDto.builder()
//                .email(checkEmail)
//                .updateEmail(updateEmail)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "asdf",
//            "12  aea3",
//            "asdf sadf asdfdsa",
//            "sadf@sdaf@sdf",
//            "23!saf",
//            "invalid_email",
//            "user@.com",
//            "@example.com",
//            "user@.com.",
//            "user@example..com",
//            "user@exa mple.com",
//            "user@example.com.",
//            "user@.example.com",
//            "user@-example.com",
//            "user@example-.com",
//            "user@example.com-",
//            "user@exam@ple.com"
//    })
//    void 이메일_변경_예외_update_이메일_검증(String checkEmail) throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/update/email";
//
//        UpdateEmailReqDto requestDto = UpdateEmailReqDto.builder()
//                .email(kingUser.getEmail())
//                .updateEmail(checkEmail)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
//    }
//
//    @DisplayName("유저의 이메일을 찾아서 인증 이메일로 보낸다.")
//    @Test
//    void 이메일_찾기_테스트() throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/find/email";
//        String dashPhoneNumber = kingUser.getPhoneNum();
//        String phoneNumber = dashPhoneNumber.replace("-", "");
//
//        FindEmailReqDto requestDto = FindEmailReqDto.builder()
//                .name(kingUser.getName())
//                .authenticationEmail(kingUser.getEmail())
//                .phoneNum(phoneNumber)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        ResultActions result = mockMvc.perform(post(url)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestBody)
//                .header("authorization", "Bearer " + token) // token header에 담기
//        );
//
//        // than
//        result
//                .andExpect(jsonPath("$.message").value(ResponseMessage.FIND_EMAIL_OK.get()))
//                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
//                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "박승찬, 010-1234-1234",
//            "이상한, 010-2383-6578",
//            "이상한, 010-1234-1234"
//    })
//    void 이메일_찾기_예외_해당_유저을_못찾는_테스트(String badName, String badPoneNum) throws Exception {
//        System.out.println("badName = " + badName);
//        System.out.println("badPoneNum = " + badPoneNum);
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/find/email";
//
//        FindEmailReqDto requestDto = FindEmailReqDto.builder()
//                .name(badName)
//                .authenticationEmail(kingUser.getEmail())
//                .phoneNum(badPoneNum)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(message).isEqualTo(ExceptionMessage.USER_NOT_EXIST_MESSAGE.get());
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.USER_NOT_EXIST.value());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "asdf",
//            "12  aea3",
//            "asdf sadf asdfdsa",
//            "sadf@sdaf@sdf",
//            "23!saf",
//            "invalid_email",
//            "user@.com",
//            "@example.com",
//            "user@.com.",
//            "user@example..com",
//            "user@exa mple.com",
//            "user@example.com.",
//            "user@.example.com",
//            "user@-example.com",
//            "user@example-.com",
//            "user@example.com-",
//            "user@exam@ple.com"
//    })
//    void 이메일_찾기_예외_해당_이메일_검증_테스트(String input) throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/find/email";
//
//        FindEmailReqDto requestDto = FindEmailReqDto.builder()
//                .name(kingUser.getName())
//                .authenticationEmail(input)
//                .phoneNum(kingUser.getPhoneNum())
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
//    }
//
//    @ParameterizedTest
//    @Disabled
//    @ValueSource(strings = {
//            "010",
//            "02-123-456",
//            "032-9876-5432",
//            "1234-5678",
//            "010-12345-6789",
//            "010-12-3456",
//            "010-1234-56789",
//            "010-1234-567",
//            "010-1!@#234-@#$567",
//            "010-1234-@#$567",
//            "010-12a4-5678",
//            "010-1234-5678-123",
//            "01012345678123",
//            "0101!@#2345678123",
//    })
//    void 이메일_찾기_예외_해당_핸드폰_검증_테스트(String input) throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/find/email";
//
//        FindEmailReqDto requestDto = FindEmailReqDto.builder()
//                .name(kingUser.getName())
//                .authenticationEmail(kingUser.getEmail())
//                .phoneNum(input)
//                .build();
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "name",
//            "phoneNum",
//            "authenticationEmail",
//            "name_1",
//            "phoneNum_1",
//            "authenticationEmail_1",
//            "name_2",
//            "phoneNum_2",
//            "authenticationEmail_2",
//    })
//    void 이메일_찾기_예외_Not_Blank_검증_테스트(String input) throws Exception {
//        // given
//        UserInfo kingUser = userService.findUserById(kingUserId);
//        final String url = "/admin/find/email";
//
//        FindEmailReqDto requestDto = FindEmailReqDto.builder()
//                .name(kingUser.getName())
//                .authenticationEmail(kingUser.getEmail())
//                .phoneNum(input)
//                .build();
//
//        switch (input) {
//            case "name":
//                requestDto.setName("");
//                break;
//            case "phoneNum":
//                requestDto.setPhoneNum("");
//                break;
//            case "authenticationEmail":
//                requestDto.setAuthenticationEmail("");
//                break;
//            case "name_1":
//                requestDto.setName(" ");
//                break;
//            case "phoneNum_1":
//                requestDto.setPhoneNum(" ");
//                break;
//            case "authenticationEmail_1":
//                requestDto.setAuthenticationEmail(" ");
//                break;
//            case "name_2":
//                requestDto.setName(null);
//                break;
//            case "phoneNum_2":
//                requestDto.setPhoneNum(null);
//                break;
//            case "authenticationEmail_2":
//                requestDto.setAuthenticationEmail(null);
//                break;
//        }
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody)
//                        .header("authorization", "Bearer " + token) // token header에 담기
//                )
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
//    }
//}