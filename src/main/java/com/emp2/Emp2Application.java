package com.emp2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author guanzepeng
 */
@EnableOpenApi
@SpringBootApplication
@MapperScan(basePackages = "com.emp2.dao")
public class Emp2Application {

	public static void main(String[] args) {
		SpringApplication.run(Emp2Application.class, args);
	}

}
