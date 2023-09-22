package com.deltainc.boracred.security;

import com.deltainc.boracred.dto.LoginDTO;
import com.deltainc.boracred.entity.Users;
import com.deltainc.boracred.exceptions.BadPasswordException;
import com.deltainc.boracred.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Users user = userService.getByUsername(username);
        if (user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("\"User not found\"");
        }
        return UserPrincipal.create(user);
    }

    public void verifyUserCredentials(LoginDTO login){
        System.out.println("Passei aqui 0");
        UserDetails user = loadUserByUsername(login.getUsername());
        System.out.println("Passei aqui 1");
        boolean passwordIsTheSame = SecurityConfig.passwordEncoder().matches(login.getPassword(), user.getPassword());
        System.out.println("Passei aqui 2");
        if (!passwordIsTheSame){
            System.out.println("Bad Password");
            throw new BadPasswordException("\"Bad password\"");
        }
    }
}
