package uz.gita.mylibrary

import androidx.lifecycle.LiveData

interface NewsRepository {
    fun placingOrder(orderData: NewsData): LiveData<Result<Unit>>
    fun getAll(): LiveData<Result<List<NewsData>>>
    fun getAll2(): LiveData<Result<List<NewsData>>>
}