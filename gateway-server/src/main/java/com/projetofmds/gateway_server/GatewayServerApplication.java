package com.projetofmds.gateway_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(excludeName = {
    "org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration",
    "org.springframework.cloud.autoconfigure.LifecycleMvcEndpointAutoConfiguration",
    "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration",
    "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
    "org.springframework.cloud.autoconfigure.RefreshAutoConfiguration",
    "org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration",
    "org.springframework.cloud.client.discovery.simple.reactive.SimpleReactiveDiscoveryClientAutoConfiguration",
    "org.springframework.cloud.netflix.eureka.reactive.EurekaReactiveDiscoveryClientConfiguration"
})
@EnableDiscoveryClient
public class GatewayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }
}