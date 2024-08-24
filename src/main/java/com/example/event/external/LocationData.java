package com.example.event.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationData {
    private String name;
    private String nameEn;
    private String full_name;
    private String full_name_en;
    private String latitude;
    private String longitude;
    private String tinh;
    private String quan;
    private String phuong;
}
