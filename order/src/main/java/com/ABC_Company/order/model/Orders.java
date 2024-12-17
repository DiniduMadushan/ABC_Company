package com.ABC_Company.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    private int id;
    private int itemId;
    private int amount;
    private String orderDate;
}
