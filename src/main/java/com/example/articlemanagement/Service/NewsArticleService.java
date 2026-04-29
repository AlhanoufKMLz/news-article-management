package com.example.articlemanagement.Service;

import com.example.articlemanagement.Model.NewsArticle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NewsArticleService {

    ArrayList<NewsArticle> articles = new ArrayList<>();

    public ArrayList<NewsArticle> getArticles(){
        return articles;
    }

    public boolean addArticle(NewsArticle article){
        for(NewsArticle a: articles){
            if(a.getId().equalsIgnoreCase(article.getId()))
                return false;
        }

        articles.add(article);
        return true;
    }

    public boolean updateArticle(String id, NewsArticle article){
        for(int i = 0; i < articles.size(); i++){
            if(articles.get(i).getId().equalsIgnoreCase(id)) {
                articles.set(i, article);
                return true;
            }
        }
        return false;
    }

    public boolean deleteArticle(String id){
        for(int i = 0; i < articles.size(); i++){
            if(articles.get(i).getId().equalsIgnoreCase(id)) {
                articles.remove(i);
                return true;
            }
        }
        return false;
    }

    public int publishArticle(String id){
        for(NewsArticle a: articles){
            if(a.getId().equalsIgnoreCase(id)) {
                if (a.isPublished())
                    return 0; //already published
                a.setPublished(true);
                return 1; //found and updated
            }
        }
        return -1; //not found
    }

    public ArrayList<NewsArticle> getPublishedArticles(){
        ArrayList<NewsArticle> publishedArticles = new ArrayList<>();
        for(NewsArticle a: articles){
            if(a.isPublished())
                publishedArticles.add(a);
        }
        return publishedArticles;
    }

    public ArrayList<NewsArticle> getArticlesByCategory(String category){
        if(!category.equalsIgnoreCase("politics") && !category.equalsIgnoreCase("sports") && !category.equalsIgnoreCase("technology"))
            return null;

        ArrayList<NewsArticle> categoryArticles = new ArrayList<>();
        for(NewsArticle a: articles){
            if(a.getCategory().equalsIgnoreCase(category))
                categoryArticles.add(a);
        }
        return categoryArticles;
    }
}
