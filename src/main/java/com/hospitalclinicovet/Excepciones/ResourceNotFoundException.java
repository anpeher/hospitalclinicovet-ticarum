package com.hospitalclinicovet.Excepciones;

/**
 * excepcion personalizada que se utiliza cuando no se encuentra un recurso.
 */
public class ResourceNotFoundException extends RuntimeException{
    /**
     * Constructor de la excepcion con un string, que es el mensaje de la excepcion.
     *
     * @param message El mensaje detallado de la excepcion.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
