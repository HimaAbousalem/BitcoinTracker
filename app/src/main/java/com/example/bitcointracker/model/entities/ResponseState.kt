package com.example.bitcointracker.model.entities

sealed class ResponseState
object Loading: ResponseState()
data class Success(internal val data: List<PopulateData>): ResponseState()
data class Error(val msg: String): ResponseState()
