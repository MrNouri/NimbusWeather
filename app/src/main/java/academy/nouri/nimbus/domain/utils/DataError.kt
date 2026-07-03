package academy.nouri.nimbus.domain.utils

interface DataError : Error {
    data object RequestTimeout : DataError
    data object TooManyRequest : DataError
    data object NoInternet : DataError
    data object Server : DataError
    data object Serialization : DataError
    data object NoAuthorization: DataError
    data object NotFoundAnyData: DataError
    data class Unknown(val message: String? = null) : DataError
}