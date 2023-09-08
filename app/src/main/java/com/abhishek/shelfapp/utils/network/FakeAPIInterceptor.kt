package com.abhishek.shelfapp.utils.network

import android.content.Context
import androidx.annotation.RawRes
import com.abhishek.shelfapp.R
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.BufferedReader
import java.io.IOException

/**
 * FakeAPIInterceptor is used to return the dummy data when the EndPoint or the API is not ready from the backend side.
 * Use this in Debug mode only. When done with the testing, remove the json file from raw directory
 */

class FakeAPIInterceptor(private val context: Context) : Interceptor {

    private val contentType = "application/json"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        when {
            uri.contains("test/fetch/book/list") -> {
                // usage example: return getResponse(chain, R.raw.demo)
                return return getResponse(chain, R.raw.demo)
            }
        }
        return chain.proceed(chain.request())
    }

    /**
     * @param chain Interceptor chain
     * @param resId dummy json file
     * @return dummy response corresponding to the API
     */
    fun getResponse(chain: Interceptor.Chain, @RawRes resId: Int): Response {
        val jsonString = this.context.resources
            .openRawResource(resId)
            .bufferedReader()
            .use(BufferedReader::readText)
        val builder = Response.Builder()
        return builder.request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .addHeader("content-type", contentType)
            .body(jsonString.toByteArray().toResponseBody(contentType.toMediaTypeOrNull()))
            .code(200)
            .message(jsonString)
            .build()
    }

}