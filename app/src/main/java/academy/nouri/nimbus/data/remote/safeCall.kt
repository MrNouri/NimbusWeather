package academy.nouri.nimbus.data.remote

import academy.nouri.nimbus.domain.utils.DataError
import academy.nouri.nimbus.domain.utils.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import java.net.UnknownHostException

suspend inline fun <reified T> safeCall(execute: suspend () -> HttpResponse): Result<T, DataError> {
    val response = try {
        execute.invoke()
    } catch (_: UnknownHostException) {
        return Result.Failure(DataError.NoInternet)
    } catch (_: UnresolvedAddressException) {
        return Result.Failure(DataError.NoInternet)
    } catch (_: SocketTimeoutException) {
        return Result.Failure(DataError.RequestTimeout)
    } catch (e: Exception) {
        return Result.Failure(DataError.Unknown(e.message))
    }

    return when (response.status.value) {
        in 200..299 -> {
            try {
                val response = response.body<T>()
                Result.Success(data = response)
            } catch (_: JsonConvertException) {
                Result.Failure(DataError.Serialization)
            } catch (_: NoTransformationFoundException) {
                Result.Failure(DataError.Serialization)
            } catch (e: Exception) {
                return Result.Failure(DataError.Unknown(e.message))
            }
        }

        401 -> Result.Failure(DataError.NoAuthorization)
        408 -> Result.Failure(DataError.RequestTimeout)
        429 -> Result.Failure(DataError.TooManyRequest)
        in 500..599 -> Result.Failure(DataError.Server)
        else -> Result.Failure(DataError.Unknown())
    }
}