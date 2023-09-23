package com.deltainc.boracred.controller;

import com.amazonaws.Response;
import com.deltainc.boracred.dto.GeoLocation;
import com.deltainc.boracred.dto.IndicacaoPfDTO;
import com.deltainc.boracred.dto.IndicacaoPjDTO;
import com.deltainc.boracred.entity.*;
import com.deltainc.boracred.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Controller
@RequestMapping("api/v1/business")
public class SelfServiceController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ContactsRepository contactsRepository;

    @Autowired
    SocioPjRepository socioPjRepository;

    @Autowired
    AceiteScrRepository aceiteScrRepository;

    @Autowired
    ProposalRepository proposalRepository;

    @PostMapping("/formwebindicacaopf")
    public ResponseEntity formWebPf(IndicacaoPfDTO data, HttpServletRequest request){
        try{
            String ip = request.getRemoteAddr();
            String uri = "http://ip-api.com/json/" + ip;
            RestTemplate restTemplate = new RestTemplate();
            GeoLocation geoLocation = restTemplate.getForObject(uri, GeoLocation.class);
            System.out.println(geoLocation);
            System.out.println(data.getRendaMedia());
            String finalLocation = geoLocation.getLatitude() + " " + geoLocation.getLongitude();
            Customer customer = new Customer();
            Contacts contact = new Contacts();
            Proposal proposal = new Proposal();
            AceiteScr aceiteScr = new AceiteScr();
            customer.setNome_completo(data.getNome());
            customer.setCpf(data.getCpf());
            customer.setOcupacao(data.getProfissao());
            customerRepository.save(customer);
            contact.setCustomer(customer);
            contact.setTelefone(data.getTelefone());
            contact.setEmail(data.getEmail());
            contactsRepository.save(contact);
            proposal.setCustomer(customer);
            proposal.setRenda_media(data.getRendaMedia());
            proposal.setValor_desejado(data.getValorDesejado());
            proposal.setPrazo(data.getPrazo());
            proposalRepository.save(proposal);
            aceiteScr.setProposal_id(proposal);
            aceiteScr.setDispositivo("Navegador Web");
            aceiteScr.setData_hora(LocalDateTime.now());
            aceiteScr.setGeolocalizacao(finalLocation);
            aceiteScr.setIp_publico_usuario(ip);
            aceiteScrRepository.save(aceiteScr);
            return new ResponseEntity("Created", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/formwebindicacaopj")
    public ResponseEntity formWebPj(IndicacaoPjDTO data, HttpServletRequest request){
        try{
            Customer customer = new Customer();
            Contacts contact = new Contacts();
            Proposal proposal = new Proposal();
            SocioPj socioPj = new SocioPj();
            AceiteScr aceiteScr = new AceiteScr();
            customer.setCnpj(data.getCnpj());
            customer.setRazao_social(data.getRazaoSocial());
            customer.setNome_fantasia(data.getNomeFantasia());
            customer.setSegmento(data.getSegmento());
            customerRepository.save(customer);
            socioPj.setCustomer(customer);
            socioPj.setCpf_socio(data.getCpfSocio());
            socioPj.setNome_socio(data.getNomeSocio());
            socioPjRepository.save(socioPj);
            contact.setCustomer(customer);
            contact.setTelefone(data.getTelefone());
            contact.setEmail(data.getEmail());
            contactsRepository.save(contact);
            proposal.setCustomer(customer);
            proposal.setRenda_media(data.getReceitaMedia());
            proposal.setValor_desejado(data.getValorDesejado());
            proposal.setPrazo(data.getPrazo());
            proposalRepository.save(proposal);
            aceiteScr.setProposal_id(proposal);
            aceiteScr.setDispositivo("Navegador Web");
            aceiteScr.setData_hora(LocalDateTime.now());
            aceiteScr.setIp_publico_usuario(request.getRemoteAddr());
            aceiteScrRepository.save(aceiteScr);
            return new ResponseEntity("\"Created\"", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
