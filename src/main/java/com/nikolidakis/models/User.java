package com.nikolidakis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserID")
    Long id; // primary key

    @Column(name = "Username")
    @NotBlank
    @Size(max = 30)
    private String username; //unique

    @Column(name = "Password")
    @NotBlank
    @Size(max = 30)
    private String password;

    @Column(name = "Firstname")
    @NotBlank
    @Size(max = 30)
    private String firstName;

    @Column(name = "Lastname")
    @NotBlank
    @Size(max = 30)
    private String lastName;

    @Column(name = "Email")
    @NotBlank
    @Email
    @Size(max = 30)
    private String email;

    @Column(name = "Phone")
    @NotBlank
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @Size(max = 15)
    private String phone;

    @Column(name = "Country")
    @NotBlank
    @Size(max = 30)
    private String country;

    @Column(name = "City")
    @NotBlank
    @Size(max = 30)
    private String city;

    @Column(name = "Address")
    @NotBlank
    @Size(max = 50)
    private String address;

    @Column(name = "AFM")
    @NotBlank
    @Size(max = 30)
    private String afm;
}
