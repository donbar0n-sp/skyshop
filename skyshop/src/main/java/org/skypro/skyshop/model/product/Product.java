package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private final String name;

    public Product(UUID id, String name) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null, empty, or only whitespace.");
        }
        this.name = name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getSearchTerm() {
        return name;
    }

    @JsonIgnore
    public String getContentType() {
        return "PRODUCT";
    }

    public abstract int getPrice();
    public abstract boolean isSpecial();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
