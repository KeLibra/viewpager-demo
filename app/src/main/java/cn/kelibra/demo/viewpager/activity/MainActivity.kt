package cn.kelibra.demo.viewpager.activity

import ItemTabViewModel
import android.annotation.SuppressLint
import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import android.gesture.GestureOverlayView.ORIENTATION_VERTICAL
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cn.kelibra.demo.viewpager.R
import cn.kelibra.demo.viewpager.adapter.TabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.control_layout.*

class MainActivity : AppCompatActivity() {

    private var isHorizontal: Boolean = true
    private val viewModel: ItemTabViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2.adapter = TabAdapter(this, viewModel).apply { setHasStableIds(true) }

        TabLayoutMediator(tabLayout2, viewPager2) { tab, position ->
            tab.text = viewModel.items[position]
        }.attach()

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            viewPager2.apply {
                layoutDirection = View.LAYOUT_DIRECTION_RTL
                currentItem = viewModel.size
            }
        }

        changeOrientation()
        addPage()
        removePage()
    }

    @SuppressLint("WrongConstant")
    private fun changeOrientation() {
        btnOrientation.setOnClickListener {
            when {
                isHorizontal -> {
                    viewPager2.orientation = ORIENTATION_VERTICAL
                    isHorizontal = false
                    btnOrientation.text = "纵向滑动"
                }
                else -> {
                    viewPager2.orientation = ORIENTATION_HORIZONTAL
                    isHorizontal = true
                    btnOrientation.text = "横向滑动"
                }
            }
        }
    }

    private fun addPage() {
        btnAddPage.setOnClickListener {
            changeItem { viewModel.addNewItem(viewPager2.currentItem + 1) }
        }
    }

    private fun removePage() {
        btnRemovePage.setOnClickListener {
            changeItem { viewModel.removeItem(viewPager2.currentItem) }
        }
    }

    private fun changeItem(performChanges: () -> Unit) {
        val oldPosition = viewPager2.currentItem
        val currentItemId = viewModel.itemId(oldPosition)
        performChanges()
        if (viewModel.contains(currentItemId)) {
            val newPosition = (0 until viewModel.size).indexOfLast {
                viewModel.itemId(it) == currentItemId
            }
            viewPager2.apply {
                adapter?.notifyDataSetChanged()
                currentItem = newPosition + 1
            }
        } else {
            viewPager2.apply {
                currentItem = oldPosition - 1
                adapter?.notifyDataSetChanged()

            }
        }
    }
}
