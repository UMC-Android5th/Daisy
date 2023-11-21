package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentStoredmusicBinding
import java.util.ArrayList


class StoredMusicFragment: Fragment() {

    lateinit var binding: FragmentStoredmusicBinding
    private var albumDatas = ArrayList<Album>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentStoredmusicBinding.inflate(inflater, container, false)

        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

        val albumRVAdapter = AlbumStoredRVAdapter(albumDatas)
        binding.storedMusicRv.adapter = albumRVAdapter
        binding.storedMusicRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        binding.storedMusicRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = albumRVAdapter
        }

        return binding.root
    }
}