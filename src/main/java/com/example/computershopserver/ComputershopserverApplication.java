package com.example.computershopserver;

import com.example.computershopserver.entity.Role;
import com.example.computershopserver.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ComputershopserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComputershopserverApplication.class, args);
	}
	@Bean
	CommandLineRunner run(RoleRepository roleRepository){
		return args -> {
			Role adminRole = roleRepository.save(new Role(1l,"ROLE_ADMIN"));
			roleRepository.save(new Role(2l,"ROLE_USER"));

//			Set<Role> roles = new HashSet<>();
//			roles.add(adminRole);
//
//			UserApp admin = new UserApp(1,"az@gmail.com" , passwordEncoder.encode("password"), roles);
//
//			userRepository.save(admin);
		};
		//java -jar chatapp-0.0.1-SNAPSHOT.jar
	}
}
