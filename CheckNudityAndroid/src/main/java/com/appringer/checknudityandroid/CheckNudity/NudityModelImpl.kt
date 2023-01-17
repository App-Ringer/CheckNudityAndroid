package com.appringer.checknudityandroid.CheckNudity

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.appringer.checknudityandroid.enums.SecurityLevelEnum
import com.appringer.checknudityandroid.Util.VideoUtil
import com.appringer.checknudityandroid.bean.Score
import com.appringer.checknudityandroid.bean.average
import com.appringer.checknudityandroid.bean.toScore
import io.github.devzwy.nsfw.NSFWHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object NudityModelImpl : NudityModelProvider {
    override fun init(context: Context, numThreads: Int, isOpenGPu: Boolean) {
        NSFWHelper.openDebugLog()
        NSFWHelper.initHelper(
            context = context,
            modelPath = null,
            isOpenGPU = isOpenGPu,
            numThreads = numThreads
        )
    }

    override fun checkNudity(bitmap: Bitmap, score: (Score) -> Unit) {
        NSFWHelper.getNSFWScore(bitmap) {
            score(it.toScore(bitmap))
        }
    }

    override fun checkNudity(
        context: Context,
        video: Uri,
        interval: SecurityLevelEnum,
        score: (scorePerFrame:ArrayList<Score>,averageScore:Score) -> Unit
    ) {
        val bitmaps = VideoUtil.getFrame(context, video, interval)
        checkNudity(bitmaps, interval, score)
    }

    override fun checkNudity(
        bitmaps: ArrayList<Bitmap>,
        interval: SecurityLevelEnum,
        score: (scorePerFrame:ArrayList<Score>,averageScore:Score) -> Unit
    ) {
        val result = arrayListOf<Score>()
        bitmaps.forEachIndexed { index, bitmap ->
            runBlocking {
                withContext(Dispatchers.IO) {
                    NSFWHelper.getNSFWScore(bitmap) {
                        result.add(it.toScore(bitmap))
                        if (index >= bitmaps.size - 1) {
                            score(result,result.average())
                        }
                    }
                }
            }
        }
    }

}