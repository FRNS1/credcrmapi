package com.deltainc.boracred.services;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.deltainc.boracred.htmltemplates.EmailTemplates;

@Service
public class EmailService {

    public void sendEmailAprovado(String to, String nomeCliente, Integer idProposta, Float taxa, Float valorDesejado, Integer prazo) throws Exception{
        String subject = String.format("O cliente %s, aceitou a proposta %d", nomeCliente, idProposta);
        String htmlContent = EmailTemplates.templateAprovado;
        htmlContent = htmlContent.replace("${nomeCliente}", nomeCliente);
        htmlContent = htmlContent.replace("${idProposta}", idProposta.toString());
        htmlContent = htmlContent.replace("${taxa}", taxa.toString());
        htmlContent = htmlContent.replace("${valorLiberado}", valorDesejado.toString());
        htmlContent = htmlContent.replace("${prazo}", prazo.toString());
        sendHtmlEmail(to, subject, htmlContent);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws Exception {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtps.uhserver.com");
        email.setSmtpPort(587);
        email.setAuthentication("relacionamento@bdidigital.com.br", "Maxter10@");
        email.setFrom("relacionamento@bdidigital.com.br");
        email.addTo(to);
        email.setSubject(subject);
        email.setHtmlMsg(htmlContent);
        email.setCharset("UTF-8");
        email.send();
    }

}
