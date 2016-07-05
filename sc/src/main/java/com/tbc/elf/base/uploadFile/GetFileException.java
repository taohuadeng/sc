package com.tbc.elf.base.uploadFile;


public class GetFileException extends RuntimeException {
    public GetFileException() {
        super();
    }

    public GetFileException(String message) {
        super(message);
    }

    public GetFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetFileException(Throwable cause) {
        super(cause);
    }

}
