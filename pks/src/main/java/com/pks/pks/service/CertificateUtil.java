package com.pks.pks.service;

import com.pks.pks.model.Issuer;
import com.pks.pks.model.Subject;
import com.pks.pks.model.dto.SubjectDTO;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CertificateUtil {

    public Subject generateSubject(SubjectDTO subjectDTO) {
        KeyPair keyPairSubject = generateKeyPair();

        //klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, subjectDTO.getNameAndSurname());
        builder.addRDN(BCStyle.SURNAME, subjectDTO.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, subjectDTO.getName());
        builder.addRDN(BCStyle.O, subjectDTO.getOrganization());
        builder.addRDN(BCStyle.OU, subjectDTO.getOrganizationName());
        builder.addRDN(BCStyle.C, subjectDTO.getCode());
        builder.addRDN(BCStyle.E, subjectDTO.getEmail());
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, subjectDTO.getId());

        return new Subject(keyPairSubject.getPublic(), builder.build());
    }

    public Issuer generateIssuer(SubjectDTO issuerDTO) {
        KeyPair kp = generateKeyPair();
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, issuerDTO.getNameAndSurname());
        builder.addRDN(BCStyle.SURNAME, issuerDTO.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, issuerDTO.getName());
        builder.addRDN(BCStyle.O, issuerDTO.getOrganization());
        builder.addRDN(BCStyle.OU, issuerDTO.getOrganizationName());
        builder.addRDN(BCStyle.C, issuerDTO.getCode());
        builder.addRDN(BCStyle.E, issuerDTO.getEmail());
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, issuerDTO.getId());

        //Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
        // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
        // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
        return new Issuer(kp.getPrivate(), kp.getPublic(), builder.build());
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public com.pks.pks.model.Certificate getCertificate(SubjectDTO subjectDTO, SubjectDTO issuerDTO) {

        try {
            Issuer issuer = generateIssuer(issuerDTO);
            Subject subject = generateSubject(subjectDTO);

            //Datumi od kad do kad vazi sertifikat
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse("2023-03-25");
            Date endDate = sdf.parse("2028-03-25");

            X509Certificate certificate = CertificateGenerator.generateCertificate(subject,
                    issuer, startDate, endDate, "1");

            return new com.pks.pks.model.Certificate(subject, issuer,
                    "1", startDate, endDate, certificate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


}
