package com.proyecto.services.eventos

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.proyecto.dto.UpdateEvento
import com.proyecto.errors.EventoError
import com.proyecto.models.Evento
import com.proyecto.models.Ranking
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
        val exist = repository.getByName(evento.nombre)

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
            return Err(EventoError.NoExist("No existe el evento con id: $id"))
        }
    }
    override suspend fun update(id: ObjectId, evento: UpdateEvento): Result<Evento, EventoError> {
        val exist = repository.getById(id)

        exist?.let {
            val eventoName = repository.getByName(evento.nombre)
            if (eventoName != null && eventoName.id != exist.id) return Err(EventoError.Exist("Ya existe el nombre del evento."))

            val eventoUpdate = it.apply {
                it.nombre = evento.nombre
                it.descripcion = evento.descripcion
                it.lugar = evento.lugar
                it.fecha = evento.fecha
                it.imagen = evento.imagen
                if (evento.desafios.isNotEmpty()){
                    it.desafios = evento.desafios
                }
            }
            return Ok(repository.update(eventoUpdate))

        }.run {
            return Err(EventoError.NoExist("No existe el evento con id: $id"))
        }
    }
    override suspend fun updateRanking(id: ObjectId, ranking: Ranking): Result<Boolean, EventoError> {
        val exist = repository.getById(id)

        exist?.let {
            val existUser = it.ranking.find {r -> r.userName.equals(ranking.userName) }

            existUser?.let {
                if (ranking.tiempo < existUser.tiempo ){
                    existUser.tiempo = ranking.tiempo
                }
            }?: it.ranking.add(ranking)

            it.ranking.sortBy { r -> r.tiempo }
            repository.update(it)
            return Ok(true)
        }.run{
            return Err(EventoError.NoExist("No existe el evento"))
        }
    }
}