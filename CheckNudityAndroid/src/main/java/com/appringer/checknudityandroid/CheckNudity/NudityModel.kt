package com.appringer.checknudityandroid.CheckNudity

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.appringer.checknudityandroid.enums.SecurityLevelEnum
import com.appringer.checknudityandroid.bean.Score

object NudityModel : NudityModelProvider {
    override fun init(context: Context, numThreads: Int, isOpenGPU: Boolean) {
        NudityModelImpl.init(context, numThreads)
    }

    override fun checkNudity(bitmap: Bitmap, score: (Score) -> Unit) {
        NudityModelImpl.checkNudity(bitmap, score)
    }

    override fun checkNudity(
        context: Context,
        video: Uri,
        interval: SecurityLevelEnum,
        score: (scorePerFrame:ArrayList<Score>,averageScore:Score) -> Unit
    ) {
        NudityModelImpl.checkNudity(
            context,
            video,
            interval,
            score
        )
    }

    override fun checkNudity(
        bitmaps: ArrayList<Bitmap>,
        interval: SecurityLevelEnum,
        score: (scorePerFrame:ArrayList<Score>,averageScore:Score) -> Unit
    ) {
        NudityModelImpl.checkNudity(
            bitmaps,
            interval,
            score
        )
    }




}