package uz.gita.readnews.prsenter.impl


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.readnews.prsenter.SplashScreenViewModel

class SplashScreenViewModelImpl : SplashScreenViewModel, ViewModel() {
    override val openMainScreenLiveData = MutableLiveData<Unit>()
    override val openIntroScreenLiveData = MutableLiveData<Unit>()

    private val repository = IntroRepositoryImpl.getInstance()

    init {

        viewModelScope.launch {
            delay(2000)
            if (repository.isFirst) {
                openIntroScreenLiveData.value = Unit
            } else {
                openMainScreenLiveData.value = Unit
            }


        }
    }

}