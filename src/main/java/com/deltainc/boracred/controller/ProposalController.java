package com.deltainc.boracred.controller;

import com.deltainc.boracred.configuration.AWSConfig;
import com.deltainc.boracred.dto.GetDataDTO;
import com.deltainc.boracred.dto.ProposalRegisterDTO;
import com.deltainc.boracred.dto.ProposalUpdateDTO;
import com.deltainc.boracred.entity.*;
import com.deltainc.boracred.repositories.*;
import com.deltainc.boracred.services.EmailService;
import com.deltainc.boracred.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/api/v1/proposal")
public class ProposalController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AnalyticsRepository analyticsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    SCRRepository scrRepository;

    @Autowired
    AllsDataRepository allsDataRepository;

    @Autowired
    FilesRepository filesRepository;

    @Autowired
    ContactsRepository contactsRepository;

    @Autowired
    LogsRepository logsRepository;

    @Autowired
    SocioPjRepository socioPjRepository;

    @Autowired
    ReferenciaRepository referenciaRepository;

    @Autowired
    public EmailService emailService;

    @GetMapping("/loans/getproposal/info/{id}")
    public ResponseEntity getProposalDetails(@PathVariable Integer id){
        try {
            Map<String, Object> response = new HashMap<>();
            Optional<Proposal> optionalProposal = proposalRepository.findById(id);
            if (optionalProposal.isPresent()){
                Proposal proposal = optionalProposal.get();
                Contacts contact = contactsRepository.findByCustomer(proposal.getCustomer());
                response.put("proposal", proposal);
                response.put("contact", contact);
            }
        if (optionalProposal.isPresent()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Proposal not found", HttpStatus.OK);
        }} catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity registerProposal(@RequestBody ProposalRegisterDTO registerData){
        try {
            Proposal proposal = new Proposal();
            Optional<Users> optionalUser = usersRepository.findById(registerData.getUser_id());
            Users user = optionalUser.get();
            Optional<Customer> optionalCustomer = customerRepository.findById(registerData.getCustomer_id());
            Customer customer = optionalCustomer.get();
            proposal.setCustomer(customer);
            proposal.setValor_desejado(registerData.getValor_desejado());
            proposal.setPrazo(registerData.getPrazo());
            proposal.setStatus("EM ANALISE");
            proposal.setObservacao_cliente(registerData.getObservacao_cliente());
            proposal.setData_abertura(LocalDate.now());
            proposal.setUser(user);
            List<String> emailsTo = new ArrayList<>();
            emailsTo.add("joao.fernandes@deltaux.com.br");
            emailsTo.add("pedro.ricco@deltainvestor.com.br");
            emailsTo.add(user.getEmail());
            proposalRepository.save(proposal);
            for (String to : emailsTo){
                System.out.println(to);
                if (customer.is_cnpj() != true) {
                    try {
                        System.out.println("enviando");
                        emailService.sendEmailNovaProposta(to, customer.getNome_completo(), proposal.getProposalId(), proposal.getValor_desejado(), proposal.getPrazo());
                        System.out.println("email enviado");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    try {
                        System.out.println("enviando");
                        emailService.sendEmailNovaProposta(to, customer.getRazao_social(), proposal.getProposalId(), proposal.getValor_desejado(), proposal.getPrazo());
                        System.out.println("email enviado");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            String action = "Register";
            LocalDateTime dataAcao = LocalDateTime.now();
            String target_type = "proposal";
            Logs log = new Logs();
            log.setAction(action);
            log.setAction_date(dataAcao);
            log.setTarget(proposal.getProposalId());
            log.setUser(user);
            log.setTarget_type(target_type);
            logsRepository.save(log);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }catch(Exception error){
            Map<String, Object> response = new HashMap<>();
            response.put("erro", error.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbyuser/{id}")
    public ResponseEntity getByUser(@PathVariable Integer id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer != null) {
            List<HashMap<String, Object>> listResponse = new ArrayList<>();
            Customer customer = optionalCustomer.get();
            List<Proposal> proposals = proposalRepository.findByCustomer(customer);
            for (Proposal proposal : proposals) {
                HashMap<String, Object> response = new HashMap<>();
                Users user = proposal.getUser();
                response.put("proposalId", proposal.getProposalId());
                response.put("tipo", customer.is_cnpj());
                response.put("username", user.getUsername());
                response.put("business", customer.getBusiness());
                response.put("date", proposal.getData_abertura());
                response.put("razaoSocial", customer.getRazao_social());
                response.put("nomeCompleto", customer.getNome_completo());
                response.put("cpf", customer.getCpf());
                response.put("cnpj", customer.getCnpj());
                response.put("status", proposal.getStatus());
                listResponse.add(response);
            }
            return new ResponseEntity<>(listResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.OK);
        }
    }

    @PostMapping("/getcustomers")
    public ResponseEntity getCustomers(@RequestBody GetDataDTO data) {
        String magicWord = "MASTER";
        List<HashMap<String, Object>> listResponse = new ArrayList<>();
        if ("MASTER".equals(data.getGrupo()) || "RISK".equals(data.getGrupo())) {
            List<Customer> allCustomers = customerRepository.findAll();
            for (Customer customer : allCustomers){
                List<Proposal> listProposal = proposalRepository.findByCustomer(customer);
                for (Proposal proposal : listProposal) {
                    HashMap<String, Object> response = new HashMap<>();
                    response.put("indicador", customer.getCreated_by());
                    response.put("business", customer.getBusiness());
                    response.put("dataCriacao", proposal.getData_abertura());
                    response.put("razaoSocial", customer.getRazao_social());
                    response.put("nomeCompleto", customer.getNome_completo());
                    response.put("cpf", customer.getCpf());
                    response.put("cnpj", customer.getCnpj());
                    response.put("status", proposal.getStatus());
                    response.put("proposalId", proposal.getProposalId());
                    listResponse.add(response);
                }
            }
            return new ResponseEntity<>(listResponse, HttpStatus.OK);
        } else if (data.getGrupo().contains(magicWord)){
            Optional<Users> optionalUser = usersRepository.findById(data.getUser_id());
            Users user = optionalUser.get();
            String grupoPesquisa = data.getGrupo().replace(" MASTER", "");
//            List<Customer> allCustomers = customerRepository.findByBusinessAndCreatedBy(grupoPesquisa, user);
            List<Customer> allCustomers = customerRepository.findByBusiness(grupoPesquisa);
            System.out.println("customers " + allCustomers);
            for (Customer customer : allCustomers){
                HashMap<String, Object> response = new HashMap<>();
                List<Proposal> proposals = proposalRepository.findByCustomer(customer);
                for (Proposal proposal : proposals) {
                    HashMap<String, Object> response2 = new HashMap<>();
                    response2.put("indicador", customer.getCreated_by());
                    response2.put("business", customer.getBusiness());
                    response2.put("dataCriacao", proposal.getData_abertura());
                    response2.put("razaoSocial", customer.getRazao_social());
                    response2.put("nomeCompleto", customer.getNome_completo());
                    response2.put("cpf", customer.getCpf());
                    response2.put("cnpj", customer.getCnpj());
                    response2.put("status", proposal.getStatus());
                    response2.put("proposalId", proposal.getProposalId());
                    listResponse.add(response2);
                }
            } return new ResponseEntity<>(listResponse, HttpStatus.OK);
        } else {
            Optional<Users> optionalUser = usersRepository.findById(data.getUser_id());
            Users user = optionalUser.get();
            List<Customer> allCustomers = customerRepository.findByCreatedBy(user);
            for (Customer customer : allCustomers){
                HashMap<String, Object> response = new HashMap<>();
                List<Proposal> proposals = proposalRepository.findByCustomer(customer);
                for (Proposal proposal : proposals) {
                    HashMap<String, Object> response3 = new HashMap<>();
                    response3.put("indicador", customer.getCreated_by());
                    response3.put("business", customer.getBusiness());
                    response3.put("dataCriacao", proposal.getData_abertura());
                    response3.put("razaoSocial", customer.getRazao_social());
                    response3.put("nomeCompleto", customer.getNome_completo());
                    response3.put("cpf", customer.getCpf());
                    response3.put("cnpj", customer.getCnpj());
                    response3.put("status", proposal.getStatus());
                    response3.put("proposalId", proposal.getProposalId());
                    listResponse.add(response3);
                }
            }
            return new ResponseEntity<>(listResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity getall(){
        return new ResponseEntity<>(proposalRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity getbyid(@PathVariable Integer id){
        Optional<Proposal> optionalProposal = proposalRepository.findById(id);
        Proposal proposal = optionalProposal.get();
        Customer customer = proposal.getCustomer();
        Analytics analytics = analyticsRepository.findByProposal(proposal);
        SCR scr = scrRepository.findByProposal(proposal);
        AllsData allsData = allsDataRepository.findByProposal(proposal);
        System.out.println(proposal.getProposalId());
        List<Files> files = filesRepository.findByProposal(proposal.getProposalId());
        System.out.println(files);
        Contacts contact = contactsRepository.findByCustomer(customer);
        SocioPj socio = socioPjRepository.findByCustomer(customer);
        Referencia referencia = referenciaRepository.findByCustomer(customer);
        HashMap<String, Object> response = new HashMap<>();
        response.put("isCnpj", customer.is_cnpj());
        response.put("proposalId", proposal.getProposalId());
        response.put("customerName", customer.getNome_completo());
        response.put("customerRazaoSocial", customer.getRazao_social());
        response.put("customerRendaMedia", proposal.getRenda_media());
        response.put("cnpj", customer.getCnpj());
        response.put("cpf", customer.getCpf());
        response.put("valorDesejado", proposal.getValor_desejado());
        response.put("taxa", proposal.getTaxa());
        response.put("corban", proposal.getCorban());
        response.put("status", proposal.getStatus());
        response.put("montante", proposal.getMontante());
        response.put("valorLiberado", proposal.getValor_liberado());
        response.put("prazo", proposal.getPrazo());
        response.put("dataAbertura", proposal.getData_abertura());
        response.put("dataPrimeiraParcela", proposal.getData_primeira_parcela());
        response.put("totalJuros", proposal.getTotal_juros());
        response.put("statusContrato", proposal.getStatus_contrato());
        response.put("motivoReprovacao", proposal.getMotivo_reprovacao());
        response.put("observacaoCliente", proposal.getObservacao_cliente());
        response.put("observacaoAnalista", proposal.getObservacao_analista());
        response.put("email", contact.getEmail());
        response.put("telefone", contact.getTelefone());
        response.put("segmento", customer.getSegmento());
        try {
            response.put("nome_sociopj", socio.getNome_socio());
            response.put("cpf_socio", socio.getCpf_socio());
        } catch (Exception e) {
            response.put("nome_sociopj", "Sem dados");
            response.put("cpf_socio", "Sem dados");
            System.out.println(e);
        }
        try {
            response.put("nome_referencia", referencia.getNomeCompleto());
            response.put("email_referencia", referencia.getEmail());
            response.put("cpf_referencia", referencia.getCpf());
            response.put("telefone_referencia", referencia.getTelefone());
        } catch (Exception e) {
            response.put("nome_referencia", "Sem dados");
            response.put("email_referencia", "Sem dados");
            response.put("cpf_referencia", "Sem dados");
            response.put("telefone_referencia", "Sem dados");
            System.out.println(e);
        }

        if (analytics != null) {
            HashMap<String, Object> responseAnalytics = new HashMap<>();
            responseAnalytics.put("num_titulos_protestados", analytics.getNum_titulos_protestados());
            responseAnalytics.put("score", analytics.getScore());
            responseAnalytics.put("num_refins", analytics.getNum_refins());
            responseAnalytics.put("valor_cadins", analytics.getValor_cadins());
            responseAnalytics.put("valor_iss", analytics.getValor_iss());
            responseAnalytics.put("num_processos", analytics.getNum_processos());
            responseAnalytics.put("valor_processos", analytics.getValor_processos());
            responseAnalytics.put("num_uf_processos", analytics.getNum_uf_processos());
            responseAnalytics.put("divida_ativa", analytics.getDivida_ativa());
            responseAnalytics.put("valor_titulos_protestados", analytics.getValor_titulos_protestados());
            responseAnalytics.put("risco", analytics.getRisco());
            responseAnalytics.put("pep", analytics.isPep());
            responseAnalytics.put("num_cheques_devolvidos", analytics.getNum_cheques_devolvidos());
            responseAnalytics.put("valor_cheques_devolvidos", analytics.getValor_cheques_devolvidos());
            responseAnalytics.put("valor_pefins", analytics.getValor_pefins());
            responseAnalytics.put("num_pefins", analytics.getNum_pefins());
            responseAnalytics.put("empresas_nao_informadas", analytics.getEmpresas_nao_informadas());
            response.put("analytics", responseAnalytics);
        } else {
            Analytics newAnalytics = new Analytics();
            newAnalytics.setProposal(proposal);
            analyticsRepository.save(newAnalytics);
            response.put("newAnalyticsId", newAnalytics.getAnalytics_id());
        }
        if (scr != null) {
            HashMap<String, Object> responseScr = new HashMap<>();
            responseScr.put("vencer_valor_total", scr.getVencer_valor_total());
            responseScr.put("vencer_ate_30_dias_vencidos_ate_14_dias", scr.getVencer_ate_30_dias_vencidos_ate_14_dias());
            responseScr.put("vencer_31_60_dias", scr.getVencer_31_60_dias());
            responseScr.put("vencer_61_90_dias", scr.getVencer_61_90_dias());
            responseScr.put("vencer_91_180_dias", scr.getVencer_91_180_dias());
            responseScr.put("vencer_181_360_dias", scr.getVencer_181_360_dias());
            responseScr.put("vencer_acima_360_dias", scr.getVencer_acima_360_dias());
            responseScr.put("vencer_indeterminado", scr.getVencer_indeterminado());
            responseScr.put("vencido_total", scr.getVencido_total());
            responseScr.put("vencido_15_30_dias", scr.getVencido_15_30_dias());
            responseScr.put("vencido_31_60_dias", scr.getVencido_31_60_dias());
            responseScr.put("vencido_61_90_dias", scr.getVencido_61_90_dias());
            responseScr.put("vencido_91_180_dias", scr.getVencido_91_180_dias());
            responseScr.put("vencido_181_360_dias", scr.getVencido_181_360_dias());
            responseScr.put("vencido_acima_360_dias", scr.getVencido_acima_360_dias());
            responseScr.put("prejuizo_total", scr.getPrejuizo_total());
            responseScr.put("prejuizo_ate_12_meses", scr.getPrejuizo_ate_12_meses());
            responseScr.put("prejuizo_acima_12_meses", scr.getPrejuizo_acima_12_meses());
            responseScr.put("coobrigacao_total", scr.getCoobrigacao_total());
            responseScr.put("coobrigacao_assumida", scr.getCoobrigacao_assumida());
            responseScr.put("coobrigacao_prestadas", scr.getCoobrigacao_prestadas());
            responseScr.put("creditos_liberar_total", scr.getCreditos_liberar_total());
            responseScr.put("creditos_liberar_ate_360_dias", scr.getCreditos_liberar_ate_360_dias());
            responseScr.put("creditos_liberar_acima_360_dias", scr.getCreditos_liberar_acima_360_dias());
            responseScr.put("limites_credito_valor_total", scr.getLimites_credito_valor_total());
            responseScr.put("limites_credito_vencimento_ate_360_dias", scr.getLimites_credito_vencimento_ate_360_dias());
            responseScr.put("limites_credito_vencimento_acima_360_dias", scr.getLimites_credito_vencimento_acima_360_dias());
            response.put("scr", responseScr);
        } else {
            SCR newScr = new SCR();
            newScr.setProposal(proposal);
            scrRepository.save(newScr);
            response.put("newScrId", newScr.getScr_id());
        }
        if (allsData != null) {
            HashMap<String, Object> responseAllsData = new HashMap<>();
            responseAllsData.put("num_pendencias_financeiras_alls", allsData.getNum_pendencias_financeiras());
            responseAllsData.put("valor_pendencias_financeiras_alls", allsData.getValor_pendencias_financeiras());
            responseAllsData.put("num_recuperacoes_alls", allsData.getNum_recuperacoes());
            responseAllsData.put("valor_recuperacoes_alls", allsData.getValor_recuperacoes());
            responseAllsData.put("num_cheque_sem_fundo_alls", allsData.getNum_cheque_sem_fundo());
            responseAllsData.put("num_protestos_alls", allsData.getNum_protestos());
            responseAllsData.put("valor_protestos_alls", allsData.getValor_protestos());
            responseAllsData.put("limite_sugerido_alls", allsData.getLimite_sugerido());
            responseAllsData.put("num_restricoes_alls", allsData.getNum_restricoes());
            responseAllsData.put("valor_restricoes", allsData.getValor_restricoes());
            response.put("allsData", responseAllsData);
        } else {
            AllsData newAllsData = new AllsData();
            newAllsData.setProposal(proposal);
            allsDataRepository.save(newAllsData);
            response.put("newAllsDataId", newAllsData.getSearch_id());
        }
        if (files != null) {
            System.out.println("Não é nulll");
            List<HashMap<String, Object>> listFiles = new ArrayList<>();
            for (Files file : files) {
                HashMap<String, Object> responseFile = new HashMap<>();
                responseFile.put("tipoArquivo", file.getTipo_arquivo());
                responseFile.put("url_arquivo", file.getUrl_arquivo());
                responseFile.put("uploaded_in", file.getUploaded_in());
                responseFile.put("file_name", file.getFile_name());
                System.out.println(file.getUrl_arquivo());
                listFiles.add(responseFile);
            }
            response.put("files", listFiles);
        } else {
            System.out.println("é nulll");
            response.put("files", "Sem arquivos");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody ProposalUpdateDTO data){
        System.out.println("Proposal update");
        try {
            System.out.println("Trying to do stuff");
            System.out.println(data.getUser_id());
            Optional<Users> optionalUser = usersRepository.findById(data.getUser_id());
            System.out.println("Consegui o optional");
            Users userLog = optionalUser.get();
            Users analista = optionalUser.get();
            System.out.println(userLog);
            Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal_id());
            if (optionalProposal.isPresent()){
                Proposal proposal = optionalProposal.get();
                // Logs
                try {
                    String action = "Update";
                    LocalDateTime dataAcao = LocalDateTime.now();
                    String newValue = "valor_desejado = " + data.getValor_desejado() + ", taxa = " + data.getTaxa() + ", corban = " + data.getCorban() + ", status = " + data.getStatus() + ", montante = " + data.getMontante() +
                                    ", valor_liberado = " + data.getValor_liberado() + ", prazo = " + data.getPrazo() + ", data_abertura = " + data.getData_abertura() + ", data_primeira_parcela = " + data.getData_primeira_parcela() + ", total_juros = " + data.getTotal_juros() +
                                    ", status_contrato = " + data.getStatus_contrato() + ", motivo_reprovacao = " + data.getMotivo_reprovacao() + ", observacao_cliente = " + data.getObservacao_cliente() + ", observacao_analista = " + data.getObservacao_analista();
                    String oldValue = "valor_desejado = " + proposal.getValor_desejado() + ", taxa = " + proposal.getTaxa() + ", corban = " + proposal.getCorban() + ", status = " + proposal.getStatus() + ", montante = " + proposal.getMontante() +
                                    ", valor_liberado = " + proposal.getValor_liberado() + ", prazo = " + proposal.getPrazo() + ", data_abertura = " + proposal.getData_abertura() + ", data_primeira_parcela = " + proposal.getData_primeira_parcela() + ", total_juros = " + proposal.getTotal_juros() +
                                    ", status_contrato = " + proposal.getStatus_contrato() + ", motivo_reprovacao = " + proposal.getMotivo_reprovacao() + ", observacao_cliente = " + proposal.getObservacao_cliente() + ", observacao_analista = " + proposal.getObservacao_analista();
                    Integer target = proposal.getProposalId();
                    String target_type = "proposal";
                    Logs log = new Logs();
                    log.setUser(userLog);
                    log.setAction(action);
                    log.setAction_date(dataAcao);
                    log.setNew_value(newValue);
                    log.setOld_value(oldValue);
                    log.setTarget(target);
                    log.setTarget_type(target_type);
                    logsRepository.save(log);
                } catch(Exception e){
                    System.out.println("erro " + e);
                }
                // fim Logs
                proposal.setAnalista(analista);
                proposal.setValor_desejado(data.getValor_desejado());
                proposal.setTaxa(data.getTaxa());
                proposal.setCorban(data.getCorban());
                proposal.setStatus(data.getStatus());
                proposal.setMontante(data.getMontante());
                proposal.setValor_liberado(data.getValor_liberado());
                proposal.setPrazo(data.getPrazo());
                proposal.setData_abertura(data.getData_abertura());
                proposal.setData_primeira_parcela(data.getData_primeira_parcela());
                proposal.setTotal_juros(data.getTotal_juros());
                proposal.setStatus_contrato(data.getStatus_contrato());
                proposal.setMotivo_reprovacao(data.getMotivo_reprovacao());
                proposal.setObservacao_cliente(data.getObservacao_cliente());
                proposal.setObservacao_analista(data.getObservacao_analista());
                proposal.setRenda_media(data.getRenda_media());
                System.out.println(proposal.getStatus());
                if ("APROVADO".equals(proposal.getStatus())){
                    System.out.println("aprovado");
                    Customer customer = proposal.getCustomer();
                    Users user = proposal.getUser();
                    List<String> emailsTo = new ArrayList<>();
                    emailsTo.add("joao.fernandes@deltaux.com.br");
                    emailsTo.add("matheus.fernandes@bdidigital.com.br");
                    emailsTo.add("marcello.rodrigues@bdidigital.com.br");
                    emailsTo.add("roberto.domiencio@bdidigital.com.br");
                    emailsTo.add("controladoria@deltaux.com.br");
                    emailsTo.add(user.getEmail());
                    for (String to : emailsTo) {
                        System.out.println(to);
                        if (customer.is_cnpj() != true) {
                            try {
                                System.out.println("enviando");
                                emailService.sendEmailAprovado(to, customer.getNome_completo(), proposal.getProposalId(), proposal.getTaxa(), proposal.getValor_liberado(), proposal.getPrazo());
                                System.out.println("email enviado");
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } else {
                            try {
                                System.out.println("enviando");
                                emailService.sendEmailAprovado(to, customer.getRazao_social(), proposal.getProposalId(), proposal.getTaxa(), proposal.getValor_liberado(), proposal.getPrazo());
                                System.out.println("email enviado");
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }
                } else if ("PRE APROVADO".equals(proposal.getStatus())){
                    System.out.println("pre aprovado");
                    Customer customer = proposal.getCustomer();
                    Users user = proposal.getUser();
                    List<String> emailsTo = new ArrayList<>();
                    emailsTo.add("joao.fernandes@deltaux.com.br");
                    emailsTo.add("matheus.fernandes@bdidigital.com.br");
                    emailsTo.add("pedro.ricco@deltainvestor.com.br");
                    for (String to : emailsTo) {
                        System.out.println(to);
                        if (customer.is_cnpj() != true) {
                            try {
                                System.out.println("enviando");
                                emailService.sendEmailPreAprovado(to, customer.getNome_completo(), proposal.getProposalId(), proposal.getTaxa(), proposal.getValor_liberado(), proposal.getPrazo());
                                System.out.println("email enviado");
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } else {
                            try {
                                System.out.println("enviando");
                                emailService.sendEmailPreAprovado(to, customer.getRazao_social(), proposal.getProposalId(), proposal.getTaxa(), proposal.getValor_liberado(), proposal.getPrazo());
                                System.out.println("email enviado");
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }
                }
                System.out.println(data.getRenda_media());
                System.out.println(proposal.getRenda_media());
                proposalRepository.save(proposal);
            }
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
