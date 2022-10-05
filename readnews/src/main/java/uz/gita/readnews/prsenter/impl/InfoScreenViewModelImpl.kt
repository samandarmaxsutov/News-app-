package uz.gita.readnews.prsenter.impl

import androidx.lifecycle.ViewModel
import uz.gita.mylibrary.NewsData
import uz.gita.mylibrary.NewsRepository
import uz.gita.mylibrary.Repository
import uz.gita.readnews.prsenter.InfoScreenViewModel

class InfoScreenViewModelImpl : InfoScreenViewModel, ViewModel(){
    private val repository = Repository.newsRepository
    override fun update(newsData: NewsData) {
        repository.update(newsData)
    }

}