package com.hcm.tms;

import com.hcm.tms.entity.City;
import com.hcm.tms.entity.Role;
import com.hcm.tms.entity.User;
import com.hcm.tms.repository.CityRepository;
import com.hcm.tms.repository.RoleRepository;
import com.hcm.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;


@SpringBootApplication
public class TmsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TmsApplication.class, args);
	}


	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CityRepository cityRepository;


	@Override
	public void run(String... args) throws Exception {

		if(cityRepository.findByCityName("HO CHI MINH") == null) {
			cityRepository.save(new City("1","HO CHI MINH"));
		}

		if(cityRepository.findByCityName("DA NANG") == null) {
			cityRepository.save(new City("2","DA NANG"));
		}

		if(cityRepository.findByCityName("HA NOI") == null) {
			cityRepository.save(new City("3","HA NOI"));
		}


		if(roleRepository.findByRoleName("ADMIN") == null) {
			roleRepository.save(new Role("ADMIN"));
		}

		if(roleRepository.findByRoleName("TRAINER") == null) {
			roleRepository.save(new Role("TRAINER"));
		}

		if(roleRepository.findByRoleName("TRAINEE") == null) {
			roleRepository.save(new Role("TRAINEE"));
		}

		if(!userRepository.findByUsername("admin").isPresent()) {
			User admin = new User();
			admin.setUserId(UUID.randomUUID().toString());
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("123456"));
			admin.setFirstname("admin");
			admin.setLastname("admin");
			admin.setEmail("admin@blabla.com");
			admin.setEnable(true);
			admin.setAccountNonExpired(true);
			admin.setCredentialsNonExpired(true);
			admin.setAccountNonLocked(true);
			admin.setCity(cityRepository.findByCityId("1"));
			admin.setTelephone("01234567890");

			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByRoleName("ADMIN"));
			admin.setRoleList(roles);

			userRepository.save(admin);
		}

		/*if(!userRepository.findByUsername("trainer").isPresent()) {
			User trainer = new User();
			trainer.setUserId(UUID.randomUUID().toString());
			trainer.setUsername("trainer");
			trainer.setPassword(passwordEncoder.encode("123456"));
			trainer.setFirstname("trainer");
			trainer.setLastname("trainer");
			trainer.setEmail("trainer@blabla.com");
			trainer.setEnable(true);
			trainer.setAccountNonExpired(true);
			trainer.setCredentialsNonExpired(true);
			trainer.setAccountNonLocked(true);

			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByRoleName("TRAINER"));
			trainer.setRoleList(roles);

			userRepository.save(trainer);
		}

		if(!userRepository.findByUsername("trainee").isPresent()) {
			User trainee = new User();
			trainee.setUserId(UUID.randomUUID().toString());
			trainee.setUsername("trainee");
			trainee.setPassword(passwordEncoder.encode("123456"));
			trainee.setFirstname("trainee");
			trainee.setLastname("trainee");
			trainee.setEmail("trainee@blabla.com");
			trainee.setEnable(true);
			trainee.setAccountNonExpired(true);
			trainee.setCredentialsNonExpired(true);
			trainee.setAccountNonLocked(true);

			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByRoleName("TRAINEE"));
			trainee.setRoleList(roles);

			userRepository.save(trainee);
		}*/

	}

}



