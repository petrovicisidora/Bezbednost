package com.pks.pks;

import com.pks.pks.model.Subject;
import com.pks.pks.model.dto.SubjectDTO;
import com.pks.pks.service.CertificateUtil;
import com.pks.pks.service.KeyStoreReader;
import com.pks.pks.service.KeyStoreWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.security.PrivateKey;
import java.security.cert.Certificate;

@SpringBootApplication
@EnableJpaRepositories
public class PksApplication {


	private static CertificateUtil certUtil;
	private static KeyStoreReader keyStoreReader;
	private static KeyStoreWriter keyStoreWriter;
	private static ApplicationContext context;

	private static SubjectDTO subjectDTO;
	private static SubjectDTO issuerDTO;

	public static void main(String[] args) {

		subjectDTO = new SubjectDTO();
		subjectDTO.setNameAndSurname("Dragan Miskic");
		subjectDTO.setSurname("Miskic");
		subjectDTO.setName("Dragan");
		subjectDTO.setOrganization("FTN");
		subjectDTO.setOrganizationName("Fakultet Teh Nauka");
		subjectDTO.setCode("RS");
		subjectDTO.setEmail("dragan123@ftn.com");
		subjectDTO.setId("123");

		issuerDTO = new SubjectDTO();
		issuerDTO.setNameAndSurname("Tehnicka sluzba");
		issuerDTO.setSurname("sluzba");
		issuerDTO.setName("Tehnicka");
		issuerDTO.setOrganization("FTN");
		issuerDTO.setOrganizationName("Fakultet Teh Nauka");
		issuerDTO.setCode("RS");
		issuerDTO.setEmail("sluzbaftn@ftn.com");
		issuerDTO.setId("1");


		context = SpringApplication.run(PksApplication.class, args);

		keyStoreReader = (KeyStoreReader) context.getBean("keyStoreReader");
		keyStoreWriter = (KeyStoreWriter) context.getBean("keyStoreWriter");
		certUtil = (CertificateUtil) context.getBean("certificateUtil");

		com.pks.pks.model.Certificate certificate = certUtil.getCertificate(subjectDTO, issuerDTO);
		System.out.println(certificate.getX509Certificate());

		// Inicijalizacija fajla za cuvanje sertifikata
		keyStoreWriter.loadKeyStore("src/main/resources/static/example.jks",  "password".toCharArray());
		PrivateKey pk = certificate.getIssuer().getPrivateKey();
		keyStoreWriter.write("cert1", pk, "password".toCharArray(), certificate.getX509Certificate());
		keyStoreWriter.saveKeyStore("src/main/resources/static/example.jks",  "password".toCharArray());

		Certificate loadedCertificate = keyStoreReader.readCertificate("src/main/resources/static/example.jks", "password", "cert1");
	}

}
