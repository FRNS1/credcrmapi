package com.deltainc.boracred.controller;

import com.deltainc.boracred.dto.LoginDTO;
import com.deltainc.boracred.dto.RegisterDTO;
import com.deltainc.boracred.dto.TokenDTO;
import com.deltainc.boracred.entity.Contacts;
import com.deltainc.boracred.entity.Grupos;
import com.deltainc.boracred.entity.Users;
import com.deltainc.boracred.repositories.GruposRepository;
import com.deltainc.boracred.repositories.UsersRepository;
import com.deltainc.boracred.security.CustomUserDetailsService;
import com.deltainc.boracred.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.deltainc.boracred.security.SecurityConfig.passwordEncoder;

@Controller
@RequestMapping("api/v1/users")
public class UsersController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private GruposRepository gruposRepository;


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerData){
        try{
            Users user = new Users();
            user.setNome(registerData.getNome());
            user.setUsername(registerData.getUsername());
            user.setPassword(passwordEncoder().encode(registerData.getPassword()));
            user.setEmpresa(registerData.getEmpresa());
            user.setSetor(registerData.getSetor());
            user.setEmail(registerData.getEmail());
            Optional<Grupos> OptionalGrupo = gruposRepository.findById(registerData.getGrupos_grupo_id());
            Grupos grupo = OptionalGrupo.get();
            user.setGrupo_id(grupo);
            usersRepository.save(user);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }catch (Exception error){
            Map<String, Object> response = new HashMap<>();
            response.put("erro", error.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginData){
        try {
            userDetailsService.verifyUserCredentials(loginData);
            String token = jwtService.generateToken(loginData.getUsername());
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(token);
            Users user = usersRepository.findByUsername(loginData.getUsername());
            String nomeUser = user.getNome();
            String email = user.getEmail();
            Integer userId = user.getUser_id();
            Grupos userGroup = user.getGrupo_id();
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("nome", nomeUser);
            response.put("logged", true);
            response.put("email", email);
            response.put("userid", userId);
            response.put("usergroup", userGroup.getNome_grupo());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception error) {
            Map<String, Object> response = new HashMap<>();
            response.put("erro", error.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
