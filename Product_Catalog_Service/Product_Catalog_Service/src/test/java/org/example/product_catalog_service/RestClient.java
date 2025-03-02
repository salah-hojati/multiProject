package org.example.product_catalog_service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestClient {

    public static void main(String[] args) {
        // URL سرور که درخواست POST را ارسال می‌کنیم
        String url = "http://localhost:8080/api/products";

        // ایجاد RestTemplate برای ارسال درخواست
        RestTemplate restTemplate = new RestTemplate();

        // ساختن داده محصول به صورت JSON
        String jsonRequest = "{\"name\":\"Phone\",\"description\":\"Smartphone\",\"price\":800}";

        // تنظیم هدر Content-Type برای درخواست JSON
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // بسته‌بندی داده‌ها و هدرها در HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        // ارسال درخواست POST و دریافت پاسخ
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // نمایش پاسخ دریافتی از سرور
        System.out.println("Response: " + response.getBody());
    }
}
