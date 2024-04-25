package edu.mfvp.encryptpassword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class EncryptPasswordApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptPasswordApplication.class, args);
    }

}
