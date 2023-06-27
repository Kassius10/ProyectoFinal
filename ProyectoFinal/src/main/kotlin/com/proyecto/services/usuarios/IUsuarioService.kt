package com.proyecto.services.usuarios

import com.github.michaelbull.result.Result
import com.proyecto.dto.UpdateUsuario
import com.proyecto.errors.EventoError
import com.proyecto.errors.UsuarioError
import com.proyecto.models.Evento
import com.proyecto.models.Usuario
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId

interface IUsuarioService {
    fun getAll(): Flow<Usuario>
    suspend fun getById(id: ObjectId): Result<Usuario, UsuarioError>
    suspend fun getByUsername(username: String): Result<Usuario, UsuarioError>
    suspend fun create(usuario: Usuario): Result<Usuario, UsuarioError>
    suspend fun update(id: ObjectId, usuario: UpdateUsuario): Result<Usuario, UsuarioError>
    suspend fun delete(id: ObjectId): Result<Boolean, UsuarioError>
}