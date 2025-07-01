package com.beststore.userservice.model;

import com.beststore.userservice.utils.CardNumberEncryptor;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "billing_info")
@Getter
@Setter
@NoArgsConstructor
public class BillingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameOnCard;
    @Convert(converter = CardNumberEncryptor.class)
    private String cardNumber;
    private String expireDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
