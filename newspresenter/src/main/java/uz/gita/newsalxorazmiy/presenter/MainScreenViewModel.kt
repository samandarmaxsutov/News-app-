package uz.gita.newsalxorazmiy.presenter

import androidx.lifecycle.LiveData
import uz.gita.mylibrary.NewsData

interface MainScreenViewModel {
    val openAddScreenLiveData:LiveData<Unit>
    val newsLiveData:LiveData<List<NewsData>>
    val massageLiveData:LiveData<String>
    val progressBarLiveData:LiveData<Boolean>
    fun searchView(txt: String)

    fun openAddScreen()


}