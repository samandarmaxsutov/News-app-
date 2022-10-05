package uz.gita.newsalxorazmiy.presenter

import androidx.lifecycle.LiveData
import uz.gita.mylibrary.NewsData

interface AddScreenViewModel {

    val backLiveData:LiveData<Unit>
    val addLiveData:LiveData<String>

    fun addNews(data:NewsData)
    fun back()
}