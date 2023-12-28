package com.cydeo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.http.ApiVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
        servers = { @Server(url = "/user-service")},
        info = @Info(title = "Ticketing App", version = "1.0", description = "User Service API")
)
public class UserServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserServiceApplication.class,args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
