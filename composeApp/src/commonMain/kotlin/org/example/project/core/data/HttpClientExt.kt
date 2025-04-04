package org.example.project.core.data

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import okio.IOException
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result
import kotlin.coroutines.coroutineContext


suspend inline fun <reified T> request(
    execute: () -> HttpResponse
) : Result<T,DataError.Remote>{
    val response = try{
        execute()
    }catch (e : SocketTimeoutException){
        return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    }catch (e : UnresolvedAddressException){
        return Result.Error(DataError.Remote.NO_INTERNET)
    }catch (e : IOException){
        return Result.Error(DataError.Remote.NO_INTERNET)
    }
    catch (e : Exception){
        coroutineContext.ensureActive()
        return Result.Error(DataError.Remote.UNKNOWN_ERROR)
    }

    return responseToResult(response)
}
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(DataError.Remote.SERIALIZATION_ERROR)
            }
        }

        in 500..599 -> Result.Error(DataError.Remote.SERVER_ERROR)
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        else -> Result.Error(DataError.Remote.UNKNOWN_ERROR)
    }
}