package uz.gita.readnews.ui.screen



import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout

import uz.gita.readnews.R
import uz.gita.readnews.databinding.FragmentInfoScreenBinding
import uz.gita.readnews.prsenter.InfoScreenViewModel
import uz.gita.readnews.prsenter.impl.InfoScreenViewModelImpl


class InfoScreen : Fragment(R.layout.fragment_info_screen) {
    private val viewModel: InfoScreenViewModel by viewModels<InfoScreenViewModelImpl>()
    private val binding: FragmentInfoScreenBinding by viewBinding()

    private val args:InfoScreenArgs by navArgs<InfoScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.title.text=args.newsData.title.toString()
        binding.txtDescription.text=args.newsData.description

        Glide
            .with(binding.imageView)
            .load(args.newsData.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imageView);

        //Back Button
        val toolbar=binding.toolbarinfo
        toolbar.setNavigationIcon(R.drawable.ic_action_back)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).actionBar?.setDisplayHomeAsUpEnabled(true);
        (activity as AppCompatActivity).actionBar?.setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.share.setOnClickListener {
            share()
        }
    }

   private fun share(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://ictschool.uz/")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }
}
