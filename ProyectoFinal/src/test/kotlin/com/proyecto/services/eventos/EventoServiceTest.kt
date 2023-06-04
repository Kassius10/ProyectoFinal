package com.proyecto.services.eventos

import com.github.michaelbull.result.get
import com.proyecto.models.Evento
import com.proyecto.repositories.eventos.EventoRepository
import com.proyecto.repositories.eventos.IEventoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventoServiceTest {

    @MockK
    private lateinit var repository: EventoRepository

    @InjectMockKs
    private lateinit var service: EventoService

    private val evento = Evento(
        id = ObjectId(),
        nombre = "EventoTest",
        descripcion = "Evento",
        fecha = LocalDateTime.now(),
        lugar = "Test",
        imagen = "",
        desafios = mutableListOf()
    )

    @Test
    fun getAll() = runTest {
        coEvery { repository.getAll() } returns flowOf<Evento>()
        val res = service.getAll().toList()

        assertEquals(res, listOf<Evento>())

        coVerify { repository.getAll() }
    }

    @Test
    fun getById() = runTest {
        coEvery { repository.getById(evento.id) } returns evento

        val res = service.getById(evento.id)

        assertAll(
            { assertEquals(res.get()!!.id, evento.id) },
            { assertEquals(res.get()!!.nombre, evento.nombre) },
            { assertEquals(res.get()!!.lugar, evento.lugar) },
            { assertEquals(res.get()!!.imagen, evento.imagen) },
        )
        coVerify { repository.getById(evento.id) }
    }

    @Test
    fun create()= runTest {
        coEvery { repository.getById(evento.id) } returns null
        coEvery { repository.create(evento) } returns evento

        val res = service.create(evento)

        assertAll(
            { assertEquals(res.get()!!.id, evento.id) },
            { assertEquals(res.get()!!.nombre, evento.nombre) },
            { assertEquals(res.get()!!.lugar, evento.lugar) },
            { assertEquals(res.get()!!.imagen, evento.imagen) },
        )
        coVerify { repository.getById(evento.id) }
        coVerify { repository.create(evento) }
    }

    @Test
    fun delete() = runTest {
        coEvery { repository.getById(evento.id) } returns evento
        coEvery { repository.delete(evento.id) } returns true

        val res = service.delete(evento.id)

        assertTrue(res.get()!!)

        coVerify { repository.getById(evento.id) }
        coVerify { repository.delete(evento.id) }
    }
}