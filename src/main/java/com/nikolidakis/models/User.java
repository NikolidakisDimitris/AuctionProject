package com.nikolidakis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.nikolidakis.utils.Utils.md5;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    @JsonIgnore(value = true)
    Long id; // primary key

    @Column(name = "username")
    @NotBlank
    @Size(max = 30)
    private String username; //unique

    @Column(name = "password")
    @NotBlank
    @Size(max = 512)
    @JsonIgnore(value = true)
    private String password;

    @Column(name = "firstname")
    @NotBlank
    @Size(max = 30)
    @JsonIgnore(value = true)
    private String firstName;

    @Column(name = "lastname")
    @NotBlank
    @Size(max = 30)
    @JsonIgnore(value = true)
    private String lastName;

    @Column(name = "email")
    @NotBlank
    @Email
    @Size(max = 30)
    @JsonIgnore(value = true)
    private String email;

    @Column(name = "phone")
    @NotBlank
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @Size(max = 15)
    @JsonIgnore(value = true)
    private String phone;

    @Column(name = "country")
    @NotBlank
    @Size(max = 30)
    @JsonIgnore(value = true)
    private String country;

    @Column(name = "city")
    @NotBlank
    @Size(max = 30)
    @JsonIgnore(value = true)
    private String city;

    @Column(name = "address")
    @NotBlank
    @Size(max = 50)
    @JsonIgnore(value = true)
    private String address;

    @Column(name = "afm")
    @NotBlank
    @Size(max = 30)
    @JsonIgnore(value = true)
    private String afm;

    public User(Long id, @NotBlank @Size(max = 30) String username, @NotBlank @Size(max = 512) String password,
                @NotBlank @Size(max = 30) String firstName, @NotBlank @Size(max = 30) String lastName,
                @NotBlank @Email @Size(max = 30) String email,
                @NotBlank @Pattern(regexp = "^(0|[1-9][0-9]*)$") @Size(max = 15) String phone,
                @NotBlank @Size(max = 30) String country, @NotBlank @Size(max = 30) String city,
                @NotBlank @Size(max = 50) String address, @NotBlank @Size(max = 30) String afm) {
        this.id = id;
        this.username = username;
        this.password = md5(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.address = address;
        this.afm = afm;
    }

    public void setPassword(@NotBlank @Size(max = 512) String password) {
        this.password = md5(password);
    }
}
