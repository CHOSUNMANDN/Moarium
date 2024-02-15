//package back.springbootdeveloper.seungchan.controller;
//
//import back.springbootdeveloper.seungchan.constant.filter.exception.ExceptionMessage;
//import back.springbootdeveloper.seungchan.testutills.TestMakeObject;
//import back.springbootdeveloper.seungchan.testutills.TestSetUp;
//import back.springbootdeveloper.seungchan.testutills.TestUtills;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.NullSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@TestPropertySource(locations = "classpath:/messages/validation/validation.properties")
//@SpringBootTest()
//@AutoConfigureMockMvc
//class LoginPageControllerTest {
//    @Autowired
//    protected MockMvc mockMvc;
//    @Autowired
//    protected ObjectMapper objectMapper;
//    @Autowired
//    private DatabaseService databaseService;
//    @Autowired
//    private TestSetUp testSetUp;
//    @Autowired
//    private TempUserRepository tempUserRepository;
//    private UserInfo kingUser;
//    @Value("${validation.email.notblank}")
//    private String MESSAGE_EMAIL_NOT_NULL;
//    @Value("${validation.password.notblank}")
//    private String MESSAGE_PASSWORD_NOT_NULL;
//    @Value("${validation.email.invalid}")
//    private String MESSAGE_EMAIL_INVALID;
//
//    @BeforeEach
//    public void setUp() {
//        databaseService.deleteAllDatabase();
//        this.kingUser = testSetUp.setUpKingUser();
//    }
//
//    @Test
//    void 유저_로그인_테스트() throws Exception {
//        // when
//        String email = kingUser.getEmail();
//        String password = "testuser1!";
//
//        // given
//        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
//                .email(email)
//                .password(password)
//                .build();
//
//        final String url = "/login";
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(userLoginRequest);
//
//        ResultActions result = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestBody));
//
//        result
//                .andExpect(jsonPath("$.name").value(kingUser.getName()))
//                .andExpect(jsonPath("$.userId").value(kingUser.getId()))
//                .andExpect(jsonPath("$.nuriKing").value(true));
//    }
//
//
//    @ParameterizedTest
//    @NullSource
//    void 유저_로그인_테스트_예외_테스트_email_값이_없는_경우_1(String email) throws Exception {
//        String password = kingUser.getPassword();
//
//        // given
//        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
//                .email(email)
//                .password(password)
//                .build();
//
//        final String url = "/login";
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(userLoginRequest);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody))
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(message).isEqualTo(MESSAGE_EMAIL_NOT_NULL);
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @ParameterizedTest
//    @NullSource
//    void 유저_로그인_테스트_예외_테스트_password_값이_없는_경우(String password) throws Exception {
//        String email = kingUser.getEmail();
//
//        // given
//        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
//                .email(email)
//                .password(password)
//                .build();
//
//        final String url = "/login";
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(userLoginRequest);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody))
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(message).isEqualTo(MESSAGE_PASSWORD_NOT_NULL);
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
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
//    void 유저_로그인_테스트_예외_테스트_형식에_맞지_않는_이메일(String email) throws Exception {
//        String password = kingUser.getPassword();
//
//        // given
//        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
//                .email(email)
//                .password(password)
//                .build();
//
//        final String url = "/login";
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(userLoginRequest);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody))
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(message).isEqualTo(MESSAGE_EMAIL_INVALID);
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    void 유저_로그인_테스트_예외_테스트_해당_유저가_없는_경우() throws Exception {
//        String email = "NOTING@gmail.com";
//        String password = "NOTING_PASSWORD";
//
//        // given
//        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
//                .email(email)
//                .password(password)
//                .build();
//
//        final String url = "/login";
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(userLoginRequest);
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody))
//                .andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        // JSON 응답을 Map으로 변환
//        String message = TestUtills.getMessageFromResponse(response);
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(message).isEqualTo(ExceptionMessage.USER_NOT_EXIST_MESSAGE.get());
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    void 신입유저를_등록을_확인_하는_테스트() throws Exception {
//        // given
//        final String url = "/sign";
//
//        TempUser tempUser = getTempUser();
//        TempUserFormReqDto request = getTempUserFormReqDto(tempUser);
//
//        // when
//        final String requestBody = objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestBody));
//
//        TempUser result = tempUserRepository.findByEmail(request.getEmail()).get();
//        // then
//        assertThat(tempUserRepository.count()).isEqualTo(1);
//        assertThat(result.getEmail()).isEqualTo(request.getEmail());
//        assertThat(result.getName()).isEqualTo(result.getName());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "name",
//            "phoneNum",
//            "major",
//            "gpa",
//            "address",
//            "specialtySkill",
//            "hobby",
//            "mbti",
//            "studentId",
//            "birthDate",
//            "advantages",
//            "disadvantage",
//            "selfIntroduction",
//            "photo",
//            "email",
//            "password",
//    })
//    void 신입유저를_등록을_확인_예외_테스트_null(String check) throws Exception {
//        // given
//        final String url = "/sign";
//
//        TempUser tempUser = getTempUser();
//        TempUserFormReqDto request = getTempUserFormReqDto(tempUser);
//
//        switch (check) {
//            case "name":
//                request.setName(null);
//                break;
//            case "phoneNum":
//                request.setPhoneNum(null);
//                break;
//            case "major":
//                request.setMajor(null);
//                break;
//            case "gpa":
//                request.setGpa(null);
//                break;
//            case "address":
//                request.setAddress(null);
//                break;
//            case "specialtySkill":
//                request.setSpecialtySkill(null);
//                break;
//            case "hobby":
//                request.setHobby(null);
//                break;
//            case "mbti":
//                request.setMbti(null);
//                break;
//            case "studentId":
//                request.setStudentId(null);
//                break;
//            case "birthDate":
//                request.setBirthDate(null);
//                break;
//            case "advantages":
//                request.setAdvantages(null);
//                break;
//            case "disadvantage":
//                request.setDisadvantage(null);
//                break;
//            case "selfIntroduction":
//                request.setSelfIntroduction(null);
//                break;
//            case "photo":
//                request.setPhoto(null);
//                break;
//            case "email":
//                request.setEmail(null);
//                break;
//            case "password":
//                request.setPassword(null);
//                break;
//        }
//        MockHttpServletResponse response = getTempUserFormReqResponseOfPost(request);
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "name",
//            "phoneNum",
//            "major",
//            "gpa",
//            "address",
//            "specialtySkill",
//            "hobby",
//            "mbti",
//            "studentId",
//            "birthDate",
//            "advantages",
//            "disadvantage",
//            "selfIntroduction",
//            "photo",
//            "email",
//            "password",
//    })
//    void 신입유저를_등록을_확인_예외_테스트_Not_Blank(String check) throws Exception {
//        // given
//        final String url = "/sign";
//
//        TempUser tempUser = getTempUser();
//        TempUserFormReqDto request = getTempUserFormReqDto(tempUser);
//
//        switch (check) {
//            case "name":
//                request.setName("");
//                break;
//            case "phoneNum":
//                request.setPhoneNum("");
//                break;
//            case "major":
//                request.setMajor("");
//                break;
//            case "gpa":
//                request.setGpa("");
//                break;
//            case "address":
//                request.setAddress("");
//                break;
//            case "specialtySkill":
//                request.setSpecialtySkill("");
//                break;
//            case "hobby":
//                request.setHobby("");
//                break;
//            case "mbti":
//                request.setMbti("");
//                break;
//            case "studentId":
//                request.setStudentId("");
//                break;
//            case "birthDate":
//                request.setBirthDate("");
//                break;
//            case "advantages":
//                request.setAdvantages("");
//                break;
//            case "disadvantage":
//                request.setDisadvantage("");
//                break;
//            case "selfIntroduction":
//                request.setSelfIntroduction("");
//                break;
//            case "photo":
//                request.setPhoto("");
//                break;
//            case "email":
//                request.setEmail("");
//                break;
//            case "password":
//                request.setPassword("");
//                break;
//        }
//        MockHttpServletResponse response = getTempUserFormReqResponseOfPost(request);
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @ParameterizedTest
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
//    void 신입유저를_등록을_확인_예외_테스트_Phone_Num_패턴_검증(String badPhoneNumber) throws Exception {
//        // given
//        final String url = "/sign";
//
//        TempUser tempUser = getTempUser();
//        TempUserFormReqDto request = getTempUserFormReqDto(tempUser);
//
//        // when
//        request.setPhoneNum(badPhoneNumber);
//        MockHttpServletResponse response = getTempUserFormReqResponseOfPost(request);
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    void 신입유저를_등록을_확인_예외_테스트_disadvantage() throws Exception {
//        // given
//        final String url = "/sign";
//
//        TempUser tempUser = getTempUser();
//        TempUserFormReqDto request = getTempUserFormReqDto(tempUser);
//
//        // when
//        request.setDisadvantage("100이하의 단점");
//        MockHttpServletResponse response = getTempUserFormReqResponseOfPost(request);
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    void 신입유저를_등록을_확인_예외_테스트_advantage() throws Exception {
//        // given
//        final String url = "/sign";
//
//        TempUser tempUser = getTempUser();
//        TempUserFormReqDto request = getTempUserFormReqDto(tempUser);
//
//        // when
//        request.setAdvantages("100이하의 장점");
//        MockHttpServletResponse response = getTempUserFormReqResponseOfPost(request);
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
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
//    void 신입유저를_등록을_확인_예외_테스트_email_검증(String badEmail) throws Exception {
//        // given
//        final String url = "/sign";
//
//        TempUser tempUser = getTempUser();
//        TempUserFormReqDto request = getTempUserFormReqDto(tempUser);
//
//        // when
//        request.setEmail(badEmail);
//        MockHttpServletResponse response = getTempUserFormReqResponseOfPost(request);
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    void 신입유저를_등록을_확인_예외_테스트_selfIntroduction() throws Exception {
//        // given
//        final String url = "/sign";
//
//        TempUser tempUser = getTempUser();
//        TempUserFormReqDto request = getTempUserFormReqDto(tempUser);
//
//        // when
//        request.setSelfIntroduction("200이하의 자기소개");
//        MockHttpServletResponse response = getTempUserFormReqResponseOfPost(request);
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
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
//    void 신입유저를_등록을_확인_예외_테스트_password_검증(String badPassword) throws Exception {
//        // given
//        final String url = "/sign";
//
//        TempUser tempUser = getTempUser();
//        TempUserFormReqDto request = getTempUserFormReqDto(tempUser);
//
//        // when
//        request.setPassword(badPassword);
//        MockHttpServletResponse response = getTempUserFormReqResponseOfPost(request);
//
//        // JSON 응답을 Map으로 변환
//        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
//
//        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    private MockHttpServletResponse getTempUserFormReqResponseOfPost(TempUserFormReqDto request) throws Exception {
//        final String requestBody = objectMapper.writeValueAsString(request);
//        final String url = "/sign";
//
//        MvcResult result = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestBody))
//                .andReturn();
//
//        return result.getResponse();
//    }
//
//    private TempUserFormReqDto getTempUserFormReqDto(TempUser tempUser) {
//        String password = "testuser1!";
//
//        return new TempUserFormReqDto(
//                tempUser.getName(),
//                tempUser.getPhoneNum(),
//                tempUser.getMajor(),
//                tempUser.getGpa(),
//                tempUser.getAddress(),
//                tempUser.getSpecialtySkill(),
//                tempUser.getHobby(),
//                tempUser.getMbti(),
//                tempUser.getStudentId(),
//                tempUser.getBirthDate(),
//                tempUser.getAdvantages(),
//                tempUser.getDisadvantage(),
//                tempUser.getSelfIntroduction(),
//                tempUser.getPhoto(),
//                tempUser.getEmail(),
//                password
//        );
//    }
//
//    private TempUser getTempUser() {
//        String name = "신입_1";
//        String email = "test1@test.com";
//
//        return TestMakeObject.makeNewUserOb(email, name);
//    }
//}