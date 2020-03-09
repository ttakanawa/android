
package com.toggl.models.validation

sealed class Email(val email: String) {
    class Valid private constructor(email: String) : Email(email) {
        companion object {
            fun from(email: String) =
                if (email.isBlank()) Invalid(email)
                else Valid(email)
        }
    }
    class Invalid(email: String) : Email(email)

    override fun toString(): String = email

    companion object {
        fun from(email: String) = Valid.from(email)
    }
}

fun String.toEmail() = Email.from(this)
