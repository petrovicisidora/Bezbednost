package com.pks.pks.service;


import com.pks.pks.config.SecurityUtils;
import com.pks.pks.model.dto.UserDTO;
import com.pks.pks.model.User;
import com.pks.pks.model.UserType;
import com.pks.pks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder;

    @Override
    public User register(UserDTO userDTO) {

        User user = userRepository.getByEmail(userDTO.getEmail());

        if(user != null) {
            return null;
        }

        user = new User();
        user.setEmail(userDTO.getEmail());;
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUserType(UserType.intermediary);

        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {

        String email = SecurityUtils.getCurrentUserLogin().get();

        return userRepository.getByEmail(email);
    }
}