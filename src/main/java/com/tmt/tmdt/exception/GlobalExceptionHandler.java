package com.tmt.tmdt.exception;


import com.tmt.tmdt.dto.response.ErrResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    //xu ly tat ca cac exception he thong(unchecked exception) (bindding error va get error da handle)
    @ExceptionHandler({ResourceNotFoundException.class})
    public String handleResourceNotFound(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "admin/error/404Err";
    }


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrResponse> handleUncheckedException(Exception e, Model model) {
        return new ResponseEntity<>(new ErrResponse(new ErrResponse.Meta(e.getMessage())), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({RuntimeException.class})
//    public ResponseEntity<String> handleUncheckedException(Exception e, Model model) {
//        return new ResponseEntity<>("error message !!!", HttpStatus.BAD_REQUEST);
//    }



}


