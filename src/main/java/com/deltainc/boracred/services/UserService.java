package com.deltainc.boracred.services;

import com.deltainc.boracred.entity.Users;
import com.deltainc.boracred.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    public Users getByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

}
