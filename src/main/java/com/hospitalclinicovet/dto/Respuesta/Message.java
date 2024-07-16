package com.hospitalclinicovet.dto.Respuesta;

import lombok.Getter;
import lombok.Setter;

/**
 * Constructor de un json que se envia generarmente con la informacion de un fallo a la hora de ejecutar un endpoint
 */
@Getter
@Setter
public class Message {
    /**
     * Mensaje personailizado.
     */
    private String message;

    /**
     * Constructor con un mensaje.
     *
     * @param message El mensaje que se envia en el endpoint.
     */
    public Message(String message) {
        this.message = message;
    }

}

