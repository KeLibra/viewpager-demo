package cn.kelibra.demo.viewpager.adapter

import ItemTabViewModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cn.kelibra.demo.viewpager.R

class TabAdapter(private val context: Context, private val viewModel: ItemTabViewModel) : RecyclerView.Adapter<TabAdapter.TabViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): TabViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_mutable,
            parent, false)
        return TabViewHolder(v)
    }

    override fun getItemCount(): Int = if(viewModel.size > 0) viewModel.size else 0

    override fun getItemId(position: Int): Long = viewModel.itemId(position)

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        val itemId = holder.itemId
        val bg = viewModel.itemId(position).toInt()

        holder.bind(viewModel.getItemById(itemId), bg)
    }

    inner class TabViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val bgColor: FrameLayout = view.findViewById(R.id.bgItem)
        private val title: AppCompatTextView = view.findViewById(R.id.titleTab)

        fun bind(item: String, bg: Int) {
            if(bg % 2 == 0) {
                bgColor.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
            } else {
                bgColor.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_light))
            }
            title.text = item
        }
    }
}

