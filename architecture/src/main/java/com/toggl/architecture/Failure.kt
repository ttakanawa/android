package com.toggl.architecture

data class Failure(val throwable: Throwable, val errorMessage: String)
