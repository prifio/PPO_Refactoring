package tolya.ash

import org.eclipse.jetty.server.Server
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ru.akirakozov.sd.refactoring.startServer
import java.io.File
import java.net.URL
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpTest {
    lateinit var server: Server

    private fun makeRequest(req: String): String =
        URL("http://127.0.0.1:8081/$req").readText()
            .replace("\r\n", "\n")
            .replace('\t', ' ')

    @BeforeAll
    fun prepareApp() {
        File("test.db").delete()
        server = startServer(emptyArray())
        addProduct("name1", 1)
        addProduct("name2", 2)
        addProduct("name3", 3)
    }

    @AfterAll
    fun closeApp() {
        server.stop()
    }

    private fun addProduct(name: String, price: Long) {
        makeRequest("add-product?name=$name&price=$price")
    }

    private fun getProducts(): String =
        makeRequest("get-products")

    private fun queryProduct(command: String): String =
        makeRequest("query?command=$command")


    @Test
    fun getTest() {
        val result = getProducts().replace("\r\n", "\n")
        assertEquals("""<html><body>
name1 1</br>
name2 2</br>
name3 3</br>
</body></html>
""", result)
    }

    @Test
    fun queryTest() {
        val maxResult = queryProduct("max")
        assertEquals("""<html><body>
<h1>Product with max price: </h1>
name3 3</br>
</body></html>
""", maxResult)
        val minResult = queryProduct("min")
        assertEquals("""<html><body>
<h1>Product with min price: </h1>
name1 1</br>
</body></html>
""", minResult)
        val sumResult = queryProduct("sum")
        assertEquals("""<html><body>
Summary price: 
6
</body></html>
""", sumResult)
        val countResult = queryProduct("count")
        assertEquals("""<html><body>
Number of products: 
3
</body></html>
""", countResult)
    }
}
