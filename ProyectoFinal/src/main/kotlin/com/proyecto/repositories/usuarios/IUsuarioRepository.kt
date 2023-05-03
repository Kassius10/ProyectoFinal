package com.proyecto.repositories.usuarios

import com.proyecto.models.Usuario
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId

interface IUsuarioRepository {
    fun getAll(): Flow<Usuario>
    suspend fun getById(id: ObjectId): Usuario?
    suspend fun getByUsername(username: String): Usuario?
    suspend fun create(usuario: Usuario): Usuario
    suspend fun delete(id: ObjectId): Boolean
    suspend fun update(usuario: Usuario): Usuario
}