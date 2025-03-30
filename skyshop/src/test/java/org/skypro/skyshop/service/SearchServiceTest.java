package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    private Product testProduct;
    private Article testArticle;

    @BeforeEach
    void setUp() {
        testProduct = new SimpleProduct(UUID.randomUUID(), "Яблоко", 100);
        testArticle = new Article(UUID.randomUUID(), "Яблоко: Полезные свойства", "Яблоки помогают здоровью.");
    }

    @Test
    void searchWhenStorageIsEmptyReturnsEmptyList() {
        when(storageService.getAllSearchables()).thenReturn(List.of());

        Collection<SearchResult> results = searchService.search("Яблоко");

        assertTrue(results.isEmpty(), "Expected empty search results when storage is empty");
        verify(storageService, times(1)).getAllSearchables();
    }

    @Test
    void searchWhenNoMatchingObjectsReturnsEmptyList() {
        when(storageService.getAllSearchables()).thenReturn(List.of(testProduct, testArticle));

        Collection<SearchResult> results = searchService.search("Банан");

        assertTrue(results.isEmpty(), "Expected empty search results when no objects match");
        verify(storageService, times(1)).getAllSearchables();
    }

    @Test
    void searchWhenMatchingObjectsExistReturnsCorrectResults() {
        when(storageService.getAllSearchables()).thenReturn(List.of(testProduct, testArticle));

        Collection<SearchResult> results = searchService.search("Яблоко");

        assertEquals(2, results.size(), "Expected two matching search results");
        verify(storageService, times(1)).getAllSearchables();
    }
}
