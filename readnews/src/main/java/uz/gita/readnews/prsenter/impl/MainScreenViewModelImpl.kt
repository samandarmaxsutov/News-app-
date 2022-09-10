package uz.gita.readnews.prsenter.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.mylibrary.NewsData
import uz.gita.mylibrary.Repository
import uz.gita.readnews.prsenter.MainScreenViewModel

class MainScreenViewModelImpl : MainScreenViewModel, ViewModel() {
    override val openInfoScreenLiveData = MutableLiveData<NewsData>()
    override val newsLiveData = MediatorLiveData<List<NewsData>>()
    override val massageLiveData = MutableLiveData<String>()
    private val repository = Repository.newsRepository

    init {


           newsLiveData.addSource(repository.getAll2()) {

               if (it.isSuccess) {
                   newsLiveData.postValue(  it.getOrNull())
                   massageLiveData.postValue(  "ma'lumotlar yangilandi")
               } else {
                   massageLiveData.postValue(  it.exceptionOrNull().toString())
               }
       }
    }

    override fun openInfoLiveData(newsData: NewsData) {
        openInfoScreenLiveData.value = newsData
    }
}