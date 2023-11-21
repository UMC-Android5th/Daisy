package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.gson.Gson
import me.relex.circleindicator.CircleIndicator3
import java.util.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var pannelAdapter: PannelVPAdapter
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicRv.adapter = albumRVAdapter
        binding.homeTodayMusicRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{

            override fun  onItemClick(album: Album) {}
            override fun onRemoveAlbum(position: Int) {}
            override fun onPlayButtonClick(album: Album) {
            }

            //override fun onItemClick(album: Album) {
                //(context as MainActivity).supportFragmentManager.beginTransaction()
                //    .replace(R.id.main_frm, AlbumFragment().apply {
                //        arguments=Bundle().apply {
                //            val gson=Gson()
                //            val albumJson = gson.toJson(album)
                //            putString("album", albumJson)
                //        }
                //    })
                //    .commitAllowingStateLoss()
                //changeAlbumFragment(album)
            //}

            //override fun onRemoveAlbum(position: Int) {
                //albumRVAdapter.removeItem(position)
            //}
        })


        binding.homeTodayMusicRv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }

        binding.homeTodayMusicRv.setOnClickListener {
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

    private fun changeAlbumFragment(album: Album){
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments=Bundle().apply {
                    val gson=Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }
}
