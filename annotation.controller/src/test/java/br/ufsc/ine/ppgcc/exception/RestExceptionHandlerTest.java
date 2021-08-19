package br.ufsc.ine.ppgcc.exception;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class RestExceptionHandlerTest {

    @Test
    public void handleException_Success(){
        final String message_error = "Message Error";

        RestExceptionHandler restHandler =new RestExceptionHandler();

        ResponseEntity<Object> errorResponse = restHandler.handleException(new Exception(message_error));

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse.getStatusCode());
        Assert.assertTrue(Objects.requireNonNull(errorResponse.getBody()).toString().contains(message_error));
    }
}
