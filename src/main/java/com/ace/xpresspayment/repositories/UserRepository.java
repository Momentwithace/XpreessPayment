package com.ace.xpresspayment.repositories;


import com.ace.xpresspayment.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByEmail(String email);

    User findByEmail(String email);
}
