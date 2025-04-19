package uk.gov.hmcts.case_management.exception;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorObject> handleException(MethodArgumentNotValidException ex, WebRequest request) {
    ErrorObject errorObject = new ErrorObject();

    errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
    errorObject.setMessage(ex.getBindingResult().getFieldErrors().stream().map(FieldError::getField).collect(Collectors.joining(", ")) + " must not be null");
    errorObject.setTimestamp(new Date());

    return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResponseStatusException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorObject> handleNotFoundException(ResponseStatusException ex, WebRequest request) {
    ErrorObject errorObject = new ErrorObject();

    errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
    errorObject.setMessage("Task not found");
    errorObject.setTimestamp(new Date());

    return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorObject> handleInvalidId(MethodArgumentTypeMismatchException ex, WebRequest request) {
    ErrorObject errorObject = new ErrorObject();

    errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
    errorObject.setMessage("ID must be a number");
    errorObject.setTimestamp(new Date());

    return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
  }
}
