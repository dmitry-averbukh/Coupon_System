package com.jb.Coupon_System_20.rest.ex;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidLoginException.class)
    protected ResponseEntity<ThatException> handleInvalidLoginException() {
        return new ResponseEntity<>(new ThatException("Incorrect username or password.")
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenTimeOutException.class)
    protected ResponseEntity<ThatException> handleTokenTimeOutException(){
        return new ResponseEntity<>(new ThatException("No activity for too much time. You have been logged out")
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdNotProvidedException.class)
    protected ResponseEntity<ThatException>handlerIdNotProvidedException(){
        return new ResponseEntity<>(new ThatException("ID not provided!"),HttpStatus.BAD_REQUEST);
    }

    private static class ThatException {
        private String message;

        public ThatException(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
