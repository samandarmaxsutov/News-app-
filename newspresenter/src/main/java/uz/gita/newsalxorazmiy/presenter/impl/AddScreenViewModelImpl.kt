package uz.gita.newsalxorazmiy.presenter.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.mylibrary.NewsData
import uz.gita.mylibrary.NewsRepository
import uz.gita.mylibrary.Repository
import uz.gita.newsalxorazmiy.presenter.AddScreenViewModel

class AddScreenViewModelImpl : AddScreenViewModel,ViewModel() {
    override val backLiveData=MutableLiveData<Unit>()
    override val addLiveData=MediatorLiveData<String>()
    private val repository:NewsRepository=Repository.newsRepository

    override fun addNews(data: NewsData) {
        addLiveData.addSource(repository.placingOrder(data)){
            if (it.isSuccess) {
                addLiveData.value="${data.title} qo'shildi"
                back()
            }
        }
    }

    override fun back() {
        backLiveData.value=Unit
    }
}