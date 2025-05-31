package com.agibank.client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document(collection = "clients")
public class Client {

    @Id
    private String id;

    @NotBlank(message = "CPF is required")
    @Pattern(regexp = "^\\d{11}$", message = "CPF must contain exactly 11 digits")
    @Indexed(unique = true)
    private String cpf;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;
}