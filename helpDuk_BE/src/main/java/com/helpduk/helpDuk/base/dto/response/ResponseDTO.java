package com.helpduk.helpDuk.base.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {

    private int status;
    private boolean success;
    private String Message;
    private T data;

}
