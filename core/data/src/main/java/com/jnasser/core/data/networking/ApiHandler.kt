package com.jnasser.core.data.networking

import com.jnasser.core.domain.util.error_handler.DataError
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException
import com.jnasser.core.domain.util.result_handler.Result

suspend fun <T> safeCall(
    call: suspend  () -> Response<T>
): Result<T, DataError.Network> {
    val response = try {
        call()
    } catch (e: UnknownHostException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: SocketTimeoutException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.REQUEST_TIMEOUT)
    } catch (e: Exception) {
        if(e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    }

    return responseToResult(response)
}

private fun <T> responseToResult(
    response: Response<T>
): Result<T, DataError.Network> {
    return when {
        response.isSuccessful -> {
            val body = response.body()

            if (body != null) {
                Result.Success(body)
            } else {
                Result.Error(DataError.Network.SERIALIZATION)
            }
        }

        response.code() == 404 -> {
            Result.Error(DataError.Network.NOT_FOUND)
        }

        response.code() == 429 -> {
            Result.Error(DataError.Network.TOO_MANY_REQUEST)
        }

        response.code() in 500..599 -> {
            Result.Error(DataError.Network.SERVER_ERROR)
        }

        else -> {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}