package com.proyecto.repositories.eventos

import com.proyecto.models.Evento
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId

interface IEventoRepository {
    fun getAll(): Flow<Evento>
    suspend fun getById(id: ObjectId): Evento?
    suspend fun getByName(name: String): Evento?
    suspend fun create(evento: Evento): Evento
    suspend fun delete(id: ObjectId): Boolean
    suspend fun update(evento: Evento): Evento
}