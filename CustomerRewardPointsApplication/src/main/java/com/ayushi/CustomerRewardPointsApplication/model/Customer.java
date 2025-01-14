package com.ayushi.CustomerRewardPointsApplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="Customer")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Customer {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @JsonProperty("customerId")
    @Column(name = "customerId")
    private String customerId;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;
}

