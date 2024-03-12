package com.oasis.TaskManagementApplication.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonPropertyOrder({
        "success",
        "message"
})
public class APIResponse {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    @JsonIgnore
    private HttpStatus status;

    public APIResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public APIResponse(Boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

}
