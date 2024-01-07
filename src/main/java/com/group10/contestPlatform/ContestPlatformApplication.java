package com.group10.contestPlatform;

import java.util.List;

import com.group10.contestPlatform.entities.Setting;
import com.group10.contestPlatform.entities.SettingCategory;
import com.group10.contestPlatform.repositories.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.entities.User;
import com.group10.contestPlatform.repositories.RoleRepository;
import com.group10.contestPlatform.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication()
@RequiredArgsConstructor
public class ContestPlatformApplication {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final SettingRepository settingRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ContestPlatformApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			try {
//				Role adminRole = new Role();
//				adminRole.setName("ADMIN");
//				Role userRole = new Role();
//				userRole.setName("USER");
//				roleRepository.saveAll(List.of(adminRole, userRole));
//
//				User admin = User.builder().username("admin")
//						.password(passwordEncoder.encode("admin"))
//						.role(adminRole)
//						.firstName("admin")
//						.lastName("admin")
//						.email("admin@gmail.com")
//						.build();
//
//				userRepository.save(admin);


				Setting settingMAIL_FROM = new Setting();
				settingMAIL_FROM.setKey("MAIL_FROM");
				settingMAIL_FROM.setValue("jayce@gmail.com");
				settingMAIL_FROM.setCategory(SettingCategory.MAIL_SERVER);


				Setting MAIL_HOST = new Setting();
				MAIL_HOST.setKey("MAIL_HOST");
				MAIL_HOST.setValue("smtp.gmail.com");
				MAIL_HOST.setCategory(SettingCategory.MAIL_SERVER);

				Setting MAIL_FROM = new Setting();
				MAIL_FROM.setKey("MAIL_FROM");
				MAIL_FROM.setValue("jaycedtp@gmail.com");
				MAIL_FROM.setCategory(SettingCategory.MAIL_SERVER);

				Setting MAIL_PASSWORD = new Setting();
				MAIL_PASSWORD.setKey("MAIL_PASSWORD");
				MAIL_PASSWORD.setValue("mclx ewav qpsg dvys");
				MAIL_PASSWORD.setCategory(SettingCategory.MAIL_SERVER);

				Setting MAIL_PORT = new Setting();
				MAIL_PORT.setKey("MAIL_PORT");
				MAIL_PORT.setValue("587");
				MAIL_PORT.setCategory(SettingCategory.MAIL_SERVER);


				Setting MAIL_USERNAME = new Setting();
				MAIL_USERNAME.setKey("MAIL_USERNAME");
				MAIL_USERNAME.setValue("jaycedtp@gmail.com");
				MAIL_USERNAME.setCategory(SettingCategory.MAIL_SERVER);

				Setting MAIL_SENDER_NAME = new Setting();
				MAIL_SENDER_NAME.setKey("MAIL_SENDER_NAME");
				MAIL_SENDER_NAME.setValue("ATC Contest");
				MAIL_SENDER_NAME.setCategory(SettingCategory.MAIL_SERVER);

				Setting SMTP_AUTH = new Setting();
				SMTP_AUTH.setKey("SMTP_AUTH");
				SMTP_AUTH.setValue(String.valueOf(true));
				SMTP_AUTH.setCategory(SettingCategory.MAIL_SERVER);

				Setting SMTP_SECURED = new Setting();
				SMTP_SECURED.setKey("SMTP_SECURED");
				SMTP_SECURED.setValue(String.valueOf(true));
				SMTP_SECURED.setCategory(SettingCategory.MAIL_SERVER);

				settingRepository.saveAll(List.of(settingMAIL_FROM,SMTP_SECURED,SMTP_AUTH,MAIL_USERNAME,MAIL_SENDER_NAME,
						MAIL_PORT,MAIL_PASSWORD,MAIL_FROM,MAIL_HOST));
		
			} catch (Exception e) {
				e.getStackTrace();
			}

		};
	}

}
