
package com.toggl.models.validation

sealed class Password(val password: String) {
    class Invalid(password: String) : Password(password)
    class Valid private constructor(password: String) : Password(password) {
        companion object {
            fun from(password: String) =
                if (password.isBlank() || password.length < 6) Invalid(password)
                else Valid(password)
        }
    }

    override fun toString(): String = password

    companion object {
        fun from(password: String) =
            Valid.from(password)
    }
}

fun String.toPassword() =
    Password.from(this)
