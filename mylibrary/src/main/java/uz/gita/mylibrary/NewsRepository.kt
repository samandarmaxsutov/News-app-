package uz.gita.mylibrary

import androidx.lifecycle.LiveData

interface NewsRepository {
    val progressBar:LiveData<Boolean>
    fun placingOrder(newsData: NewsData): LiveData<Result<Unit>>
    fun getAll(): LiveData<Result<List<NewsData>>>
    fun getAll2(): LiveData<Result<List<NewsData>>>
    fun update(newsData: NewsData): LiveData<Result<Unit>>
}