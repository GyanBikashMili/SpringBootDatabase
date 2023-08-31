package com.example.springboot.email.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class SenderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String host;

    @NotNull
    @Min(value = 1)
    private int port;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}

