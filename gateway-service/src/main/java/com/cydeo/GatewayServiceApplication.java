package com.cydeo;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class,args);
    }

    @Bean
    public List<GroupedOpenApi> apiList(SwaggerUiConfigParameters swaggerUiConfigParameters,
                                        RouteDefinitionLocator routeDefinitionLocator){
        List<GroupedOpenApi> groupedOpenApis = new ArrayList<>();
        List<RouteDefinition> definitions = routeDefinitionLocator
                .getRouteDefinitions().collectList().block();

        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")
        ).forEach(routeDefinition -> {
            String name = routeDefinition.getId().replaceAll("-service","");
            swaggerUiConfigParameters.addGroup(name);
            GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
        });
        return groupedOpenApis;
    }

}
