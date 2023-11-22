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
import com.example.flo.SongDatabase
import com.google.gson.Gson
/*
class MainActivity : AppCompatActivity(){

//    override fun onItemClick(album: Album) {
//        // 앨범을 클릭한 경우 처리
//    }
//
//    override fun onRemoveAlbum(position: Int) {
//        // 앨범을 제거한 경우 처리
//    }
//
//    override fun onPlayButtonClick(album: Album) {
//        updateMiniPlayer(album)
//    }

    //companion object { const val STRING_INTENT_KEY = "my_string_key"}

    //private val getResultText = registerForActivityResult(
    //    ActivityResultContracts.StartActivityForResult()
    //) { result ->
    //    if (result.resultCode == Activity.RESULT_OK) {
    //        val resultString = result.data?.getStringExtra(STRING_INTENT_KEY)
    //    }
    //}

    //private val albumRVAdapter = AlbumRVAdapter(ArrayList())

//    companion object {
//        const val STRING_INTENT_KEY = "my_string_key"
//    }
//
//    //
//    private val getResultText = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result: ActivityResult ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val resultString = result.data?.getStringExtra(SongActivity.STRING_INTENT_KEY)
//            Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show()
//        }
//    }


//    private fun updateMiniPlayer(album: Album) {
//        binding?.let {
//            it.mainMiniplayerTitleTv.text = album.title
//            it.mainMiniplayerSingerTv.text = album.singer
//
//            // 여기에 미니플레이어 업데이트 관련 로직을 추가하세요.
//            // 예를 들어, 미니플레이어의 이미지 업데이트 등을 수행할 수 있습니다.
//        }
        //binding?.mainMiniplayerTitleTv?.text = album.title
        //binding?.mainMiniplayerSingerTv?.text = album.singer
        //Song( album.title, album.singer, 0, 60, false, "flo_music")

        // 미니플레이어 업데이트 관련 로직 추가

        // 아래의 예시는 미니플레이어에 앨범 이미지 업데이트 예시
        // binding.mainMiniplayerCoverImg.setImageResource(album.coverImg!!)
//}

//

    private lateinit var binding: ActivityMainBinding

    private var song: Song=Song()
    private var gson: Gson=Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
        inputDummySongs()

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
    //        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
    //        val songJson  = sharedPreferences.getString("songData",null)
    //
    //        song= if(songJson==null) {
    //            Song("라일락", "아이유(IU)", 0, 60, false, "flo_music")
    //        } else {
    //            gson.fromJson(songJson, Song::class.java)
    //        }
    //
    //        setMiniPlayer(song)

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0){
            songDB.songDao().getSong(1)
        } else{
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)


    }



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


//        binding.mainPlayerCl.setOnClickListener {
//            val intent = Intent(this, SongActivity::class.java)
//            getResultText.launch(intent) // SongActivity 호출
//        }
//        binding.mainPlayerCl.setOnClickListener {
//            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
//            editor.putInt("songId",song.id)
//            editor.apply()
//
//            val intent = Intent(this,SongActivity::class.java)
//            startActivity(intent)
//            super.onStart()
//            val spf = getSharedPreferences("song", MODE_PRIVATE)
//            val songId = spf.getInt("songId",0)
//
//            val songDB = SongDatabase.getInstance(this)!!
//
//            song = if (songId == 0){
//                songDB.songDao().getSong(1)
//            } else{
//                songDB.songDao().getSong(songId)
//            }
//
//            Log.d("song ID", song.id.toString())
//            setMiniPlayer(song)
//        }


        //?
//        binding.mainPlayerCl.setOnClickListener {
//            val intent = Intent(this, SongActivity::class.java)
//            intent.putExtra("title", song.title)
//            intent.putExtra("singer", song.singer)
//            intent.putExtra("second", song.second)
//            intent.putExtra("playTime", song.playTime)
//            intent.putExtra("isPlaying", song.isPlaying)
//            intent.putExtra("music", song.music)
//            startActivity(intent)
//            getResultText.launch(intent) // SongActivity 호출
//        }

//        if (intent.hasExtra("title")&&intent.hasExtra("singer")) {
//            binding.mainMiniplayerTitleTv.text=intent.getStringExtra("title")
//            binding.mainMiniplayerSingerTv.text=intent.getStringExtra("singer")
//        }

//        Log.d("Song", song.title + song.singer)
//
//    }

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





    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_flu",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_next",
                R.drawable.img_album_exp3,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "music_boy",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp4,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

    //ROOM_DB
}

*/


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var gson: Gson = Gson()
    private var song: Song = Song()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        initBottomNavigation()
        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId",song.id)
            editor.apply()

            val intent = Intent(this,SongActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initBottomNavigation(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commit()
        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    val homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, homeFragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    val lookFragment = LookFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, lookFragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    val searchFragment = SearchFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, searchFragment)
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    val lockerFragment = LockerFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, lockerFragment)
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
    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_flu",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_next",
                R.drawable.img_album_exp3,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "music_boy",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp4,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }
    override fun onStart() {
        super.onStart()
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0){
            songDB.songDao().getSong(1)
        } else{
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)
    }
}

