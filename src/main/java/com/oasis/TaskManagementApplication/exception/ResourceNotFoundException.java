package com.oasis.TaskManagementApplication.exception;

import com.oasis.TaskManagementApplication.dto.res.APIResponse;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private APIResponse apiResponse;

    public ResourceNotFoundException(APIResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
