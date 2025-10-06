package io.github.whdt

import io.github.whdt.db.entities.HumanDigitalTwin
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    private lateinit var repository: HDTRepository

    @Before
    fun setup() {
        // Crea un mock dell'interfaccia
        repository = mockk()
    }

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testProva() = testApplication {
        // Given
        val hdt1 = HumanDigitalTwin("Twin1")
        val hdt2 = HumanDigitalTwin("Twin2")

        // When
        repository.addHDT(hdt1)
        repository.addHDT(hdt2)
        val result = repository.allHDT()

        // Then
        /*
        assertEquals(2, result.size)
        assertTrue(result.contains(hdt1))
        assertTrue(result.contains(hdt2))*/
    }

}
