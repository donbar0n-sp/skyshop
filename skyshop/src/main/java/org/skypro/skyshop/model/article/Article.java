package org.skypro.skyshop.model.article;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public final class Article implements org.skypro.skyshop.model.search.Searchable {
    private final UUID id;
    private final String title;
    private final String content;

    public Article(UUID id, String title, String content) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.title = title;
        this.content = content;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String getName() {
        return title;
    }

    @JsonIgnore
    public String getSearchTerm() {
        return title + "\n" + content;
    }

    @JsonIgnore
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String toString() {
        return title + "\n" + content;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Article article = (Article) obj;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}