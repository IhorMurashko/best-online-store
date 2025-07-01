package com.beststore.Entity;

import jakarta.persistence.Entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producer extends Base {

    String producerName;
}
