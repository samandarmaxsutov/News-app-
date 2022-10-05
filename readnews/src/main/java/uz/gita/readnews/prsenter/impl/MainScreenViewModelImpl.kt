package uz.gita.readnews.prsenter.impl

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.mylibrary.NewsData
import uz.gita.mylibrary.Repository
import uz.gita.readnews.prsenter.MainScreenViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainScreenViewModelImpl : MainScreenViewModel, ViewModel() {
    override val openInfoScreenLiveData = MutableLiveData<NewsData>()
    override val newsLiveData = MediatorLiveData<List<NewsData>>()
    override val massageLiveData = MutableLiveData<String>()
    override val progressBarLiveData=MutableLiveData<Boolean>()
    private val repository = Repository.newsRepository
    init {


        newsLiveData.addSource(repository.getAll2()) {

            if (it.isSuccess) {
                newsLiveData.postValue(  it.getOrNull())
                progressBarLiveData.value=false

//                   massageLiveData.postValue(  "ma'lumotlar yangilandi")
            }
            else {
                massageLiveData.postValue(  it.exceptionOrNull().toString())
                progressBarLiveData.value=false
            }

        }
        newsLiveData.addSource(repository.progressBar){
            progressBarLiveData.value=it
        }
    }

    override fun openInfoLiveData(newsData: NewsData) {
        openInfoScreenLiveData.value = newsData
    }

    override fun searchView(txt: String) {
        newsLiveData.addSource(repository.getAll2()) {

            val list=ArrayList<NewsData>()
            if (it.isSuccess) {

                Log.d("ma'lumot","${it.getOrNull().toString()}")
                it.getOrNull()?.forEach { newsData->
                    if (newsData.title.lowercase(Locale.getDefault()).contains(txt.lowercase(Locale.getDefault()))) {
                        if (!list.contains(newsData))list.add(newsData)
                    }

                    if (newsData.description.lowercase(Locale.getDefault()).contains(txt.lowercase(Locale.getDefault()))) {

                        if (!list.contains(newsData))list.add(newsData)
                    }

                    if (newsData.like.lowercase(Locale.getDefault()).contains(txt.lowercase(Locale.getDefault()))) {
                        if (!list.contains(newsData))list.add(newsData)
                    }

                    if (newsData.notlike.lowercase(Locale.getDefault()).contains(txt.lowercase(Locale.getDefault()))) {
                        if (!list.contains(newsData))list.add(newsData)
                    }

                    if (newsData.date.lowercase(Locale.getDefault()).contains(txt.lowercase(Locale.getDefault()))) {
                        if (!list.contains(newsData))list.add(newsData)
                    }


                }


                newsLiveData.postValue(  list)
                progressBarLiveData.value=false
//                massageLiveData.postValue(  "ma'lumotlar yangilandi")
            }
            else {
                massageLiveData.postValue(  it.exceptionOrNull().toString())

            }
            progressBarLiveData.value=false
        }
    }
}