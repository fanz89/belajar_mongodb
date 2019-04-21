package com.mif.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Document(collection = "customers")
public class Customer {

    @Id
    private Long customerId;

    @NotBlank(message = "Nama harus diisi!!")
    @Size(max = 100)
    private String fullname;

    @NotBlank(message = "Email harus diisi!!")
    @Size(max = 75)
    @Email
    private String email;

    @NotBlank(message = "Nomor HP harus diisi!!")
    @Size(min = 10, max = 30)
    private String phoneNumber;

}
