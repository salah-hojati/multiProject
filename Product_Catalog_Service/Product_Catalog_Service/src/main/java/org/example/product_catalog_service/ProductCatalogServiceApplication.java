package org.example.product_catalog_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ProductCatalogServiceApplication  implements CommandLineRunner {
    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    public static void main(String[] args) {
        new File("data").mkdirs();
        SpringApplication.run(ProductCatalogServiceApplication.class, args);
    }
    @Override
    public void run(String... args) {
        System.out.println("Database URL: " + datasourceUrl);
    }
}
