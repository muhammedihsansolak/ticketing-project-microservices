package com.cydeo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@OpenAPIDefinition(
        servers = { @Server(url = "/task-service")},
        info = @Info(title = "Ticketing App", version = "1.0", description = "Task Service API")
)
public class TaskServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(TaskServiceApplication.class,args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
