package com.pks.pks.model.dto;

import com.pks.pks.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    public UserType userType;
    public long id;
    public String name;
    public String surname;
    public String email;
    public String password;
}
