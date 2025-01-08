package com.ayushi.CustomerRewardPointsApplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Customer")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("customerId")
    @Column(name = "customerId")
    private String customerId;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("email")
    @Column(name = "email")
    private String email;
}

