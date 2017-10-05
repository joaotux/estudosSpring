package com.originmobi.cobranca;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
public class CobrancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CobrancaApplication.class, args);
	}

	/*
	 * Responsável por definir o local como Brasil e a lingua como pt-br
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
}
