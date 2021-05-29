package com.capstone.saba

import android.app.Application
import com.capstone.saba.di.AppComponent
import com.capstone.saba.di.CoreComponent
import com.capstone.saba.di.DaggerAppComponent
import com.capstone.saba.di.DaggerCoreComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}