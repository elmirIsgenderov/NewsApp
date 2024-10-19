package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.ArticleDataBase
import com.example.newsapp.models.Article

class NewsRepository(private val db: ArticleDataBase) {

    suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNumber)


    suspend fun searchForNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)


    fun getAllArticle() = db.getArticleDAO().getAllArticle()
    suspend fun upset(article: Article) = db.getArticleDAO().upsert(article)
    suspend fun delete(article: Article) = db.getArticleDAO().deleteArticle(article)

}