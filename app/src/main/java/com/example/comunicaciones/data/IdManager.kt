package com.example.comunicaciones.data

import java.util.UUID

object IdManager {

    private var generatedId: String? = null

    fun generateIdIfNeeded(){
        if (generatedId == null){
            generatedId = UUID.randomUUID().toString()
        }
    }
    fun getGeneratedId(): String = UUID.randomUUID().toString()
}