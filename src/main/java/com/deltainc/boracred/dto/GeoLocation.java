package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoLocation {
    private String ip;
    private String city;
    private String country;
    private String latitude;
    private String longitude;
}
