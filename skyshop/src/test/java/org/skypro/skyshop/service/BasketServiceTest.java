package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exceptions.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;  // Mocking ProductBasket

    @Mock
    private StorageService storageService;  // Mocking StorageService

    @InjectMocks
    private BasketService basketService;  // Injecting mocks into BasketService

    private UUID existingProductId;
    private Product existingProduct;

    @BeforeEach
    void setUp() {
        existingProductId = UUID.randomUUID();
        existingProduct = new SimpleProduct(existingProductId, "Test Product", 100);
    }

    @Test
    void addProductToBasket_ShouldThrowException_WhenProductNotExists() {
        // Given: storageService returns empty for the product
        when(storageService.getProductById(existingProductId)).thenReturn(Optional.empty());

        // When & Then: Expect NoSuchProductException when adding a non-existing product
        assertThrows(NoSuchProductException.class, () -> basketService.addProductToBasket(existingProductId));

        // Verify that productBasket.addProduct was never called
        verify(productBasket, never()).addProduct(any(UUID.class));
    }

    @Test
    void addProductToBasket_ShouldCallProductBasketAddProduct_WhenProductExists() {
        // Given: storageService finds the product
        when(storageService.getProductById(existingProductId)).thenReturn(Optional.of(existingProduct));

        // When: We add the product
        basketService.addProductToBasket(existingProductId);

        // Then: Ensure productBasket.addProduct was called once
        verify(productBasket, times(1)).addProduct(existingProductId);
    }

    @Test
    void getUserBasket_ShouldReturnEmpty_WhenNoProductsInBasket() {
        // Given: Empty basket
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());

        // When: Retrieving the basket
        UserBasket userBasket = basketService.getUserBasket();

        // Then: Basket should be empty
        assertTrue(userBasket.getItems().isEmpty());
        assertEquals(0.0, userBasket.getTotal());
    }

    @Test
    void getUserBasket_ShouldReturnValidBasket_WhenProductsExist() {
        // Given: Basket contains a product with quantity 2
        Map<UUID, Integer> productsInBasket = new HashMap<>();
        productsInBasket.put(existingProductId, 2);

        when(productBasket.getProducts()).thenReturn(productsInBasket);
        when(storageService.getProductById(existingProductId)).thenReturn(Optional.of(existingProduct));

        // When: Retrieving the basket
        UserBasket userBasket = basketService.getUserBasket();

        // Then: Basket should contain the product with correct quantity and price
        assertFalse(userBasket.getItems().isEmpty());
        assertEquals(1, userBasket.getItems().size());

        BasketItem basketItem = userBasket.getItems().get(0);
        assertEquals(existingProduct, basketItem.getProduct());
        assertEquals(2, basketItem.getQuantity());

        // Total price should be 100 * 2 = 200
        assertEquals(200.0, userBasket.getTotal());
    }
}
