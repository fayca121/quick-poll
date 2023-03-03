package dz.bououza.quickpoll.handler;

import dz.bououza.quickpoll.dto.error.ErrorDetail;
import dz.bououza.quickpoll.exception.PollApiException;
import dz.bououza.quickpoll.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                             HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource not found");
        errorDetail.setDetail(ex.getMessage());
        String requestPath = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestPath == null)
            requestPath = request.getRequestURI();
        errorDetail.setPath(requestPath);
        errorDetail.setDeveloperMessage(ex.getClass().getName());
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PollApiException.class)
    public ResponseEntity<ErrorDetail> handlePollApiException(PollApiException ex,
                                                              WebRequest webRequest){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(ex.getStatus().value());
        errorDetail.setTitle("Bad Request");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setPath(webRequest.getDescription(false));
        errorDetail.setDeveloperMessage(ex.getClass().getName());
        return new ResponseEntity<>(errorDetail,ex.getStatus());
    }
}
