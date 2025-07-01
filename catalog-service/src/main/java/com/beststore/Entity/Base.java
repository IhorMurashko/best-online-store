package com.beststore.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


}
