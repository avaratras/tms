package com.hcm.tms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class City {
    @Id
    private String cityId;


    @Size(max=50, message = "City name must be less than 50 character")
    private String cityName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    @JsonIgnore
    private List<User> userList= new ArrayList<>();

    public City() {
    }

    public City(String cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }
}
