package com.siemens.hackathon.application.db.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class H2Configuration {
	/*@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setName("hackathon").setType(EmbeddedDatabaseType.H2).build();
	}*/
}
