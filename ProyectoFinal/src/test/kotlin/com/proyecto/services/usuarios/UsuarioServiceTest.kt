package com.proyecto.services.usuarios

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.proyecto.dto.UpdateUsuario
import com.proyecto.models.Usuario
import com.proyecto.repositories.usuarios.UsuarioRepository
import io.mockk.coEvery
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
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioServiceTest {

    @MockK
    private lateinit var repository: UsuarioRepository

    @InjectMockKs
    private lateinit var service: UsuarioService

    private val user = Usuario(
        id = ObjectId(),
        name = "Prueba",
        surname = "Test",
        userName = "Prueba",
        email = "prueba@gmail.com",
        password = "Da123456",
        dateBirth = LocalDate.now()
    )

    private val updateUser = UpdateUsuario(
        name = "Prueba",
        surname = "Test",
        userName = "Prueba",
        email = "prueba@gmail.com",
        dateBirth = LocalDate.now()
    )

    @Test
    fun getAll() = runTest {
        coEvery { repository.getAll() } returns flowOf()
        val res = service.getAll().toList()

        assertEquals(res, listOf<Usuario>())

        coEvery { repository.getAll() }

    }

    @Test
    fun getById() = runTest {
        coEvery { repository.getById(user.id) } returns user

        val res = service.getById(user.id)

        assertAll(
            { assertEquals(res.get()!!.id, user.id) },
            { assertEquals(res.get()!!.name, user.name) },
            { assertEquals(res.get()!!.surname, user.surname) },
        )
        coEvery { repository.getById(user.id) }
    }

    @Test
    fun getByIdNoExist() = runTest {
        coEvery { repository.getById(user.id) } returns null

        val res = service.getById(user.id)
        assertTrue(res.getError()!!.message.contains("No existe el usuario"))

        coEvery { repository.getById(user.id) }
    }

    @Test
    fun getByUsername() = runTest {
        coEvery { repository.getByUsername(user.userName) } returns user

        val res = service.getByUsername(user.userName)

        assertAll(
            { assertEquals(res.get()!!.id, user.id) },
            { assertEquals(res.get()!!.name, user.name) },
            { assertEquals(res.get()!!.surname, user.surname) },
        )

        coEvery { repository.getByUsername(user.userName) }
    }

    @Test
    fun getByUsernameNoExist() = runTest {
        coEvery { repository.getByUsername(user.userName) } returns null

        val res = service.getByUsername(user.userName)

        assertTrue(res.getError()!!.message.contains("No existe el usuario"))

        coEvery { repository.getByUsername(user.userName) }
    }

    @Test
    fun create() = runTest {
        coEvery { repository.create(user) } returns user
        coEvery { repository.getByUsername(user.userName) } returns null
        coEvery { repository.getByEmail(user.email)} returns null

        val res = service.create(user)

        assertAll(
            { assertEquals(res.get()!!.id, user.id) },
            { assertEquals(res.get()!!.name, user.name) },
            { assertEquals(res.get()!!.surname, user.surname) }
        )
        coEvery { repository.create(user) }
        coEvery { repository.getByEmail(user.email)}
        coEvery { repository.getByUsername(user.userName)}
    }

    @Test
    fun createExistUserName() = runTest {
        coEvery { repository.getByUsername(user.userName) } returns user
        coEvery { repository.getByEmail(user.email)} returns null

        val res = service.create(user)

        assertTrue(res.getError()!!.message.contains("Ya existe el usuario"))

        coEvery { repository.getByEmail(user.email)}
        coEvery { repository.getByUsername(user.userName)}
    }

    @Test
    fun createExistEmail() = runTest {
        coEvery { repository.getByUsername(user.userName) } returns null
        coEvery { repository.getByEmail(user.email)} returns user

        val res = service.create(user)

        assertTrue(res.getError()!!.message.contains("Ya existe el correo"))

        coEvery { repository.getByEmail(user.email)}
        coEvery { repository.getByUsername(user.userName)}
    }

    @Test
    fun update()= runTest {
        coEvery { repository.update(user) } returns user
        coEvery { repository.getById(user.id) } returns user
        coEvery { repository.getByUsername(updateUser.userName) } returns user
        coEvery { repository.getByEmail(updateUser.email) } returns user

        val res = service.update(user.id,updateUser)

        assertAll(
            { assertEquals(res.get()!!.id, user.id) },
            { assertEquals(res.get()!!.name, user.name) },
            { assertEquals(res.get()!!.surname, user.surname) },
        )
        coEvery { repository.create(user) }
        coEvery { repository.getById(user.id) }
        coEvery { repository.getByEmail(user.email)}
        coEvery { repository.getByUsername(user.userName)}
    }

    @Test
    fun updateNotFound()= runTest {
        coEvery { repository.getById(user.id) } returns null

        val res = service.update(user.id,updateUser)
        assertTrue(res.getError()!!.message.contains("No existe el usuario"))

        coEvery { repository.getById(user.id) }
    }

    @Test
    fun delete() = runTest {
        coEvery { repository.delete(user.id) } returns true
        coEvery { repository.getById(user.id) } returns user

        val res = service.delete(user.id)

        assertTrue(res.get()!!)

        coEvery { repository.delete(user.id) }
        coEvery { repository.getById(user.id) }
    }
    @Test
    fun deleteError() = runTest {
        coEvery { repository.getById(user.id) } returns null

        val res = service.delete(user.id)

        assertTrue(res.getError()!!.message.contains("No existe el usuario"))

        coEvery { repository.getById(user.id) }
    }
}