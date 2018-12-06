package com.example.patricklin.gymclub.model.session

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import com.example.patricklin.gymclub.model.AuthService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SessionServiceImpl(private val sessionApi: SessionApi, private val authService: AuthService) : SessionService {
    private val classes = MutableLiveData<List<Session>>()

    override fun getSessions(): LiveData<List<Session>> {
        GlobalScope.launch {
            try {
                val res = sessionApi.getSessions(authService.getAuthHeader()).await()
                classes.postValue(res.list)
            } catch (err: Throwable) {
                Log.d("sessionApi", "$err")
            }
        }
        return classes
    }

    override fun getLiveSession(id: String): LiveData<Session> = Transformations.map(classes) {
        it.find { c -> c.id == id }
    }

    override fun getSession(id: String): Session? = classes.value.orEmpty().find { c -> c.id == id }
}