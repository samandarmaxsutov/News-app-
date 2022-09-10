package uz.gita.readnews.prsenter

import androidx.lifecycle.LiveData
import uz.gita.mylibrary.NewsData

interface MainScreenViewModel {
    val openInfoScreenLiveData:LiveData<NewsData>
    val newsLiveData:LiveData<List<NewsData>>
    val massageLiveData:LiveData<String>


    fun openInfoLiveData(newsData: NewsData)


}