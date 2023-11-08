package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding
    private var gson: Gson = Gson()

    private val information = arrayListOf("수록곡", "상세정보", "영상")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        setInit(album)

        binding.btnArrowBlackIb.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()
        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter=albumAdapter
        TabLayoutMediator(binding.tabs, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()



        return binding.root
    }

    private fun setInit(album: Album) {
        binding.imgAlbum5thLilacIv.setImageResource(album.coverImg!!)
        binding.album5thTitleTv.text = album.title.toString()
        binding.album5thSingerTv.text = album.singer.toString()
    }
}