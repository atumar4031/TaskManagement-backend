package com.oasis.TaskManagementApplication;

import com.oasis.TaskManagementApplication.entity.Role;
import com.oasis.TaskManagementApplication.entity.User;
import com.oasis.TaskManagementApplication.repo.RoleRepo;
import com.oasis.TaskManagementApplication.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.oasis.TaskManagementApplication.util.Constant.ADMIN_ROLE;
import static com.oasis.TaskManagementApplication.util.Constant.USER_ROLE;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableMethodSecurity
public class TaskManagementApplication {

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}

	@PostConstruct
	public void seedRolesAndUsers() {
		// Create roles if not exist
		Role adminRole = roleRepo.findByName(ADMIN_ROLE).orElse(null);
		if (adminRole == null) {
			adminRole = new Role();
			adminRole.setName(ADMIN_ROLE);
			roleRepo.save(adminRole);
		}

		Role userRole = roleRepo.findByName(USER_ROLE).orElse(null);
		if (userRole == null) {
			userRole = new Role();
			userRole.setName(USER_ROLE);
			roleRepo.save(userRole);
		}

		// Create admin user if not exist
		User user = userRepo.findByUsername("admin.admin@gmail.com").orElse(null);
		if (user == null) {
			user = new User();
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setUsername("admin.admin@gmail.com");
			user.setEmail("admin.admin@gmail.com");
			user.setRoles(List.of(adminRole, userRole));
			user.setEnabled(true);
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			user.setAccountNonLocked(true);
			user.setPassword(passwordEncoder.encode("Pass123"));
			userRepo.save(user);
		}
	}

}
