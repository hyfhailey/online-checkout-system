package com.online.checkout.controller;

import com.online.checkout.controller.response.BadRequestResponse;
import com.online.checkout.controller.response.BadRequestResponse.InvalidParams;
import com.online.checkout.controller.response.BadRequestResponse.ErrorGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static com.online.checkout.util.Errors.DEFAULT_ERROR_CODE;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    private final Properties prop;
    private ResourceLoader resourceLoader;

    public ApiExceptionHandler(ResourceLoader resourceLoader, Environment environment) {
        this.resourceLoader = resourceLoader;
        prop = getErrorMessageProperties();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BadRequestResponse> handleConstraintViolationException(
            final ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(ex.getConstraintViolations()
                .stream().map(this::invalidParams));
    }

    @ExceptionHandler
    public ResponseEntity<BadRequestResponse> handleAllOtherExceptions(final Exception ex) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(Stream.of(invalidParams(ex)));
    }

    private ResponseEntity<BadRequestResponse> buildResponseEntity(
            Stream<InvalidParams> invalidParams) {
        final BadRequestResponse requestResponse =
                new BadRequestResponse(buildResponseBodyFromInvalidParams(invalidParams));
        return new ResponseEntity<>(requestResponse, BAD_REQUEST);
    }

    private List<ErrorGroup> buildResponseBodyFromInvalidParams(Stream<InvalidParams> invalidParams) {
        return invalidParams
                .collect(groupingBy(InvalidParams::getName, toList()))
                .entrySet().stream()
                .map(ErrorGroup::new)
                .collect(toList());
    }

    private InvalidParams invalidParams(ConstraintViolation<?> constraintViolation) {
        return new InvalidParams(constraintViolation.getPropertyPath().toString(),
                prop.getProperty(constraintViolation.getMessage() + ".message",
                        constraintViolation.getMessage()),
                prop.getProperty(constraintViolation.getMessage() + ".code",
                        DEFAULT_ERROR_CODE));
    }

    private InvalidParams invalidParams(Exception e) {
        String error = e.getMessage();
        return new InvalidParams("generalError", error, DEFAULT_ERROR_CODE);
    }

    private Properties getErrorMessageProperties() {
        Resource resource = resourceLoader.getResource("classpath:error-messages.properties");
        Properties prop = new Properties();
        try {
            if (!isNull(resource)) {
                prop.load(resource.getInputStream());
            }
        } catch (IOException e) {

        }
        return prop;
    }
}
