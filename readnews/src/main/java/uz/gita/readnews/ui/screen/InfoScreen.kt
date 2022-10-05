package uz.gita.readnews.ui.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.mylibrary.NewsData
import uz.gita.readnews.R
import uz.gita.readnews.databinding.FragmentInfoScreenBinding
import uz.gita.readnews.prsenter.InfoScreenViewModel
import uz.gita.readnews.prsenter.impl.InfoScreenViewModelImpl


class InfoScreen : Fragment(R.layout.fragment_info_screen) {
    private val viewModel: InfoScreenViewModel by viewModels<InfoScreenViewModelImpl>()
    private val binding: FragmentInfoScreenBinding by viewBinding()

    private val args: InfoScreenArgs by navArgs<InfoScreenArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.title.text = args.newsData.title.toString()
        binding.txtDescription.text = args.newsData.description

        binding.txtDate.text=args.newsData.date
        Glide
            .with(binding.imageView)
            .load(args.newsData.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imageView);

        //Back Button
        val toolbar = binding.toolbarinfo
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
        lifecycleScope.launch{
            delay(14500)
            var count=args.newsData.seen.toString().toLong()
            count++
            viewModel.update(NewsData(id=args.newsData.id, title = args.newsData.title, description = args.newsData.description,
                like= args.newsData.like, notlike=args.newsData.notlike, image =args.newsData.image, seen = count.toString(), date = args.newsData.date,link=args.newsData.link ))
        }

    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, args.newsData.link)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_item, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_star -> {
                composeEmail()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun composeEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle
            putExtra(Intent.EXTRA_SUBJECT, "Ilova haqida fikrlarim")
            putExtra(Intent.EXTRA_TEXT, "Fiklaringizni zarifboyevjavohir27@gmail.com ga yuboring")
        }
        startActivity(intent)
    }
    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(activity?.window!!.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

}
