package com.appringer.checknudityandroid.Util

import android.graphics.Color
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.appringer.checknudityandroid.R
import com.appringer.checknudityandroid.bean.Score

fun TextView.showNSFWScore(score: Score){
    this.text =
        "nsfw:${score.nsfwScore}\nsfw:${score.sfwScore}\ntime: ${score.scanTime} ms"
    if (score.nsfwScore > 0.7) {
        this.setBackgroundColor(Color.parseColor("#C1FF0000"))
    } else if (score.nsfwScore > 0.5) {
        this.setBackgroundColor(Color.parseColor("#C1FF9800"))
    } else {
        this.setBackgroundColor(Color.parseColor("#803700B3"))
    }
}

fun ImageView.setCensoredBitmap(score: Score){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.foreground =
            if (score.nsfwScore > 0.5) {
                ContextCompat.getDrawable(context, R.drawable.fg_censor)
            } else null
    }

    this.setImageBitmap(score.bitmap)
}