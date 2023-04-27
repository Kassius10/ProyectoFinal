package com.proyecto.services.usuarios

import com.github.michaelbull.result.Result
import com.proyecto.errors.EventoError
import com.proyecto.models.Evento
import com.proyecto.models.Usuario
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId

interface IUsuarioService {
    fun getAll(): Flow<Usuario>
    suspend fun getById(id: ObjectId): Result<Usuario, EventoError>
    suspend fun create(usuario: Usuario): Result<Usuario, EventoError>
    suspend fun delete(id: ObjectId): Result<Boolean, EventoError>
}