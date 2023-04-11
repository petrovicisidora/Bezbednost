package com.pks.pks.repository;


import com.pks.pks.model.User;
import com.pks.pks.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

/**
 * JPA repository for management of the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    List<User> findAllByUserType(UserType userType);


    User getByEmail(String email);

}