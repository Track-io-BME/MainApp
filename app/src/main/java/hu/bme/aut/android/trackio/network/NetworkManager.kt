package hu.bme.aut.android.trackio.network

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object NetworkManager {
    private val retrofit: Retrofit
    private val serverApi : RetrofitApi
    private const val SERVICE_URL = "https://23.97.188.188:443/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        serverApi = retrofit.create(RetrofitApi::class.java)
    }

    fun loginUser(mail : String, password : String): Call<String>{
        Log.d("baj van", serverApi.login(mail,password).toString())
        return serverApi.login(mail,password)
    }

    fun signUp( firstName: String,
                lastName: String,
                emailAddress: String,
                gender: Int,
                birthDate: Long,
                weight: Float,
                height: Float,
                password: String): Call<String>{
        return serverApi.signUp(firstName,
            lastName,
            emailAddress,
            gender,
            weight,
            height,
            birthDate,
            password)

    }

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }.build()
    }




}
