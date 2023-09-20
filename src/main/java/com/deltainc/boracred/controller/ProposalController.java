package com.deltainc.boracred.controller;

import com.deltainc.boracred.configuration.AWSConfig;
import com.deltainc.boracred.dto.GetDataDTO;
import com.deltainc.boracred.dto.ProposalRegisterDTO;
import com.deltainc.boracred.dto.ProposalUpdateDTO;
import com.deltainc.boracred.entity.*;
import com.deltainc.boracred.repositories.*;
import com.deltainc.boracred.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
            proposal.setStatus("Em análise");
            proposal.setObservacao_cliente(registerData.getObservacao_cliente());
            proposal.setData_abertura(LocalDate.now());
            proposal.setUser(user);
            proposalRepository.save(proposal);
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
            List<Customer> allCustomers = customerRepository.findByBusinessAndCreatedBy(grupoPesquisa, user);
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
                    listResponse.add(response);
                }
            }
        }
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
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
        List<Files> files = filesRepository.findByProposal(proposal);
        HashMap<String, Object> response = new HashMap<>();
        response.put("isCnpj", customer.is_cnpj());
        response.put("proposalId", proposal.getProposalId());
        response.put("customerName", customer.getNome_completo());
        response.put("customerRazaoSocial", customer.getRazao_social());
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
            List<HashMap<String, Object>> listFiles = new ArrayList<>();
            for (Files file : files) {
                HashMap<String, Object> responseFile = new HashMap<>();
                responseFile.put("tipoArquivo", file.getTipo_arquivo());
                responseFile.put("url_arquivo", file.getUrl_arquivo());
                responseFile.put("uploaded_in", file.getUploaded_in());
                responseFile.put("file_name", file.getFile_name());
                listFiles.add(responseFile);
            }
            response.put("files", listFiles);
        } else {
            response.put("files", "Sem arquivos");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody ProposalUpdateDTO data){
        Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal_id());
        System.out.println(data);
//        if (optionalProposal.isPresent()){
//            Proposal proposal = optionalProposal.get();
//            if (proposal.getValor_desejado() != data.getValor_desejado()){
//                proposal.setValor_desejado(data.getValor_desejado());
//            }
//            if (proposal.getTaxa() != data.getTaxa()){
//                proposal.setTaxa(data.getTaxa());
//            }
//            if (proposal.getCorban() != data.getCorban()){
//                proposal.setCorban(data.getCorban());
//            }
//            if (!data.getStatus().equals(proposal.getStatus())) {
//                proposal.setStatus(data.getStatus());
//            }
//            if (proposal.getMontante() != data.getMontante()){
//                proposal.setMontante(data.getMontante());
//            }
//            if (proposal.getValor_liberado() != data.getMontante()){
//                proposal.setValor_liberado(data.getValor_liberado());
//            }
//            if (proposal.getPrazo() != data.getPrazo()){
//                proposal.setPrazo(data.getPrazo());
//            }
//            if (!proposal.getData_abertura().equals(data.getData_abertura())){
//                proposal.setData_abertura(data.getData_abertura());
//            }
//            if (!proposal.getData_primeira_parcela().equals(data.getData_primeira_parcela())){
//                proposal.setData_primeira_parcela(data.getData_primeira_parcela());
//            }
//            if (proposal.getTotal_juros() != data.getTotal_juros()){
//                proposal.setTotal_juros(data.getTotal_juros());
//            }
//            if (!proposal.getStatus_contrato().equals(data.getStatus_contrato())){
//                proposal.setStatus_contrato(data.getStatus_contrato());
//            }
//            if (!proposal.getMotivo_reprovacao().equals(data.getMotivo_reprovacao())){
//                proposal.setMotivo_reprovacao(data.getMotivo_reprovacao());
//            }
//            if (!proposal.getObservacao_cliente().equals(data.getObservacao_cliente())){
//                proposal.setObservacao_cliente(data.getObservacao_cliente());
//            }
//            if (!proposal.getObservacao_analista().equals(data.getObservacao_analista())){
//                proposal.setObservacao_analista(data.getObservacao_analista());
//            }
//            proposalRepository.save(proposal);
//        }
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

}
