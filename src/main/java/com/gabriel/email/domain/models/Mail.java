package com.gabriel.email.domain.models;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class Mail {

    private String nomeDestino;
    private String emailDestino;
    private String mensagem;
}