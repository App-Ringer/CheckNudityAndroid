package com.appringer.checknudity

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.appringer.checknudityandroid.R
import com.appringer.checknudityandroid.Util.setCensoredBitmap
import com.appringer.checknudityandroid.Util.showNSFWScore
import com.appringer.checknudityandroid.bean.Score
import com.appringer.checknudity.databinding.ImgItemBinding

class TimelineAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private val items: MutableList<Score> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ImgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(newItems: List<Score>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }
}

class MyViewHolder(private val binding: ImgItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Score) {
        binding.invalidateAll()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.iv.foreground =
                if (item.nsfwScore < 0.5) {
                    binding.iv.setImageBitmap(item.bitmap)
                    ContextCompat.getDrawable(binding.root.context, R.drawable.fg_censor)
                } else {
                    binding.iv.setImageBitmap(null)
                    null
                }
        }



        binding.tvFrame.text = "${adapterPosition + 1}"
        binding.tvScore.showNSFWScore(item)
        binding.executePendingBindings()
    }

    fun clear() {
        binding.unbind()
    }
}