package org.example.project.core.presentation

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.error_disk_full
import kotlinproject.composeapp.generated.resources.error_no_internet
import kotlinproject.composeapp.generated.resources.error_request_timeout
import kotlinproject.composeapp.generated.resources.error_serialization
import kotlinproject.composeapp.generated.resources.error_too_many_requests
import kotlinproject.composeapp.generated.resources.error_unknown
import kotlinproject.composeapp.generated.resources.server_error
import org.example.project.core.domain.DataError

fun DataError.toUIText() : UiText {
    val stringRes = when(this){
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.SERVER_ERROR -> Res.string.server_error
        DataError.Remote.SERIALIZATION_ERROR -> Res.string.error_serialization
        DataError.Remote.UNKNOWN_ERROR -> Res.string.error_unknown
    }

    return UiText.StringResourceId(stringRes)
}