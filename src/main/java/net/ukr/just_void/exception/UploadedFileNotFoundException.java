package net.ukr.just_void.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "File was not found on the server")
public class UploadedFileNotFoundException extends RuntimeException {
}