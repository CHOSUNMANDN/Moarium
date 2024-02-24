package back.springbootdeveloper.seungchan.filter.handler;

import back.springbootdeveloper.seungchan.constant.SuggestionConstant;
import back.springbootdeveloper.seungchan.constant.filter.CustomHttpStatus;
import back.springbootdeveloper.seungchan.constant.filter.exception.ExceptionMessage;
import back.springbootdeveloper.seungchan.filter.exception.ApiException;
import back.springbootdeveloper.seungchan.filter.exception.EmailSameMatchException;
import back.springbootdeveloper.seungchan.filter.exception.external.DefaultExternalApiClientErrorException;
import back.springbootdeveloper.seungchan.filter.exception.external.DefaultExternalApiServerErrorException;
import back.springbootdeveloper.seungchan.filter.exception.judgment.*;
import back.springbootdeveloper.seungchan.filter.exception.user.NewUserRegistrationException;
import back.springbootdeveloper.seungchan.filter.exception.user.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {UserNotExistException.class})
    public ResponseEntity<Object> handleUserNotExistException(UserNotExistException e) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomHttpStatus customHttpStatus = CustomHttpStatus.USER_NOT_EXIST;

        ApiException apiException = new ApiException(
                ExceptionMessage.USER_NOT_EXIST_MESSAGE.get(),
                httpStatus,
                customHttpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomHttpStatus customHttpStatus = CustomHttpStatus.DATA_VALID;

        ApiException apiException = new ApiException(
//                ExceptionMessage.USER_NOT_EXIST_MESSAGE.get(),
                errorMessage,
                httpStatus,
                customHttpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {NewUserRegistrationException.class})
    public ResponseEntity<Object> handleNewUserRegistrationExceptionException(NewUserRegistrationException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                ExceptionMessage.NEW_USER_REGISTRATION_MESSAGE.get(),
                httpStatus,
                httpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {WeekendException.class})
    public ResponseEntity<Object> handleWeekendException(WeekendException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomHttpStatus customHttpStatus = CustomHttpStatus.WEEKEND;

        ApiException apiException = new ApiException(
                ExceptionMessage.WEEKEND_MESSAGE.get(),
                httpStatus,
                customHttpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {PasswordConfirmationException.class})
    public ResponseEntity<Object> handlePasswordConfirmationException(PasswordConfirmationException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomHttpStatus customHttpStatus = CustomHttpStatus.PASSWORD_CONFIRMATION;

        ApiException apiException = new ApiException(
                ExceptionMessage.PASSWORD_CONFIRMATION.get(),
                httpStatus,
                customHttpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {UpdateFailedException.class})
    public ResponseEntity<Object> handleUpdateFailedException(UpdateFailedException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomHttpStatus customHttpStatus = CustomHttpStatus.UPDATE_FAILED; // add

        ApiException apiException = new ApiException(
                ExceptionMessage.UPDATE_FAILED.get(), // add
                httpStatus,
                customHttpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {EmailSameMatchException.class})
    public ResponseEntity<Object> handleEmailsMatchException(EmailSameMatchException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomHttpStatus customHttpStatus = CustomHttpStatus.EMAIL_SAME_MATCH; // add

        ApiException apiException = new ApiException(
                ExceptionMessage.EMAIL_SAME_MATCH.get(), // add
                httpStatus,
                customHttpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {MissMatchesPasswordException.class})
    public ResponseEntity<Object> handleMissMatchesPasswordException(MissMatchesPasswordException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomHttpStatus customHttpStatus = CustomHttpStatus.PASSWORD_MISS_MATCHES; // add

        ApiException apiException = new ApiException(
                ExceptionMessage.PASSWORD_MISS_MATCH.get(), // add
                httpStatus,
                customHttpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(MissMatchesPasswordException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomHttpStatus customHttpStatus = CustomHttpStatus.ENTITY_NOT_FOUND; // add

        ApiException apiException = new ApiException(
                ExceptionMessage.ENTITY_NOT_FOUND.get(), // add
                httpStatus,
                customHttpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {InvalidSelectionClassificationException.class}) // add
    public ResponseEntity<Object> handleInvalidSelectionClassificationException(InvalidSelectionClassificationException e) { // add
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomHttpStatus customHttpStatus = CustomHttpStatus.INVALID_SELECTION_CLASSIFICATION; // add
        ApiException apiException = new ApiException(
                ExceptionMessage.INVALID_SELECTION_CLASSIFICATION.get(), // add
                httpStatus,
                customHttpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }



    // 외부 API 4xx 응답시 발생하는 기본 Exception
    @ExceptionHandler(value = {DefaultExternalApiClientErrorException.class})
    public ResponseEntity<Object> handleDefaultExternalApiClientErrorException(DefaultExternalApiClientErrorException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                ExceptionMessage.EXTERNAL_SERVICE_BAD_REQUEST.get(),
                httpStatus,
                httpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    // 외부 API 5xx 응답시 발생하는 기본 Exception
    @ExceptionHandler(value = {DefaultExternalApiServerErrorException.class})
    public ResponseEntity<Object> handleDefaultExternalApiServerErrorException(DefaultExternalApiServerErrorException e){
        HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        ApiException apiException = new ApiException(
                ExceptionMessage.EXTERNAL_SERVICE_UNAVAILABLE.get(),
                httpStatus,
                httpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,httpStatus);
    }
}
