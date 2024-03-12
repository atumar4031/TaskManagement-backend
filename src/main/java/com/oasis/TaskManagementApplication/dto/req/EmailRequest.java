package com.oasis.TaskManagementApplication.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailRequest {
  private String  recipient;
  private String messageBody;
  private String subject;
}
