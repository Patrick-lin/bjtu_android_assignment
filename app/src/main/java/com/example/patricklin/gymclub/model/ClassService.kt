package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData

interface ClassService {
    fun getClasses(): LiveData<List<Class>>

    fun getLiveClass(id: String): LiveData<Class>
    fun getClass(id: String): Class?
}