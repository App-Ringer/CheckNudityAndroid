package com.appringer.checknudity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.appringer.checknudityandroid.CheckNudity.NudityModel
import com.appringer.checknudityandroid.enums.SecurityLevelEnum
import com.appringer.checknudityandroid.Util.setCensoredBitmap
import com.appringer.checknudityandroid.Util.showNSFWScore
import com.appringer.checknudity.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val REQUEST_CODE_VIDEO = 1
    private val timelineAdapter: TimelineAdapter = TimelineAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = timelineAdapter
        }
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            btn1.setOnClickListener {
                checkNSFW(resources.getDrawable(R.drawable.img).toBitmap())
            }
            btn2.setOnClickListener {
                checkNSFW(resources.getDrawable(R.drawable.img_anime).toBitmap())
            }
            btn3.setOnClickListener {
                checkNSFW(resources.getDrawable(R.drawable.img_doja).toBitmap())
            }
            vid.setOnClickListener {
                openVideoChooser()
            }
        }
    }

    fun openVideoChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "video/*"
        }
        startActivityForResult(intent, REQUEST_CODE_VIDEO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_VIDEO && resultCode == Activity.RESULT_OK && data != null) {
            data.data?.let { uri ->
                binding.flProgress.isVisible = true
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        NudityModel.checkNudity(this@MainActivity, uri, SecurityLevelEnum.LOW) { scorePerFrame, averageScore->
                            timelineAdapter.clearItems()
                            timelineAdapter.addItems(scorePerFrame)
                            binding.tvVideoScore.showNSFWScore(averageScore)
                            binding.flProgress.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun checkNSFW(bitmap: Bitmap) {
        binding.apply {
            flProgress.isVisible = true
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    NudityModel.checkNudity(bitmap) {
                        iv.setCensoredBitmap(it)
                        tv.showNSFWScore(it)
                        flProgress.isVisible = false
                    }
                }
            }
        }
    }

}