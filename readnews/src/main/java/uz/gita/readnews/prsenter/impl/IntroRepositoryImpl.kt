package uz.gita.readnews.prsenter.impl

import uz.gita.readnews.models.sharedperfarnce.SharedPref
import uz.gita.readnews.prsenter.IntroRepository


class IntroRepositoryImpl private constructor(): IntroRepository {
    companion object{
        private var introRepositoryImpl:IntroRepository?=null
        fun init(){
            introRepositoryImpl=IntroRepositoryImpl()
        }
        fun getInstance()= introRepositoryImpl!!
    }
    private var sharedPref= SharedPref.getInstance()
    override var isFirst: Boolean; get() = sharedPref.isFirst ; set(value) {sharedPref.isFirst=value}

}