package io.awiya.hopitalh2;

import io.awiya.hopitalh2.entity.Patient;
import io.awiya.hopitalh2.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class Hopitalh2Application {

    public static void main(String[] args) {
        SpringApplication.run(Hopitalh2Application.class, args);
    }

    @Bean
    CommandLineRunner start(PatientRepository patientRepository) {
        return args -> {
            patientRepository.saveAll(
                    List.of(
                            Patient.builder().name("AYOUB").dob(LocalDate.now()).score(242).sick(true).build(),
                            Patient.builder().name("JAD").dob(LocalDate.now()).score(6789).sick(true).build(),
                            Patient.builder().name("JUDIA").dob(LocalDate.now()).score(76).sick(false).build(),
                            Patient.builder().name("FADZA").dob(LocalDate.now()).score(23234).sick(true).build()

                    ));
            patientRepository.findAll().forEach(System.out::println);
        };
    }

}
