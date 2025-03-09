package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixedPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {

    private final Map<UUID, Product> productStorage;
    private final Map<UUID, Article> articleStorage;

    public StorageService() {
        this.productStorage = new HashMap<>();
        this.articleStorage = new HashMap<>();
        initializeTestData();
    }

    private void addProduct(Product product) {
        productStorage.put(product.getId(), product);
    }

    private void addArticle(Article article) {
        articleStorage.put(article.getId(), article);
    }

    public Collection<Product> getAllProducts() {
        return productStorage.values();
    }

    public Collection<Article> getAllArticles() {
        return articleStorage.values();
    }

    private void initializeTestData() {
        addProduct(new SimpleProduct(UUID.randomUUID(), "Яблоко", 100));
        addProduct(new DiscountedProduct(UUID.randomUUID(), "Банан", 50, 20));
        addProduct(new FixedPriceProduct(UUID.randomUUID(), "Апельсин"));
        addProduct(new DiscountedProduct(UUID.randomUUID(), "Виноград", 120, 50));
        addProduct(new SimpleProduct(UUID.randomUUID(), "Манго", 150));
        addProduct(new FixedPriceProduct(UUID.randomUUID(), "Арбуз"));

        addArticle(new Article(UUID.randomUUID(), "Яблоко: Полезные свойства", "Яблоки помогают поддерживать здоровье сердца."));
        addArticle(new Article(UUID.randomUUID(), "Витамины в бананах", "Бананы богаты калием и полезны для восстановления мышц."));
        addArticle(new Article(UUID.randomUUID(), "Короткое", "Статья с коротким названием."));
        addArticle(new Article(UUID.randomUUID(), "Очень длинное название статьи", "Статья с длинным названием."));
        addArticle(new Article(UUID.randomUUID(), "Среднее название", "Статья со средним названием."));
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();
        searchables.addAll(productStorage.values());
        searchables.addAll(articleStorage.values());
        return searchables;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(productStorage.get(id));
    }

}
