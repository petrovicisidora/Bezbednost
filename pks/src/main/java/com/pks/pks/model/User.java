package com.pks.pks.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    public UserType userType;
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    public long id;
    public String name;
    public String surname;
    public String email;
    public String password;

    public String getRole() {
        return userType.toString();
    }
}
