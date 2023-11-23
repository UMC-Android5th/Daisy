package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemAlbumBinding
import java.util.ArrayList

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun  onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
        fun onPlayButtonClick(album: Album){

        }
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun  setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener=itemClickListener
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{mItemClickListener.onItemClick(albumList[position])}
        //holder.binding.homePannelAlbumTodayTitleTv.setOnClickListener { mItemClickListener.onRemoveAlbum(position) }

        holder.binding.itemAlbumPlayImgIv.setOnClickListener {
            mItemClickListener.onPlayButtonClick(albumList[position])
        }
    }

    override fun getItemCount(): Int = albumList.size
    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImage!!)

            binding.itemAlbumPlayImgIv.setOnClickListener{
                mItemClickListener.onPlayButtonClick(album)
            }

            // 아래 코드를 추가하여 cover 이미지를 클릭했을 때 앨범 프래그먼트로 이동
            binding.itemAlbumCoverImgCardView.setOnClickListener {
                mItemClickListener.onItemClick(album)
            }
        }




    }

}