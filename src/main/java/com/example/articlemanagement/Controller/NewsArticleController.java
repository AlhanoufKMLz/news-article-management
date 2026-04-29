package com.example.articlemanagement.Controller;

import com.example.articlemanagement.ApiResponse.ApiResponse;
import com.example.articlemanagement.Model.NewsArticle;
import com.example.articlemanagement.Service.NewsArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news-artical")
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

}
