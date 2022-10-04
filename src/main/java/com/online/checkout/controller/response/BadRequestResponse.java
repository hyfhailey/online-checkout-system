package com.online.checkout.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
public class BadRequestResponse {

    private final Date timestamp;
    private final int status;
    private final List<ErrorGroup> invalidParams;

    public BadRequestResponse(final List<ErrorGroup> invalidParams) {
        this.timestamp = new Date();
        this.status = HttpStatus.BAD_REQUEST.value();
        this.invalidParams = invalidParams;
    }

    @Getter
    @AllArgsConstructor
    public static class InvalidParams {

        @JsonIgnore
        private String name;
        private String reason;
        private String code;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String requestStatus;

        public InvalidParams(String name, String reason, String code) {
            this.name = name;
            this.reason = reason;
            this.code = code;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorGroup {

        private String name;
        private List<InvalidParams> errors;

        public ErrorGroup(Map.Entry<String, List<InvalidParams>> errorGroup) {
            this.name = errorGroup.getKey();
            this.errors = errorGroup.getValue();
        }
    }
}
