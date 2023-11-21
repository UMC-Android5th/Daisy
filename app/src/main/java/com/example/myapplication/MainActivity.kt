package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.databinding.ActivityMainBinding
import androidx.activity.result.ActivityResult
import android.widget.Toast
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), AlbumRVAdapter.MyItemClickListener{

    override fun onItemClick(album: Album) {
        // 앨범을 클릭한 경우 처리
    }

    override fun onRemoveAlbum(position: Int) {
        // 앨범을 제거한 경우 처리
    }

    override fun onPlayButtonClick(album: Album) {
        updateMiniPlayer(album)
    }

    //companion object { const val STRING_INTENT_KEY = "my_string_key"}

    //private val getResultText = registerForActivityResult(
    //    ActivityResultContracts.StartActivityForResult()
    //) { result ->
    //    if (result.resultCode == Activity.RESULT_OK) {
    //        val resultString = result.data?.getStringExtra(STRING_INTENT_KEY)
    //    }
    //}

    private val albumRVAdapter = AlbumRVAdapter(ArrayList())

    companion object {
        const val STRING_INTENT_KEY = "my_string_key"
    }

    //
    private val getResultText = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultString = result.data?.getStringExtra(SongActivity.STRING_INTENT_KEY)
            Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateMiniPlayer(album: Album) {
        binding?.let {
            it.mainMiniplayerTitleTv.text = album.title
            it.mainMiniplayerSingerTv.text = album.singer

            // 여기에 미니플레이어 업데이트 관련 로직을 추가하세요.
            // 예를 들어, 미니플레이어의 이미지 업데이트 등을 수행할 수 있습니다.
        }
        //binding?.mainMiniplayerTitleTv?.text = album.title
        //binding?.mainMiniplayerSingerTv?.text = album.singer
        //Song( album.title, album.singer, 0, 60, false, "flo_music")

        // 미니플레이어 업데이트 관련 로직 추가

        // 아래의 예시는 미니플레이어에 앨범 이미지 업데이트 예시
        // binding.mainMiniplayerCoverImg.setImageResource(album.coverImg!!)
    }

//

    lateinit var binding: ActivityMainBinding

    private var song: Song=Song()
    private var gson: Gson=Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val song= Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), 0, 60, false, "flo_music")

        //binding.mainPlayerCl.setOnClickListener {
            //startActivity(Intent(this, SongActivity::class.java))
        //    val intent=Intent(this,SongActivity::class.java)
        //    intent.putExtra("title", song.title)
        //    intent.putExtra("singer", song.singer)
        //    startActivity(intent)
        //}

        //

//        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {
//            override fun onItemClick(album: Album) {
//                // 앨범을 클릭한 경우 처리
//            }
//
//            override fun onRemoveAlbum(position: Int) {
//                // 앨범을 제거한 경우 처리
//            }
//
//            override fun onPlayButtonClick(album: Album) {
//                updateMiniPlayer(album)
//            }
//        })


        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            getResultText.launch(intent) // SongActivity 호출
        }


        //?
        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
            startActivity(intent)
            getResultText.launch(intent) // SongActivity 호출
        }

        if (intent.hasExtra("title")&&intent.hasExtra("singer")) {
            binding.mainMiniplayerTitleTv.text=intent.getStringExtra("title")
            binding.mainMiniplayerSingerTv.text=intent.getStringExtra("singer")
        }

        initBottomNavigation()

        Log.d("Song", song.title + song.singer)

    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text=song.title
        binding.mainMiniplayerSingerTv.text=song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson  = sharedPreferences.getString("songData",null)

        song= if(songJson==null) {
            Song("라일락", "아이유(IU)", 0, 60, false, "flo_music")
        } else {
            gson.fromJson(songJson, Song::class.java)
        }

        setMiniPlayer(song)


    }
}

