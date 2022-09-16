package uz.gita.readnews.prsenter

import androidx.lifecycle.LiveData
import uz.gita.mylibrary.NewsData

interface MainScreenViewModel {
    val openInfoScreenLiveData:LiveData<NewsData>
    val newsLiveData:LiveData<List<NewsData>>
    val massageLiveData:LiveData<String>
    val progressBarLiveData:LiveData<Boolean>
    fun searchView(txt: String)

    fun openInfoLiveData(newsData: NewsData)


}