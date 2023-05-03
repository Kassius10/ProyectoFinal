package com.proyecto.services.eventos

import com.proyecto.models.Evento
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import com.github.michaelbull.result.Result
import com.proyecto.errors.EventoError

interface IEventoService {
    fun getAll(): Flow<Evento>
    suspend fun getById(id: ObjectId): Result<Evento,EventoError>
    suspend fun create(evento: Evento): Result<Evento,EventoError>
    suspend fun delete(id: ObjectId): Result<Boolean,EventoError>
//    suspend fun update(evento: Evento): Evento
}