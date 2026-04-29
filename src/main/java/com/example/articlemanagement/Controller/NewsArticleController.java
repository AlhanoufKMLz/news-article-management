package com.example.articlemanagement.Controller;

import com.example.articlemanagement.ApiResponse.ApiResponse;
import com.example.articlemanagement.Model.NewsArticle;
import com.example.articlemanagement.Service.NewsArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/news-article")
@RequiredArgsConstructor
public class NewsArticleController {

    private final NewsArticleService newsArticleService;

    //BASIC CRUD ENDPOINTS
    @GetMapping("/get")
    public ResponseEntity<?> getArticles(){
        return ResponseEntity.status(200).body(newsArticleService.getArticles());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addArticle(@RequestBody @Valid NewsArticle article, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        boolean isDone = newsArticleService.addArticle(article);
        if(isDone)
            return ResponseEntity.status(200).body(new ApiResponse("News Article added successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("News Article with ID: " + article.getId() + " is already exist."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable String id, @RequestBody @Valid NewsArticle article, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        boolean isDone = newsArticleService.updateArticle(id, article);
        if(isDone)
            return ResponseEntity.status(200).body(new ApiResponse("Article updated successfully"));

        return ResponseEntity.status(404).body(new ApiResponse("News Article with ID: " + id + " not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable String id){
        boolean isDone = newsArticleService.deleteArticle(id);

        if(isDone)
            return ResponseEntity.status(200).body(new ApiResponse("News Article deleted successfully"));

        return ResponseEntity.status(404).body(new ApiResponse("News Article with ID: " + id + " not found"));
    }

    //EXTRA ENDPOINTS
    @PutMapping("/publish/{id}")
    public ResponseEntity<?> publishArticle(@PathVariable String id){
        int result = newsArticleService.publishArticle(id);

        if(result == 0)
            return ResponseEntity.status(400).body(new ApiResponse("News Article with ID: " + id + " is already published"));
        if(result == -1)
            return ResponseEntity.status(404).body(new ApiResponse("News Article with ID: " + id + " not found"));

        return ResponseEntity.status(200).body(new ApiResponse("News Article published successfully"));
    }

    @GetMapping("/get-published")
    public ResponseEntity<?> getPublishedArticles(){
        ArrayList<NewsArticle> publishedArticles = newsArticleService.getPublishedArticles();

        if(publishedArticles.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No published articles found."));

        return ResponseEntity.status(200).body(publishedArticles);
    }

    @GetMapping("/get-category/{category}")
    public ResponseEntity<?> getArticlesByCategory(@PathVariable String category){
        ArrayList<NewsArticle> categoryArticles = newsArticleService.getArticlesByCategory(category);

        if(categoryArticles == null)
            return ResponseEntity.status(400).body(new ApiResponse("Category must be either politics, sports or technology only."));

        if(categoryArticles.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No news articles in category: " + category + " found."));

        return ResponseEntity.status(200).body(categoryArticles);

    }

    //EXTRA
    @GetMapping("/get-sorted-by-date")
    public ResponseEntity<?> sortByPublishedDate() {
        ArrayList<NewsArticle> sortedArticles = newsArticleService.sortByPublishedDate();

        if (sortedArticles.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No published articles found."));

        return ResponseEntity.status(200).body(sortedArticles);
    }

    @GetMapping("/get-author/{author}")
    public ResponseEntity<?> getArticlesByAuthor(@PathVariable String author){
        ArrayList<NewsArticle> authorArticles = newsArticleService.getArticlesByAuthor(author);

        if(authorArticles.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No news articles for the author: " + author + " found."));

        return ResponseEntity.status(200).body(authorArticles);

    }

}
