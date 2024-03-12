package com.oasis.TaskManagementApplication.exception;

import com.oasis.TaskManagementApplication.dto.res.APIResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnauthorizedException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private APIResponse apiResponse;
    public UnauthorizedException(APIResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

}
