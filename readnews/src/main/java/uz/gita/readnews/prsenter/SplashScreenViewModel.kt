package uz.gita.readnews.prsenter

import androidx.lifecycle.LiveData

interface SplashScreenViewModel {
    val openMainScreenLiveData:LiveData<Unit>
    val openIntroScreenLiveData:LiveData<Unit>

}