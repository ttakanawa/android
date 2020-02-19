package com.toggl.models.validation

sealed class Email {
    class Valid private constructor(val email: String) : Email() {
        companion object {
            fun from(email: String) =
                if (email.isBlank() ) Invalid
                else Valid(email)
        }
    }
    object Invalid : Email()

    companion object {
        fun from(email: String) = Valid.from(email)
    }
}

fun Email.validEmailOrNull() : Email.Valid? =
    when(this) {
        is Email.Valid -> this
        Email.Invalid -> null
    }

fun String.toEmail() = Email.from(this)