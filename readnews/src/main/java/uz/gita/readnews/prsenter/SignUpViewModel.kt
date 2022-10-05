package uz.gita.readnews.prsenter

import android.app.Activity
import androidx.lifecycle.LiveData
import uz.gita.readnews.models.UserData

interface SignUpViewModel {
    val openMainScreenLivedata:LiveData<Unit>
    val error:LiveData<String>
    val message:LiveData<String>
    val progressLiveData:LiveData<Boolean>
    fun create(context: Activity, userData: UserData)
}