package uz.gita.readnews.prsenter

import androidx.lifecycle.LiveData

interface IntroScreenViewModel {
    val nextPageOpenLiveData:LiveData<Int>
    val openMainScreenLiveData:LiveData<Unit>
     fun next(pos:Int)
}