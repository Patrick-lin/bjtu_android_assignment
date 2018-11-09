package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData

interface ClassService {
    fun getClasses(): LiveData<List<Class>>
    fun getClass(id: Int): LiveData<Class>
}