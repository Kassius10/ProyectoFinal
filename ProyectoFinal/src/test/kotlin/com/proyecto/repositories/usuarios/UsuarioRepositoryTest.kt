package com.proyecto.repositories.usuarios

import com.proyecto.models.Usuario
import com.proyecto.services.database.DatabaseContext
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioRepositoryTest {
    private val repository = UsuarioRepository(DatabaseContext())
    private val user = Usuario(
        id = ObjectId(),
        name = "Prueba",
        surname = "Test",
        userName = "Prueba",
        email = "prueba@gmail.com",
        password = "Da123456",
        dateBirth = LocalDate.now()
    )

    @Test
    fun getAll() = runTest {
        repository.create(user)

        val res = repository.getAll().toList()
        assertTrue(res.isNotEmpty())

        repository.delete(user.id)
    }

    @Test
    fun getById() = runTest {
        repository.create(user)

        val res = repository.getById(user.id)
        assertAll(
            { assertEquals(res!!.id, user.id)},
            { assertEquals(res!!.userName, user.userName) },
            { assertEquals(res!!.name, user.name)},
            { assertEquals(res!!.surname, user.surname)}
        )

        repository.delete(user.id)
    }

    @Test
    fun getByUsername()= runTest {
        repository.create(user)

        val res = repository.getByUsername(user.userName)
        assertAll(
            { assertEquals(res!!.id, user.id)},
            { assertEquals(res!!.userName, user.userName) },
            { assertEquals(res!!.name, user.name)},
            { assertEquals(res!!.surname, user.surname)}
        )

        repository.delete(user.id)
    }

    @Test
    fun getByEmail()= runTest {
        repository.create(user)

        val res = repository.getByEmail(user.email)
        assertAll(
            { assertEquals(res!!.id, user.id)},
            { assertEquals(res!!.userName, user.userName) },
            { assertEquals(res!!.name, user.name)},
            { assertEquals(res!!.surname, user.surname)}
        )

        repository.delete(user.id)
    }

    @Test
    fun create() = runTest {
        val res = repository.create(user)
        assertAll(
            { assertEquals(res.id, user.id) },
            { assertEquals(res.userName, user.userName) },
            { assertEquals(res.name, user.name) },
            { assertEquals(res.surname, user.surname) }
        )

        repository.delete(user.id)
    }

    @Test
    fun delete() = runTest {
        repository.create(user)
        val res = repository.delete(user.id)

        assertTrue(res)
    }

    @Test
    fun update() = runTest {
        repository.create(user)
        user.userName = "NuevoTest"

        val res = repository.update(user)

        assertAll(
            { assertEquals(res.id, user.id) },
            { assertEquals(res.userName, user.userName) },
            { assertEquals(res.name, user.name) },
            { assertEquals(res.surname, user.surname) }
        )

        repository.delete(user.id)
    }
}