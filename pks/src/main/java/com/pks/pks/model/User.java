package com.pks.pks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public UserType userType;
    public long id;
    public String name;
    public String surname;
    public String email;
    public String password;

    public String getRole() {
        return userType.toString();
    }
}
