package com.pks.pks.service;

import com.pks.pks.model.dto.UserDTO;
import com.pks.pks.model.User;

import java.util.List;

public interface UserService {

    User register(UserDTO userDTO);
    User getCurrentUser();
}
