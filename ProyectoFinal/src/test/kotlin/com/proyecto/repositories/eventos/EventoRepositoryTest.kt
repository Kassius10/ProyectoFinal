package com.proyecto.repositories.eventos

import com.proyecto.models.Evento
import com.proyecto.services.database.DatabaseContext
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventoRepositoryTest {
    private val repository = EventoRepository(DatabaseContext())
    private val evento = Evento(
        id = ObjectId(),
        nombre = "EventoTest",
        descripcion = "Evento",
        fecha = LocalDateTime.now(),
        lugar = "Test",
        imagen = "",
        desafios = mutableListOf()
    )

//    @Test
//    fun getAll() = runTest{
//        val res = repository.getAll().toList()
//        assertEquals(res, listOf<Evento>())
//    }

    @Test
    fun getById() = runTest {
        repository.create(evento)
        val res = repository.getById(evento.id)

        assertAll(
            {assertEquals(res!!.id,evento.id)},
            {assertEquals(res!!.nombre,evento.nombre)},
            {assertEquals(res!!.lugar,evento.lugar)},
            {assertEquals(res!!.imagen,evento.imagen)},
        )
        repository.delete(evento.id)
    }

    @Test
    fun create() = runTest {
        val res = repository.create(evento)

        assertAll(
            {assertEquals(res.id,evento.id)},
            {assertEquals(res.nombre,evento.nombre)},
            {assertEquals(res.lugar,evento.lugar)},
            {assertEquals(res.imagen,evento.imagen)},
        )
        repository.delete(evento.id)
    }

    @Test
    fun delete() = runTest{
        repository.create(evento)
        val res = repository.delete(evento.id)

        assertTrue(res)
    }
}