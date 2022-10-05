package uz.gita.newsalxorazmiy.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mylibrary.NewsData
import uz.gita.mylibrary.utils.massage
import uz.gita.newsalxorazmiy.R
import uz.gita.newsalxorazmiy.databinding.FragmentAddScreenBinding
import uz.gita.newsalxorazmiy.databinding.FragmentMainScreenBinding
import uz.gita.newsalxorazmiy.presenter.AddScreenViewModel
import uz.gita.newsalxorazmiy.presenter.MainScreenViewModel
import uz.gita.newsalxorazmiy.presenter.impl.AddScreenViewModelImpl
import uz.gita.newsalxorazmiy.presenter.impl.MainScreenViewModelImpl

class AddScreen : Fragment(R.layout.fragment_add_screen) {
    private val viewModel: AddScreenViewModel by viewModels<AddScreenViewModelImpl>()
    private val binding: FragmentAddScreenBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.addLiveData.observe(this){
            massage(requireActivity(),it)
        }
        viewModel.backLiveData.observe(this){
            findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addButton.setOnClickListener {
            viewModel.addNews(
                NewsData(
                    title = binding.titleReg.text.toString(),
                    description = binding.descTitle.text.toString(),
                    like = binding.hashtag1.text.toString(),
                    notlike = binding.hashtag2.text.toString(),
                    image = binding.linkImage.text.toString(),
                    seen = binding.seenItem.text.toString(),
                    date = binding.dateItem.text.toString()
                )
            )
        }
    }
}