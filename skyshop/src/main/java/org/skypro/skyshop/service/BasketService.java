package org.skypro.skyshop.service;

import org.skypro.skyshop.exceptions.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProductToBasket (UUID id) {
        Product product = storageService.getProductById(id)
                .orElseThrow(() -> new NoSuchProductException("Product with ID " + id + " not found"));
        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        List<BasketItem> items = productBasket.getProducts().entrySet().stream()
                .map(entry -> new BasketItem(storageService.getProductById(entry.getKey())
                        .orElseThrow(() -> new NoSuchProductException("Product with ID " + entry.getKey() + " not found")), entry.getValue()))
                .toList();

        double total = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        return new UserBasket(items, total);
    }
}
