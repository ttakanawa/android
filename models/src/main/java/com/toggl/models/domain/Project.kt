package com.toggl.models.domain

data class Project(
    val id: Long,
    val name: String,
    val color: String,
    val active: Boolean,
    val isPrivate: Boolean,
    val billable: Boolean?,
    val clientId: Long?
)