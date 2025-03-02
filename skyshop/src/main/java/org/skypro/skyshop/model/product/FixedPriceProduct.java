package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixedPriceProduct extends Product {
    private static final int FIXED_PRICE = 500;

    public FixedPriceProduct(UUID id, String name) {
        super(id, name);
    }

    @Override
    public int getPrice() {
        return FIXED_PRICE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": Фиксированная цена " + FIXED_PRICE;
    }
}
