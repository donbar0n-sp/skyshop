package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private int basePrice;
    private int percentageDiscount;

    public DiscountedProduct(UUID id, String name, int basePrice, int percentageDiscount) {
        super(id, name);
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Base price must be greater than 0.");
        }
        if (percentageDiscount < 0 || percentageDiscount > 100) {
            throw new IllegalArgumentException("Percentage discount must be between 0 and 100 inclusive.");
        }
        this.basePrice = basePrice;
        this.percentageDiscount = percentageDiscount;
    }

    @Override
    public int getPrice() {
        return basePrice - (basePrice * percentageDiscount / 100);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPrice() + " (" + percentageDiscount + "%)";
    }
}
