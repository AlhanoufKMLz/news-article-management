package com.example.articlemanagement.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class NewsArticle {

    @NotEmpty(message = "ID can't be empty.")
    private String id;

    @NotEmpty(message = "Title can't be empty.")
    @Size(max = 100, message = "Title must be at most 100 characters.")
    private String title;

    @NotEmpty(message = "Author can't be empty.")
    @Size(min = 4, max = 20, message = "Author must be between 5 and 20 characters.")
    private String author;

    @NotEmpty(message = "Content can't be empty.")
    @Size(min = 201, message = "Content must be more than 200 characters.")
    private String content;

    @NotEmpty(message = "Category can't be empty.")
    @Pattern(regexp = "^(politics|sports|technology)$")
    private String category;

    @NotEmpty(message = "Image URL can't be empty.")
    private String imageUrl;

    private boolean isPublished;

    private LocalDate publishDate;

}
