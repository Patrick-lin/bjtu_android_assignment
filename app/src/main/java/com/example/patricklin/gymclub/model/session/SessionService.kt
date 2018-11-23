package com.example.patricklin.gymclub.model.session

import android.arch.lifecycle.LiveData

interface SessionService {
    fun getSessions(): LiveData<List<Session>>

    fun getLiveSession(id: String): LiveData<Session>
    fun getSession(id: String): Session?
}