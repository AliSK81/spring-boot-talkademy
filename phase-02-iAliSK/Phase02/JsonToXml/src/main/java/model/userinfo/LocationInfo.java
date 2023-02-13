package model.userinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LocationInfo {
    private String country;
    private String location;
    private int lng;
    private int lat;
    private double livelng;
    private double livelat;
    private String liveLocation;
}
