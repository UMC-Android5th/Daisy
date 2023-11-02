package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentHomeBinding
import me.relex.circleindicator.CircleIndicator3

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var pannelAdapter: PannelVPAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homePannelTodayAlbumIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }

        binding.homePannelTodayAlbumIv2.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Create the adapter and add fragments
        pannelAdapter = PannelVPAdapter(this)
        pannelAdapter.addFragment(PannelFragment(R.drawable.img_first_album_default))
        pannelAdapter.addFragment(PannelFragment(R.drawable.discovery_banner_aos))

        // Set the adapter for the ViewPager2
        binding.homePannelVp.adapter = pannelAdapter
        binding.homePannelVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val viewPager = binding.homePannelVp // ViewPager2의 ID로 변경할 수 있음
        val indicator = binding.indicator

        // CircleIndicator와 ViewPager2 연결
        indicator.setViewPager(viewPager)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Start auto sliding
        startAutoSlide()
    }

    private fun startAutoSlide() {
        val viewPager = binding.homePannelVp
        val indicator = binding.indicator

        val autoSlideHandler = Handler()
        val delay: Long = 2000 // 슬라이딩 간격 (2초)

        val autoSlideRunnable = object : Runnable {
            override fun run() {
                val currentItem = viewPager.currentItem
                val nextItem = if (currentItem == pannelAdapter.itemCount - 1) 0 else currentItem + 1
                viewPager.currentItem = nextItem
                autoSlideHandler.postDelayed(this, delay)
            }
        }

        autoSlideHandler.postDelayed(autoSlideRunnable, delay)
    }
}
