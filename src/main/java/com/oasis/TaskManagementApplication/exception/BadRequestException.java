package com.oasis.TaskManagementApplication.exception;

import com.oasis.TaskManagementApplication.dto.res.APIResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private APIResponse apiResponse;

    public BadRequestException(APIResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
