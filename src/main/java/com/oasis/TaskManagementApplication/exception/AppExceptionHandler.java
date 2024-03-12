package com.oasis.TaskManagementApplication.exception;

import com.oasis.TaskManagementApplication.dto.res.APIResponse;
import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

    // OBJECTS REQUEST FIELDS EXCEPTIONS START
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> resolveException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> messages = new ArrayList<>(fieldErrors.size());
        for (FieldError error : fieldErrors) {
            messages.add(error.getField() + " - " + error.getDefaultMessage());
        }
        return new ResponseEntity<>(new ExceptionResponse(messages, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> resolveException(MethodArgumentTypeMismatchException ex) {
        String message = "Parameter '" + ex.getParameter().getParameterName() + "' must be '"
                + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + "'";
        APIResponse apiResponse = new APIResponse(Boolean.FALSE, message);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    // OBJECTS REQUEST FIELDS EXCEPTIONS END
    // CUSTOM APPLICATION EXCEPTIONS START
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse<?>> resolveException(BadRequestException exception) {
       String message = exception.getMessage();
        BaseResponse<?> response = new BaseResponse<>(Boolean.FALSE, message, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> resolveException(UnauthorizedException exception) {
        APIResponse apiResponse = exception.getApiResponse();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<APIResponse> resolveException(NoResourceFoundException exception) {
        APIResponse apiResponse = new APIResponse(Boolean.FALSE, exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
    // CUSTOM APPLICATION EXCEPTIONS END
    // HTTP METHODS EXCEPTIONS START
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<APIResponse> resolveException(HttpRequestMethodNotSupportedException ex) {
        String message = "Request method '" + ex.getMethod() + "' not supported. List of all supported methods - "
                + ex.getSupportedHttpMethods();
        APIResponse apiResponse = new APIResponse(Boolean.FALSE, message);
        return new ResponseEntity<>(apiResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> resolveException(HttpMessageNotReadableException ex) {
        String message = "Please provide Request Body in valid JSON format";
        List<String> messages = new ArrayList<>(1);
        messages.add(message);
        return new ResponseEntity<>(new ExceptionResponse(messages, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
    // HTTP METHODS EXCEPTIONS END
    // SPRING SECURITY USER DETAILS EXCEPTIONS START
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> resolveAuthenticationException(AuthenticationException exception) {

        APIResponse apiResponse = null;

        if (exception instanceof BadCredentialsException) {
            apiResponse = new APIResponse(Boolean.FALSE, exception.getMessage());
        }

        if (exception instanceof DisabledException) {
            apiResponse = new APIResponse(Boolean.FALSE, exception.getMessage());
        }

        if (exception instanceof AccountExpiredException) {
            apiResponse = new APIResponse(Boolean.FALSE, exception.getMessage());
        }

        if (exception instanceof CredentialsExpiredException) {
            apiResponse = new APIResponse(Boolean.FALSE, exception.getMessage());
        }


        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LockedException.class)
    @ResponseBody
    public ResponseEntity<APIResponse> resolveLockedException(LockedException exception) {
        APIResponse apiResponse = new APIResponse(Boolean.FALSE, exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    // SPRING SECURITY USER DETAILS EXCEPTIONS END
    // JWT EXCEPTIONS START
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(JwtException.class)
    public final ResponseEntity<APIResponse> handleJwtException(JwtException ex) {
        APIResponse apiResponse = null;

        if (ex instanceof SignatureException) {
            apiResponse = new APIResponse(Boolean.FALSE, "Invalid JWT signature");
        }
        if (ex instanceof MalformedJwtException) {
            apiResponse = new APIResponse(Boolean.FALSE, "Invalid JWT token");
        }

        if (ex instanceof UnsupportedJwtException) {
            apiResponse = new APIResponse(Boolean.FALSE, "Unsupported JWT token");
        }

        if (ex instanceof ExpiredJwtException) {
            apiResponse = new APIResponse(Boolean.FALSE, "Expired JWT token");
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    // JWT EXCEPTIONS END
    // APPLICATION FALLBACK EXCEPTIONS HANDLER
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> resolveExceptions(Exception exception) {
        log.info("FALLBACK ERROR HERE ::-> ", exception.fillInStackTrace());
        APIResponse apiResponse = new APIResponse(Boolean.FALSE, exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
