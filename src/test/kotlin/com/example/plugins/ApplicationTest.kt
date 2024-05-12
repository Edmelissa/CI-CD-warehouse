import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.Assert.assertEquals
import org.junit.*

class ApplicationTest {

    private lateinit var client: HttpClient

    @Before
    fun init() {
        client = HttpClient()
    }

    @Test
    fun testRoot() = testApplication {
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, world!", response.bodyAsText())
    }
}