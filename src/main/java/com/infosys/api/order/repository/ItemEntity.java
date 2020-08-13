package com.infosys.api.order.repository;

import lombok.Data;


import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ItemEntity {
    private String itemName;
    //@Column
    private UUID cartIdentifier;
    //@Column
    private BigDecimal price;
    //@Column
    private Integer quantity;

    public ItemEntity() {
    }

    public ItemEntity(String itemName, UUID cartIdentifier, BigDecimal price, Integer quantity) {
        this.itemName = itemName;
        this.cartIdentifier = cartIdentifier;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public UUID getCartIdentifier() {
        return cartIdentifier;
    }

    public void setCartIdentifier(UUID cartIdentifier) {
        this.cartIdentifier = cartIdentifier;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
