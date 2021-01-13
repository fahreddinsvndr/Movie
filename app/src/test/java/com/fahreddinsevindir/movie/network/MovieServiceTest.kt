package com.fahreddinsevindir.movie.network

import com.fahreddinsevindir.movie.ZoneDateTimeProvider
import com.fahreddinsevindir.movie.ZoneDateTimeProvider.loadTimeZone
import com.fahreddinsevindir.movie.model.Movie
import com.fahreddinsevindir.movie.model.Movies
import com.fahreddinsevindir.movie.model.adapter.ZonedDateTimeAdapter
import com.squareup.moshi.Moshi
import io.reactivex.rxjava3.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class MovieServiceTest {

    private lateinit var service: MovieService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        ZoneDateTimeProvider.loadTimeZone()
        mockWebServer = MockWebServer()
        val moshi = Moshi.Builder()
            .add(ZonedDateTimeAdapter())
            .build()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @After
    fun cleanup() {
        mockWebServer.shutdown()
    }

    @Test
    fun getTrendingMovie() {
        enqueueResponse("trending-movies.json")

        val testObserver = TestObserver<Movies>()
        service.getTrendingMovie(1).subscribe(testObserver)

        // random test the values
        testObserver.await()
            .assertValue {
                return@assertValue it.page == 1L
            }
            .assertValue {
                return@assertValue it.totalPages == 1000L
            }
            .assertComplete()
            .assertNoErrors()

        // test the request path
        val takeRequest = mockWebServer.takeRequest()
        assertThat(takeRequest.path, `is`("/trending/all/day?page=1"))
    }


    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }

        mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }
}