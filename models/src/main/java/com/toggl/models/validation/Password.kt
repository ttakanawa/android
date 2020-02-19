package com.toggl.models.validation

sealed class Password {
    class Valid private constructor(val password: String) : Password() {
        companion object {
            fun from(password: String) =
                if (password.isBlank() || password.length < 6) Invalid
                else Valid(password)
        }
    }
    object Invalid : Password()

    companion object {
        fun from(password: String) =
            Valid.from(password)
    }
}

fun Password.validPasswordOrNull() : Password.Valid? =
    when(this) {
        is Password.Valid -> this
        Password.Invalid -> null
    }


fun String.toPassword() =
    Password.from(this)