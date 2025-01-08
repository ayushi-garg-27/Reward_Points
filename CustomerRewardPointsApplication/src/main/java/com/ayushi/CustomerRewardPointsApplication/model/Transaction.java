package com.ayushi.CustomerRewardPointsApplication.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Transaction")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Transaction {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @JsonProperty("customerId")
        @Column(name = "customerId")
        private String customerId;

        @JsonProperty("amount")
        @Column(name = "amount")
        private double amount;

        @JsonProperty("transactionMonth")
        @Column(name = "transactionMonth")
        private String month;

    public Transaction() {
    }

    public Transaction(String customerId, double amount, String month) {
        this.customerId = customerId;
        this.amount = amount;
        this.month = month;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


}