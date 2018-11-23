package com.example.patricklin.gymclub.model.session

import android.arch.lifecycle.LiveData
import com.example.patricklin.gymclub.model.session.Class

interface ClassService {
    fun getClasses(): LiveData<List<Class>>

    fun getLiveClass(id: String): LiveData<Class>
    fun getClass(id: String): Class?
}