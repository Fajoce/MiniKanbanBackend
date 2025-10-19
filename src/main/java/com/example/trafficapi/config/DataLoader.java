package com.example.trafficapi.config;

import com.example.trafficapi.model.Infraction;
import com.example.trafficapi.model.InfractionStatus;
import com.example.trafficapi.model.InfractionTypes;
import com.example.trafficapi.model.User;
import com.example.trafficapi.repository.InfractionRepository;
import com.example.trafficapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            InfractionRepository infractionRepository
    ) {
        return args -> {
            // --- Crear usuario admin ---
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(new BCryptPasswordEncoder().encode("admin123"));
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Usuario admin creado con contraseña 'admin123'");
            }

            // --- Crear varias infracciones ---
            if (infractionRepository.count() == 0) {
                Infraction inf1 = new Infraction();
                inf1.setDriverName("Juan Perez");
                inf1.setDriverId("123456789");
                inf1.setType(InfractionTypes.TYPE_A);
                inf1.setAmount(100.0);
                inf1.setStatus(InfractionStatus.PENDING);
                inf1.setOfficerName("Oficial Gómez");
                inf1.setDueDate(LocalDateTime.now().plusDays(15));
                inf1.setNotes("Exceso de velocidad en zona escolar");

                Infraction inf2 = new Infraction();
                inf2.setDriverName("Maria Lopez");
                inf2.setDriverId("987654321");
                inf2.setType(InfractionTypes.TYPE_B);
                inf2.setAmount(150.0);
                inf2.setStatus(InfractionStatus.PENDING);
                inf2.setOfficerName("Oficial Martínez");
                inf2.setDueDate(LocalDateTime.now().plusDays(20));
                inf2.setNotes("Estacionamiento indebido");

                Infraction inf3 = new Infraction();
                inf3.setDriverName("Carlos Sánchez");
                inf3.setDriverId("456789123");
                inf3.setType(InfractionTypes.TYPE_C);
                inf3.setAmount(200.0);
                inf3.setStatus(InfractionStatus.PENDING);
                inf3.setOfficerName("Oficial Ruiz");
                inf3.setDueDate(LocalDateTime.now().plusDays(10));
                inf3.setNotes("Conducción bajo influencia de alcohol");

                Infraction inf4 = new Infraction();
                inf4.setDriverName("Alejandro Gonzalez");
                inf4.setDriverId("763218989");
                inf4.setType(InfractionTypes.TYPE_A);
                inf4.setAmount(200.0);
                inf4.setStatus(InfractionStatus.PENDING);
                inf4.setOfficerName("Oficial Ruiz");
                inf4.setDueDate(LocalDateTime.now().plusDays(10));
                inf4.setNotes("Parqueo indebido en zona de discapacitados");

                Infraction inf5 = new Infraction();
                inf5.setDriverName("Edgar Morillo");
                inf5.setDriverId("26383993");
                inf5.setType(InfractionTypes.TYPE_E);
                inf5.setAmount(200.0);
                inf5.setStatus(InfractionStatus.PENDING);
                inf5.setOfficerName("Oficial Pertuz");
                inf5.setDueDate(LocalDateTime.now().plusDays(30));
                inf5.setNotes("Atropello peaton");

                infractionRepository.save(inf1);
                infractionRepository.save(inf2);
                infractionRepository.save(inf3);
                infractionRepository.save(inf4);
                infractionRepository.save(inf5);

                System.out.println("Infracciones de prueba creadas!");
            }
        };
    }
}