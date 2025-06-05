package com.besstore.userCartService.model.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractBaseItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;
    protected Long productId;
    protected int quantity;
    protected BigDecimal priceSnapshot;

    public AbstractBaseItemModel(Long productId, int quantity,
                                 BigDecimal priceSnapshot) {
        this.productId = productId;
        this.quantity = quantity;
        this.priceSnapshot = priceSnapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseItemModel that = (AbstractBaseItemModel) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

}
