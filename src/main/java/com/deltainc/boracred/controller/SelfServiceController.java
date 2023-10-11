package com.deltainc.boracred.controller;

import com.amazonaws.Response;
import com.deltainc.boracred.dto.GeoLocation;
import com.deltainc.boracred.dto.IndicacaoPfDTO;
import com.deltainc.boracred.dto.IndicacaoPjDTO;
import com.deltainc.boracred.entity.*;
import com.deltainc.boracred.repositories.*;
import com.deltainc.boracred.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    GruposRepository gruposRepository;

    @Autowired
    public EmailService emailService;

    @PostMapping("/formwebindicacaopf")
    public ResponseEntity formWebPf(@RequestBody IndicacaoPfDTO data, HttpServletRequest request){
        try{
            String ip = request.getRemoteAddr();
            String uri = "http://ip-api.com/json/" + ip;
            RestTemplate restTemplate = new RestTemplate();
            GeoLocation geoLocation = restTemplate.getForObject(uri, GeoLocation.class);
            System.out.println(geoLocation);
            String finalLocation = geoLocation.getLat() + " " + geoLocation.getLon();
            Optional<Users> optionalUser = usersRepository.findById(data.getCodigo_indicador());
            Users user = optionalUser.get();
            Grupos grupo = user.getGrupo_id();
            Customer customer = new Customer();
            Contacts contact = new Contacts();
            Proposal proposal = new Proposal();
            AceiteScr aceiteScr = new AceiteScr();
            customer.setNome_completo(data.getNome());
            customer.setCpf(data.getCpf());
            customer.setOcupacao(data.getProfissao());
            customer.setCreated_by(user);
            customer.setBusiness(grupo.getNome_grupo());
            customerRepository.save(customer);
            contact.setCustomer(customer);
            contact.setTelefone(data.getTelefone());
            contact.setEmail(data.getEmail());
            contactsRepository.save(contact);
            proposal.setCustomer(customer);
            proposal.setRenda_media(data.getRendaMedia());
            proposal.setValor_desejado(data.getValorDesejado());
            proposal.setPrazo(data.getPrazo());
            proposal.setUser(user);
            proposal.setStatus("EM ANALISE");
            proposalRepository.save(proposal);
            aceiteScr.setProposal_id(proposal);
            aceiteScr.setDispositivo("Navegador Web");
            aceiteScr.setData_hora(LocalDateTime.now());
            aceiteScr.setGeolocalizacao(finalLocation);
            aceiteScr.setIp_publico_usuario(ip);
            aceiteScrRepository.save(aceiteScr);
            List<String> emailsTo = new ArrayList<>();
            emailsTo.add("joao.fernandes@deltaux.com.br");
            emailsTo.add("pedro.ricco@deltainvestor.com.br");
            emailsTo.add("roberto.domiencio@bdidigital.com.br");
            emailsTo.add("marcello.rodrigues@bdidigital.com.br");
            emailsTo.add(user.getEmail());
            for (String to : emailsTo){
                System.out.println(to);
                try {
                    System.out.println("enviando");
                    emailService.sendEmailNovaProposta(to, customer.getNome_completo(), proposal.getProposalId(), proposal.getValor_desejado(), proposal.getPrazo());
                    System.out.println("email enviado");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            return new ResponseEntity("Created", HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/formwebindicacaopj")
    public ResponseEntity formWebPj(@RequestBody IndicacaoPjDTO data, HttpServletRequest request){
        try{
            String ip = request.getRemoteAddr();
            String uri = "http://ip-api.com/json/" + ip;
            RestTemplate restTemplate = new RestTemplate();
            GeoLocation geoLocation = restTemplate.getForObject(uri, GeoLocation.class);
            System.out.println(geoLocation);
            String finalLocation = geoLocation.getLat() + " " + geoLocation.getLon();
            Optional<Users> optionalUser = usersRepository.findById(data.getCodigo_indicador());
            Users user = optionalUser.get();
            Grupos grupo = user.getGrupo_id();
            Customer customer = new Customer();
            Contacts contact = new Contacts();
            Proposal proposal = new Proposal();
            SocioPj socioPj = new SocioPj();
            AceiteScr aceiteScr = new AceiteScr();
            customer.set_cnpj(true);
            customer.setCnpj(data.getCnpj());
            customer.setRazao_social(data.getRazaoSocial());
            customer.setNome_fantasia(data.getNomeFantasia());
            customer.setSegmento(data.getSegmento());
            customer.setCreated_by(user);
            customer.setBusiness(grupo.getNome_grupo());
            customer.setData_abertura(data.getData_abertura());
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
            proposal.setUser(user);
            proposal.setStatus("EM ANALISE");
            proposalRepository.save(proposal);
            aceiteScr.setProposal_id(proposal);
            aceiteScr.setDispositivo("Navegador Web");
            aceiteScr.setData_hora(LocalDateTime.now());
            aceiteScr.setIp_publico_usuario(ip);
            aceiteScr.setGeolocalizacao(finalLocation);
            aceiteScrRepository.save(aceiteScr);
            socioPj.setCustomer(customer);
            socioPj.setNome_socio(data.getNomeSocio());
            socioPj.setCpf_socio(data.getCpfSocio());
            socioPjRepository.save(socioPj);
            List<String> emailsTo = new ArrayList<>();
            emailsTo.add("joao.fernandes@deltaux.com.br");
            emailsTo.add("pedro.ricco@deltainvestor.com.br");
            emailsTo.add("roberto.domiencio@bdidigital.com.br");
            emailsTo.add("marcello.rodrigues@bdidigital.com.br");
            emailsTo.add(user.getEmail());
            for (String to : emailsTo){
                System.out.println(to);
                try {
                    System.out.println("enviando");
                    emailService.sendEmailNovaProposta(to, customer.getRazao_social(), proposal.getProposalId(), proposal.getValor_desejado(), proposal.getPrazo());
                    System.out.println("email enviado");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            return new ResponseEntity("\"Created\"", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
