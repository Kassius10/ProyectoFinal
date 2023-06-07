package com.proyecto.services.eventos

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.proyecto.dto.UpdateEvento
import com.proyecto.models.Desafio
import com.proyecto.models.Direccion
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
        desafios = mutableListOf(),
        ranking = mutableListOf()
    )
    private val updateEvento = UpdateEvento(
        nombre = "EventoTest1",
        descripcion = "Evento",
        fecha = LocalDateTime.now(),
        lugar = "Test",
        imagen = "",
        desafios = mutableListOf(
            Desafio(1,"test","test", Direccion("123","123"), "res","clave")
        ),
    )

    @Test
    fun getAll() = runTest {
        coEvery { repository.getAll() } returns flowOf()
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
    fun getByIdNotFound() = runTest {
        coEvery { repository.getById(evento.id) } returns null

        val res = service.getById(evento.id)

        assertTrue(res.getError()!!.message.contains("No existe"))

        coVerify { repository.getById(evento.id) }
    }

    @Test
    fun create()= runTest {
        coEvery { repository.getByName(evento.nombre) } returns null
        coEvery { repository.create(evento) } returns evento

        val res = service.create(evento)

        assertAll(
            { assertEquals(res.get()!!.id, evento.id) },
            { assertEquals(res.get()!!.nombre, evento.nombre) },
            { assertEquals(res.get()!!.lugar, evento.lugar) },
            { assertEquals(res.get()!!.imagen, evento.imagen) },
        )
        coVerify { repository.getByName(evento.nombre) }
        coVerify { repository.create(evento) }
    }

    @Test
    fun createError() = runTest {
        coEvery { repository.getByName(evento.nombre) } returns evento

        val res = service.create(evento)

        assertTrue(res.getError()!!.message.contains("Ya existe"))

        coVerify { repository.getByName(evento.nombre)  }
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

    @Test
    fun deleteNotFound() = runTest {
        coEvery { repository.getById(evento.id) } returns null

        val res = service.delete(evento.id)

        assertTrue(res.getError()!!.message.contains("No existe"))

        coVerify { repository.getById(evento.id)  }
    }
    @Test
    fun update() = runTest {
        coEvery { repository.getById(evento.id) } returns evento
        coEvery { repository.getByName(updateEvento.nombre) } returns evento
        coEvery { repository.update(evento)} returns evento

        val res = service.update(evento.id,updateEvento)

        assertAll(
            { assertEquals(res.get()!!.nombre, updateEvento.nombre) },
            { assertEquals(res.get()!!.lugar, updateEvento.lugar) },
            { assertEquals(res.get()!!.imagen, updateEvento.imagen) },
        )

        coVerify { repository.getById(evento.id)  }
        coVerify { repository.getByName(updateEvento.nombre) }
        coVerify { repository.update(evento)  }
    }

    @Test
    fun updateNotFound() = runTest {
        coEvery { repository.getById(evento.id) } returns null

        val res = service.update(evento.id,updateEvento)
        assertTrue(res.getError()!!.message.contains("No existe"))

        coVerify { repository.getById(evento.id)  }
    }
}