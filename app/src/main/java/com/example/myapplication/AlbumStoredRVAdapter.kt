package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemStoredalbumBinding
import java.util.ArrayList

class AlbumStoredRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumStoredRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun  onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)

        fun onPlayButtonClick(album: Album)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun  setMyItemClickListener(itemColickListener: MyItemClickListener) {
        mItemClickListener=itemColickListener
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumStoredRVAdapter.ViewHolder {
        val binding: ItemStoredalbumBinding = ItemStoredalbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumStoredRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{mItemClickListener.onItemClick(albumList[position])}
        //holder.binding.homePannelAlbumTodayTitleTv.setOnClickListener { mItemClickListener.onRemoveAlbum(position) }

    }

    override fun getItemCount(): Int = albumList.size
    inner class ViewHolder(val binding: ItemStoredalbumBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.storedItemAlbumTitleTv.text = album.title
            binding.storedItemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)

            binding.itemAlbumPlayImgIv.setOnClickListener{
                mItemClickListener.onPlayButtonClick(album)
            }
        }




    }

}