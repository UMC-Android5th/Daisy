
package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySongBinding
import androidx.activity.result.ActivityResult
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.flo.SongDatabase
import com.google.gson.Gson


class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    lateinit var timer: Timer
    lateinit var song: Song
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPlayList()
        //initSong()
        initClickListener()
    }

    fun customToastView(text: String) {
        val inflater: LayoutInflater = layoutInflater
        val layout: View = inflater.inflate(R.layout.custom_toast, findViewById<ViewGroup>(R.id.toast_layout_root))
        val textView: TextView = layout.findViewById(R.id.textboard)
        textView.text = text

        val toastView: Toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toastView.setGravity(Gravity.BOTTOM, 0, 30)
        toastView.view = layout
        toastView.show()
    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun initClickListener(){
        binding.btnDownIv.setOnClickListener {
            finish()
        }

        binding.nuguBtnPlayIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.nuguBtnPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.nuguBtnSkipNextIv.setOnClickListener {
            moveSong(+1)
        }

        binding.nuguBtnSkipPreviousIv.setOnClickListener {
            moveSong(-1)
        }
        binding.icMyLikeOffIv.setOnClickListener {
            customToastView("Like Click")
            setLike(songs[nowPos].isLike)
        }
    }
    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if (!isLike){
            binding.icMyLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.icMyLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }

    }
//    private fun moveSong(direct: Int){
//        if (nowPos + direct < 0){
//            Toast.makeText(this,"first song",Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if (nowPos + direct >= songs.size){
//            Toast.makeText(this,"last song",Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        nowPos += direct
//
//        timer.interrupt()
//        startSong()
//
//        mediaPlayer?.release()
//        mediaPlayer = null
//
//        setPlayer(songs[nowPos])
//    }

    private fun moveSong(direct: Int) { // direct는 +1 또는 -1임
        if (nowPos + direct < 0) {
            Toast.makeText(this,"first song",Toast.LENGTH_SHORT).show()
        }

        else if (nowPos + direct >= songs.size){
            Toast.makeText(this,"last song",Toast.LENGTH_SHORT).show()
        }

        else {
            nowPos += direct
            timer.interrupt()
            startSong()

            mediaPlayer?.release()
            mediaPlayer = null

            setPlayer(songs[nowPos])
        }
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }
    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID",songs[nowPos].id.toString())

        startSong()
        setPlayer(songs[nowPos])
    }
    private fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song:Song){
        //binding.albumTitleTv.text=intent.getStringExtra("title")!!
        //binding.albumSingerTv.text=intent.getStringExtra("singer")!!
        binding.albumTitleTv.text=song.title
        binding.albumSingerTv.text=song.singer
        binding.songPlayingStartTv.text=String.format("%02d:%02d", song.second/60, song.second %60)
        binding.songPlayingEndTv.text=String.format("%02d:%02d", song.playTime/60, song.playTime %60)
        binding.imgAlbumLilacIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)


        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer=MediaPlayer.create(this, music)

        if (song.isLike){
            binding.icMyLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.icMyLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying) {
            binding.nuguBtnPlayIv.visibility= View.VISIBLE
            binding.nuguBtnPauseIv.visibility= View.GONE
            mediaPlayer?.start()
        }
        else {
            binding.nuguBtnPlayIv.visibility= View.GONE
            binding.nuguBtnPauseIv.visibility= View.VISIBLE
            if (mediaPlayer?.isPlaying==true) {
                mediaPlayer?.pause()
            }
        }
    }
    private fun startSong(){
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
        timer.start()
    }
    inner class Timer(private val playTime: Int,var isPlaying: Boolean = true):Thread(){
        private var second : Int = 0
        private var mills: Float = 0f
        override fun run() {
            super.run()
            try {
                while (true){
                    if (second >= playTime){
                        break
                    }
                    if (isPlaying){
                        sleep(10)
                        mills += 10
                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }
                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songPlayingStartTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("Song","Thread is gone: ${e.message}")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("songId",songs[nowPos].id)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
