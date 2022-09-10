package uz.gita.readnews.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import uz.gita.readnews.R
import uz.gita.readnews.prsenter.SplashScreenViewModel
import uz.gita.readnews.prsenter.impl.SplashScreenViewModelImpl

class SplashScreen : Fragment(R.layout.fragment_splash_screen) {
    private val viewModel: SplashScreenViewModel by viewModels<SplashScreenViewModelImpl> ()
    private val navController : NavController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.openIntroScreenLiveData.observe(this){
            navController.navigate(SplashScreenDirections.actionSplashScreenToIntroScreen())
        }
        viewModel.openMainScreenLiveData.observe(this){
            navController.navigate(SplashScreenDirections.actionSplashScreenToMainScreen())
        }
    }
}