package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ClassServiceImpl(private val classApi: ClassApi, private val authService: AuthService) : ClassService {
    private val classes = MutableLiveData<List<Class>>()

    override fun getClasses(): LiveData<List<Class>> {
        GlobalScope.launch {
            try {
                val res = classApi.getClasses(authService.getAuthHeader()).await()
                Log.i("test", "update classes from api")
                classes.postValue(res.list)
            } catch (err: Throwable) {
                Log.d("classApi", "$err")
            }
        }
        return classes
    }

    override fun getLiveClass(id: String): LiveData<Class> = Transformations.map(classes) {
        it.find { c -> c.id == id }
    }

    override fun getClass(id: String): Class? = classes.value.orEmpty().find { c -> c.id == id }
}