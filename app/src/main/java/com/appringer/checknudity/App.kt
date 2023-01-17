package com.appringer.checknudity

import android.app.Application
import com.appringer.checknudityandroid.CheckNudity.NudityModel

class App: Application() {
    override fun onCreate() {
        super.onCreate()

       NudityModel.init(
            context = this,
            numThreads = 4
        )

    }
}