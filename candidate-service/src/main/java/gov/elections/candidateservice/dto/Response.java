package gov.elections.candidateservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.elections.candidateservice.common.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private Status status;
    private T data;
    private String message;
}