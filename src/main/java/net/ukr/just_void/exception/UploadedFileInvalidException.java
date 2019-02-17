package net.ukr.just_void.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error while uploading file. File might be invalid")
public class UploadedFileInvalidException extends RuntimeException {
}