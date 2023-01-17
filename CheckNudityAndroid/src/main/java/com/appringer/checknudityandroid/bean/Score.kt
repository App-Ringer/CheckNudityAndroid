package com.appringer.checknudityandroid.bean

import android.graphics.Bitmap
import io.github.devzwy.nsfw.NSFWScoreBean

data class Score(
    val nsfwScore: Float,
    val sfwScore: Float,
    val loadTime: Long,
    val scanTime: Long,
    val bitmap: Bitmap?
)

fun NSFWScoreBean.toScore(bitmap: Bitmap) = Score(
    nsfwScore = nsfwScore,
    sfwScore = sfwScore,
    loadTime = timeConsumingToLoadData,
    scanTime = timeConsumingToScanData,
    bitmap = bitmap
)

fun ArrayList<Score>.average(): Score {
    return Score(
        nsfwScore = this.map { it.nsfwScore }.average().toFloat(),
        sfwScore = this.map { it.sfwScore }.average().toFloat(),
        loadTime = this.map { it.loadTime }.average().toLong(),
        scanTime = this.map { it.scanTime }.average().toLong(),
        bitmap = null
    )
}