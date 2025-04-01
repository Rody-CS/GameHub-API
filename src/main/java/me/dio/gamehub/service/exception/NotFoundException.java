package me.dio.gamehub.service.exception;

import java.io.Serial;

public class NotFoundException extends BusinessException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super("Recurso não encontrado.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
