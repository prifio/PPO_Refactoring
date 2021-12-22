package tolya.ash

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ru.akirakozov.sd.refactoring.*
import java.io.File
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DbTest {
    @BeforeAll
    fun prepareApp() {
        File("test.db").delete()
        initDb()
        addProduct("name1", 1)
        addProduct("name2", 2)
        addProduct("name3", 3)
    }

    @Test
    fun getTest() {
        val result = getProducts()
        assertEquals(
            listOf(
                "name1" to 1L,
                "name2" to 2L,
                "name3" to 3L
            ), result
        )
    }

    @Test
    fun queryTest() {
        assertEquals("name1" to 1L, getMin())
        assertEquals("name3" to 3L, getMax())
        assertEquals(6, getSum())
        assertEquals(3, getCount())
    }
}