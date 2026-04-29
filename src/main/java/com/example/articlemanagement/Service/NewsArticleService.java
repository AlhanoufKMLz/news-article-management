package com.example.articlemanagement.Service;

import com.example.articlemanagement.Model.NewsArticle;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

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
                article.setId(id); //make sure the user doesn't change the id
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
                a.setPublishDate(LocalDate.now());
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

    //EXTRA
    public ArrayList<NewsArticle> sortByPublishedDate(){
        ArrayList<NewsArticle> sortedArticles = new ArrayList<>();
        for (NewsArticle a : articles) {
            if (a.isPublished())
                sortedArticles.add(a);
        }
        sortedArticles.sort((a1, a2) -> a2.getPublishDate().compareTo(a1.getPublishDate()));
        return sortedArticles;
    }

    public ArrayList<NewsArticle> getArticlesByAuthor(String author){
        ArrayList<NewsArticle> authorArticles = new ArrayList<>();
        for(NewsArticle a: articles){
            if(a.getAuthor().equalsIgnoreCase(author))
                authorArticles.add(a);
        }
        return authorArticles;
    }
}
