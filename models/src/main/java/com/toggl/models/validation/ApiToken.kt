package com.toggl.models.validation

sealed class ApiToken {
    class Valid private constructor(val apiToken: String) : ApiToken() {
        companion object {
            fun from(apiToken: String) =
                if (apiToken.isBlank() || apiToken.length != 32) Invalid
                else Valid(apiToken)
        }
    }

    object Invalid : ApiToken()

    companion object {
        fun from(apiToken: String) =
            Valid.from(apiToken)
    }
}

fun String.toApiToken() =
    ApiToken.from(this)
