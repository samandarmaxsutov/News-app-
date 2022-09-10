package uz.gita.readnews.app

import android.app.Application
import uz.gita.readnews.models.sharedperfarnce.SharedPref
import uz.gita.readnews.prsenter.IntroRepository
import uz.gita.readnews.prsenter.impl.IntroRepositoryImpl

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(this)
        IntroRepositoryImpl.init()
    }
}