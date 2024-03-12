package com.oasis.TaskManagementApplication.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse<T>{

    private Boolean flag;
    private String message;
    private T result;

}
