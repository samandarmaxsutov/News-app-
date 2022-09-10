package uz.gita.readnews.ui.screen



import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide

import uz.gita.readnews.R
import uz.gita.readnews.databinding.FragmentInfoScreenBinding
import uz.gita.readnews.prsenter.InfoScreenViewModel
import uz.gita.readnews.prsenter.impl.InfoScreenViewModelImpl


class InfoScreen : Fragment(R.layout.fragment_info_screen) {
    private val viewModel: InfoScreenViewModel by viewModels<InfoScreenViewModelImpl>()
    private val binding: FragmentInfoScreenBinding by viewBinding()

    private val args:InfoScreenArgs by navArgs<InfoScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.txtTitle.title=args.newsData.title
        binding.txtDescription.text=args.newsData.description
        Glide
            .with(binding.imageView)
            .load(args.newsData.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imageView);
    }
}