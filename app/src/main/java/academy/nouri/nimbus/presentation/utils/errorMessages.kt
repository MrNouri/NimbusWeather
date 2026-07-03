package academy.nouri.nimbus.presentation.utils

import academy.nouri.nimbus.domain.utils.DataError

fun DataError.errorMessage() = when (this) {
    is DataError.NoInternet -> "No internet connection"
    is DataError.RequestTimeout -> "Request timeout, try again later"
    is DataError.Serialization -> "Failed to process data."
    is DataError.Server -> "Server error occurred."
    is DataError.TooManyRequest -> "Too many request."
    is DataError.NoAuthorization -> "Not authorization, Login again please!"
    is DataError.NotFoundAnyData -> "Not found any data yet!"
    is DataError.Unknown -> "An unknown error, message: ${this.message}"
    else -> ""
}