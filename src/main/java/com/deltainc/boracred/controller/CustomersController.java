package com.deltainc.boracred.controller;

import com.deltainc.boracred.dto.CustomerRegisterDTO;
import com.deltainc.boracred.dto.CustomerUpdateDTO;
import com.deltainc.boracred.dto.GetDataDTO;
import com.deltainc.boracred.entity.Address;
import com.deltainc.boracred.entity.Contacts;
import com.deltainc.boracred.entity.Customer;
import com.deltainc.boracred.entity.Users;
import com.deltainc.boracred.repositories.AddressRepository;
import com.deltainc.boracred.repositories.ContactsRepository;
import com.deltainc.boracred.repositories.CustomerRepository;
import com.deltainc.boracred.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("api/v1/customers")
public class CustomersController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ContactsRepository contactsRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UsersRepository usersRepository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody CustomerRegisterDTO csDataReg){
        try{
            Customer newCustomer = new Customer();
            Address newAddress = new Address();
            Contacts newContact = new Contacts();
            Optional<Users> OptionalUser = usersRepository.findById(csDataReg.getCreated_by());
            Users createdBy = OptionalUser.get();
            System.out.println(csDataReg);
            if ("true".equals(csDataReg.getIs_cnpj())) {
                newCustomer.set_cnpj(true);
                newCustomer.setRazao_social(csDataReg.getRazao_social());
                newCustomer.setNome_fantasia(csDataReg.getNome_fantasia());
                newCustomer.setCnpj(csDataReg.getCnpj());
                newCustomer.setData_abertura(csDataReg.getData_abertura());
                newCustomer.setSegmento(csDataReg.getSegmento());
                newCustomer.setCreated_by(createdBy);
                newCustomer.setBusiness(csDataReg.getBusiness().replace(" MASTER", ""));
                customerRepository.save(newCustomer);
                newContact.setCustomer(newCustomer);
                newContact.setEmail(csDataReg.getEmail());
                newContact.setTelefone(csDataReg.getTelefone());
                contactsRepository.save(newContact);
                newAddress.setCustomer_id(newCustomer);
                newAddress.setCep(csDataReg.getCep());
                newAddress.setLogradouro(csDataReg.getLogradouro());
                newAddress.setBairro(csDataReg.getBairro());
                newAddress.setCidade(csDataReg.getCidade());
                newAddress.setEstado(csDataReg.getEstado());
                newAddress.setPais(csDataReg.getPais());
                addressRepository.save(newAddress);
                return new ResponseEntity<>("Created", HttpStatus.CREATED);
            }else {
                newCustomer.set_cnpj(false);
                newCustomer.setNome_completo(csDataReg.getNome_completo());
                newCustomer.setCpf(csDataReg.getCpf());
                newCustomer.setData_nascimento(csDataReg.getData_nascimento());
                newCustomer.setEscolaridade(csDataReg.getEscolaridade());
                newCustomer.setGenero(csDataReg.getGenero());
                newCustomer.setOcupacao(csDataReg.getOcupacao());
                newCustomer.setCreated_by(createdBy);
                newCustomer.setBusiness(csDataReg.getBusiness().replace(" MASTER", ""));
                customerRepository.save(newCustomer);
                newContact.setCustomer(newCustomer);
                newContact.setEmail(csDataReg.getEmail());
                newContact.setTelefone(csDataReg.getTelefone());
                contactsRepository.save(newContact);
                newAddress.setCustomer_id(newCustomer);
                newAddress.setCep(csDataReg.getCep());
                newAddress.setLogradouro(csDataReg.getLogradouro());
                newAddress.setBairro(csDataReg.getBairro());
                newAddress.setCidade(csDataReg.getCidade());
                newAddress.setEstado(csDataReg.getEstado());
                newAddress.setPais(csDataReg.getPais());
                addressRepository.save(newAddress);
                return new ResponseEntity<>("Created", HttpStatus.CREATED);
            }
        }catch (Exception error){
            Map<String, Object> response = new HashMap<>();
            response.put("erro", error.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getall")
    public ResponseEntity getAll(@RequestBody GetDataDTO data) {
        String magicWord = "MASTER";
        List<HashMap<String, Object>> listResponse = new ArrayList<>();
        if ("MASTER".equals(data.getGrupo()) || "RISK".equals(data.getGrupo())) {
            List<Customer> allCustomers = customerRepository.findAll();
            for (Customer customer : allCustomers) {
                HashMap<String, Object> response = new HashMap<>();
                Contacts contact = contactsRepository.findByCustomer(customer);
                System.out.println(contact);
                response.put("customerId", customer.getCustomer_id());
                response.put("nome", customer.getNome_completo());
                response.put("nomeFantasia", customer.getNome_fantasia());
                response.put("razaoSocial", customer.getRazao_social());
                response.put("cnpj", customer.getCnpj());
                response.put("cpf", customer.getCpf());
                response.put("email", contact.getEmail());
                response.put("telefone", contact.getTelefone());
                listResponse.add(response);
            }
            return new ResponseEntity(listResponse, HttpStatus.OK);
        } else if (data.getGrupo().contains(magicWord)) {
            Optional<Users> optionalUser = usersRepository.findById(data.getUser_id());
            Users user = optionalUser.get();
            String grupoPesquisa = data.getGrupo().replace(" MASTER", "");
            List<Customer> allCustomers = customerRepository.findByBusiness(grupoPesquisa);
            for (Customer customer : allCustomers) {
                HashMap<String, Object> response = new HashMap<>();
                Contacts contact = contactsRepository.findByCustomer(customer);
                System.out.println(contact);
                response.put("customerId", customer.getCustomer_id());
                response.put("nome", customer.getNome_completo());
                response.put("nomeFantasia", customer.getNome_fantasia());
                response.put("razaoSocial", customer.getRazao_social());
                response.put("cnpj", customer.getCnpj());
                response.put("cpf", customer.getCpf());
                response.put("email", contact.getEmail());
                response.put("telefone", contact.getTelefone());
                listResponse.add(response);
            }
            return new ResponseEntity(listResponse, HttpStatus.OK);
        } else {
            Optional<Users> optionalUser = usersRepository.findById(data.getUser_id());
            Users user = optionalUser.get();
            List<Customer> allCustomers = customerRepository.findByBusinessAndCreatedBy(data.getGrupo(), user);
            for (Customer customer : allCustomers) {
                HashMap<String, Object> response = new HashMap<>();
                Contacts contact = contactsRepository.findByCustomer(customer);
                System.out.println(contact);
                response.put("customerId", customer.getCustomer_id());
                response.put("nome", customer.getNome_completo());
                response.put("nomeFantasia", customer.getNome_fantasia());
                response.put("razaoSocial", customer.getRazao_social());
                response.put("cnpj", customer.getCnpj());
                response.put("cpf", customer.getCpf());
                response.put("email", contact.getEmail());
                response.put("telefone", contact.getTelefone());
                listResponse.add(response);
            }
            return new ResponseEntity(listResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity getCustomerById(@PathVariable Integer id){
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateCustomer(@RequestBody CustomerUpdateDTO updateData){
        Optional<Customer> optionalCustomer = customerRepository.findById(updateData.getCustomer_id());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if (customer.is_cnpj() == true) {
                if (!customer.getRazao_social().equals(updateData.getRazao_social())){
                    customer.setRazao_social(updateData.getRazao_social());
                }
                if (!customer.getNome_fantasia().equals(updateData.getNome_fantasia())){
                    customer.setNome_fantasia(updateData.getNome_fantasia());
                }
                if (!customer.getCnpj().equals(updateData.getCnpj())){
                    customer.setCnpj(updateData.getCnpj());
                }
                if (!customer.getData_abertura().equals(updateData.getData_abertura())){
                    customer.setData_abertura(updateData.getData_abertura());
                }
                if (!customer.getSegmento().equals(updateData.getSegmento())){
                    customer.setSegmento(updateData.getSegmento());
                }
                customerRepository.save(customer);
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            }else {
                if (!customer.getNome_completo().equals(updateData.getNome_completo())){
                    customer.setNome_completo(updateData.getNome_completo());
                }
                if (!customer.getCpf().equals(updateData.getCpf())){
                    customer.setCpf(updateData.getCpf());
                }
                if (!customer.getData_nascimento().equals(updateData.getData_nascimento())){
                    customer.setData_nascimento(updateData.getData_nascimento());
                }
                if (!customer.getEscolaridade().equals(updateData.getEscolaridade())){
                    customer.setEscolaridade(updateData.getEscolaridade());
                }
                if (!customer.getGenero().equals(updateData.getGenero())){
                    customer.setGenero(updateData.getGenero());
                }
                if (!customer.getOcupacao().equals(updateData.getOcupacao())){
                    customer.setOcupacao(updateData.getOcupacao());
                }
                customerRepository.save(customer);
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
    }
}
