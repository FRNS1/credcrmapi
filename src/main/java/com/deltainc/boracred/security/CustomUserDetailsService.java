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
            throw new UsernameNotFoundException("\"User not found\"");
        }
        return UserPrincipal.create(user);
    }

    public void verifyUserCredentials(LoginDTO login){
        UserDetails user = loadUserByUsername(login.getUsername());
        boolean passwordIsTheSame = SecurityConfig.passwordEncoder().matches(login.getPassword(), user.getPassword());
        if (!passwordIsTheSame){
            throw new BadPasswordException("\"Bad password\"");
        }
    }
}
