package uz.gita.readnews.prsenter.impl


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.readnews.prsenter.IntroScreenViewModel


class IntroScreenViewModelImpl : IntroScreenViewModel,ViewModel() {
    private val repository= IntroRepositoryImpl.getInstance()
    override val nextPageOpenLiveData=MutableLiveData<Int>()
    override val openMainScreenLiveData=MutableLiveData<Unit>()

    override fun next(pos: Int) {
        if (pos==2) {
            repository.isFirst = false
            openMainScreenLiveData.postValue(Unit)
        }
        else nextPageOpenLiveData.value=(1+pos)
    }

}