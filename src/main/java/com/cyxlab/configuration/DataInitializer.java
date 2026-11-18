package com.cyxlab.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.cyxlab.model.DBUser;
import com.cyxlab.repository.DBUserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(DBUserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

                DBUser user = new DBUser();
                user.setUsername("user");
                user.setPassword(encoder.encode("user")); // S'encode proprement avec {bcrypt}...
                user.setRole("USER");
                userRepository.save(user);

                DBUser admin = new DBUser();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin")); // S'encode proprement avec {bcrypt}...
                admin.setRole("ADMIN");
                userRepository.save(admin);

                System.out.println(">>> Utilisateurs 'user' et 'admin' encodés et insérés avec succès !");
            }
        };
    }
}