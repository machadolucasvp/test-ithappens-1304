package com.ithappens.interview.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Size(min=4, max=40,message="Name should be have at least 4 and at most 40 characters")
    private String nome;

    @Email
    @NotEmpty(message="Email should not be empty")
    private String email;

    @NotEmpty(message="Password should not be empty")
    private String password;

    private boolean active;

    private String pictureUrl;

}
