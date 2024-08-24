package com.example.event.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalAPIService {
    
    private RestTemplate restTemplate;
    @Autowired
    public ExternalAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LocationData getLocationDataById(Integer id) {
        String url = "https://esgoo.net/api-tinhthanh/5/"+id+".htm";
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
        
        if (response != null && response.getError() == 0) {
            return response.getData();
        } else {
            // Xử lý khi có lỗi hoặc không có dữ liệu
            return null;
        }
    }
}
