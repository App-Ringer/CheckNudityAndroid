package com.appringer.checknudityandroid.CheckNudity

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.appringer.checknudityandroid.enums.SecurityLevelEnum
import com.appringer.checknudityandroid.bean.Score

interface NudityModelProvider {
    fun init(context: Context, numThreads: Int,isOpenGPu:Boolean = true)
    fun checkNudity(bitmap: Bitmap, score:(Score)->Unit)
    fun checkNudity(context: Context, video:Uri, interval: SecurityLevelEnum, score:(scorePerFrames:ArrayList<Score>, averageScore:Score)->Unit)
    fun checkNudity(bitmaps:ArrayList<Bitmap>, interval: SecurityLevelEnum, score:(scorePerFrames:ArrayList<Score>, averageScore:Score)->Unit)
}