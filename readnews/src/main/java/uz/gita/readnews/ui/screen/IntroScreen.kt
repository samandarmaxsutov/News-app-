package uz.gita.readnews.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import uz.gita.readnews.R
import uz.gita.readnews.databinding.FragmentIntroScreenBinding
import uz.gita.readnews.models.IntroData
import uz.gita.readnews.prsenter.IntroScreenViewModel
import uz.gita.readnews.prsenter.impl.IntroScreenViewModelImpl
import uz.gita.readnews.ui.adapter.IntroAdapter


class IntroScreen : Fragment(R.layout.fragment_intro_screen) {
    private var _binding:FragmentIntroScreenBinding?=null
    private val binding get() = _binding!!
    private val list=(arrayListOf<IntroData>(
        IntroData(R.drawable.ic_launcher_foreground,"Intro1","Intro description1"),
        IntroData(R.drawable.ic_launcher_foreground,"Intro2","Intro description2"),
        IntroData(R.drawable.ic_launcher_foreground,"Intro3","Intro description3")
    ))
    private val adapter: IntroAdapter by lazy { IntroAdapter(this,list)}

    private val viewModel: IntroScreenViewModel by viewModels<IntroScreenViewModelImpl>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.openMainScreenLiveData.observe(this){
            findNavController().navigate(IntroScreenDirections.actionIntroScreenToMainScreen())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.introViewpager.adapter=adapter
        binding.indicatorView.apply {
            setSliderColor(R.color.purple_500, R.color.teal_700)
            setSliderWidth(resources.getDimension(R.dimen.dp_10))
            setSliderHeight(resources.getDimension(R.dimen.dp_5))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setPageSize(binding.introViewpager.adapter!!.itemCount)
            notifyDataChanged()
        }
        binding.introViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                binding.indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position==2){
                    binding.nextBtn.text="Go"
                }else{
                    binding.nextBtn.text="Next"
                }
                binding.indicatorView.onPageSelected(position)
            }
        })
        viewModel.nextPageOpenLiveData.observe(viewLifecycleOwner){
            binding.introViewpager.currentItem = it
        }
        binding.nextBtn.setOnClickListener {
            viewModel.next(binding.introViewpager.currentItem)
        }
        binding.skipBtn.setOnClickListener {
            viewModel.next(2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentIntroScreenBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}