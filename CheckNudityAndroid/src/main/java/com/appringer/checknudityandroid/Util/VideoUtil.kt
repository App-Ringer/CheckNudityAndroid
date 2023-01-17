package com.appringer.checknudityandroid.Util

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.appringer.checknudityandroid.enums.SecurityLevelEnum


object VideoUtil {

    internal fun getFrame(context: Context, mUri: Uri, interval: SecurityLevelEnum): ArrayList<Bitmap> {
        val intervalInMs:Long
        val frames = ArrayList<Bitmap>()
        var videoLength: Long
        try {
            videoLength = MediaMetadataRetriever().apply {
                setDataSource(context, mUri)
            }.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0L
            videoLength *= 1000
            intervalInMs = getIntervalInMicroSec(videoLength,interval)
        } catch (e: Exception) {
            println("Invalid URI: $mUri")
            return frames
        }
        var frameTime = 0L
        while (frameTime < videoLength) {
            val retriever = MediaMetadataRetriever().apply {
                setDataSource(context, mUri)
            }
            retriever.getFrameAtTime(
                (frameTime),
                MediaMetadataRetriever.OPTION_CLOSEST
            )?.let {
                frames.add(it)
            }
            frameTime += intervalInMs
            retriever.release()
        }
        return frames
    }

    private fun getIntervalInMicroSec(videoLength: Long, interval: SecurityLevelEnum): Long {
        if (interval.value.toMicroSecond() > videoLength){
            return when(interval){
                SecurityLevelEnum.LOW->{
                    if(SecurityLevelEnum.MEDIUM.value.toMicroSecond() < videoLength ){
                        SecurityLevelEnum.MEDIUM.value.toMicroSecond()
                    }else{
                        SecurityLevelEnum.HIGH.value.toMicroSecond()
                    }
                }
                else->{
                    SecurityLevelEnum.HIGH.value.toMicroSecond()
                }
            }
        }else{
            return interval.value.toMicroSecond()
        }
    }

    private fun Int.toMicroSecond() = this * 1_000_000L
}