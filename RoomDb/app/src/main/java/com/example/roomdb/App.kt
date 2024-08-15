package com.example.roomdb

import android.app.Application
import com.example.roomdb.data.MainDb

class App : Application () {
    val database by lazy { MainDb.createDataBase(this) }
}
