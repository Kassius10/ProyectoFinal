package com.proyecto.services.eventos

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.proyecto.errors.EventoError
import com.proyecto.models.Evento
import com.proyecto.repositories.eventos.IEventoRepository
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.koin.core.annotation.Single

@Single
class EventoService(
    private val repository: IEventoRepository
): IEventoService {
    override fun getAll(): Flow<Evento> {
        return repository.getAll()
    }

    override suspend fun getById(id: ObjectId): Result<Evento, EventoError> {
        val evento = repository.getById(id)
        evento?.let {
            return Ok(it)
        }.run {
            return Err(EventoError.NoExist("No existe el evento con id: $id"))
        }
    }

    override suspend fun create(evento: Evento): Result<Evento,EventoError>{
        val exist = repository.getById(evento.id)

        // TODO esto seria para comparar nombre o eso
        exist?.let {
            return Err(EventoError.Exist("Ya existe el evento."))
        }.run {
          return Ok(repository.create(evento))
        }
    }

    override suspend fun delete(id: ObjectId): Result<Boolean, EventoError> {
        val exist = repository.getById(id)
        exist?.let {
            repository.delete(id)
            return Ok(true)
        }.run {
            return Err(EventoError.Exist("No existe el evento con id: $id"))
        }

    }

}