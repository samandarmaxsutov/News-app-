package uz.gita.newsalxorazmiy.presenter.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.newsalxorazmiy.presenter.SplashScreenViewModel

class SplashScreenViewModelImpl : SplashScreenViewModel,ViewModel() {
    override val openMainScreenLiveData=MutableLiveData<Unit>()

    init {
        viewModelScope.launch{
            delay(2000)
            openMainScreenLiveData.value=Unit
        }
    }
}