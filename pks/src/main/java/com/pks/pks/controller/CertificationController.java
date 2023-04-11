package com.pks.pks.controller;

import com.pks.pks.model.dto.SubjectDTO;
import com.pks.pks.service.CertificateUtil;
import com.pks.pks.service.KeyStoreReader;
import com.pks.pks.service.KeyStoreWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;

@RestController
@RequestMapping("api/certification")
public class CertificationController {

    private static CertificateUtil certUtil;
    private static KeyStoreReader keyStoreReader;
    private static KeyStoreWriter keyStoreWriter;
    private static ApplicationContext context;
    private static SubjectDTO issuerDTO;
    @PostMapping(path = "/issueCertificate")
    public ResponseEntity<?> issueCertificate(@RequestBody SubjectDTO subjectDTO) {

        issuerDTO = new SubjectDTO();
        issuerDTO.setNameAndSurname("Tehnicka sluzba");
        issuerDTO.setSurname("sluzba");
        issuerDTO.setName("Tehnicka");
        issuerDTO.setOrganization("FTN");
        issuerDTO.setOrganizationName("Fakultet Teh Nauka");
        issuerDTO.setCode("RS");
        issuerDTO.setEmail("sluzbaftn@ftn.com");
        issuerDTO.setId("1");

        keyStoreReader = (KeyStoreReader) context.getBean("keyStoreReader");
        keyStoreWriter = (KeyStoreWriter) context.getBean("keyStoreWriter");
        certUtil = (CertificateUtil) context.getBean("certificateUtil");

        com.pks.pks.model.Certificate certificate = certUtil.getCertificate(subjectDTO, issuerDTO);
        System.out.println(certificate.getX509Certificate());

        keyStoreWriter.loadKeyStore("src/main/resources/static/example.jks",  "password".toCharArray());
        PrivateKey pk = certificate.getIssuer().getPrivateKey();
        keyStoreWriter.write("cert1", pk, "password".toCharArray(), certificate.getX509Certificate());
        keyStoreWriter.saveKeyStore("src/main/resources/static/example.jks",  "password".toCharArray());

        return ResponseEntity.ok("Generated");
    }
}
