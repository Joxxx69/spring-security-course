package com.spring.security.springsecuritycourse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringsecurityCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityCourseApplication.class, args);

	}
	//@Bean 
	//CommandLineRunner createPasswordCommand(PasswordEncoder passwordEncoder){
	//	return arg->{
	//		System.out.println(passwordEncoder.encode("clave123"));
	//		System.out.println(passwordEncoder.encode("clave234"));
	//		System.out.println(passwordEncoder.encode("clave345"));
	//	};
	//}

}
