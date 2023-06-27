package com.proyecto.repositories.eventos

import com.proyecto.models.Evento
import com.proyecto.services.database.DatabaseContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.updateOne

@Single
class EventoRepository(
    private val context: DatabaseContext
): IEventoRepository {
    override fun getAll(): Flow<Evento> {
        return context.mongoDatabase.getCollection<Evento>()
            .find().publisher.asFlow()
    }

    override suspend fun getById(id: ObjectId): Evento? = withContext(Dispatchers.IO) {
        context.mongoDatabase.getCollection<Evento>()
            .findOneById(id)
    }

    override suspend fun getByName(name: String): Evento? = withContext(Dispatchers.IO) {
        context.mongoDatabase.getCollection<Evento>()
            .find("{'nombre': '$name'}").first()
    }

    override suspend fun create(evento: Evento): Evento = withContext(Dispatchers.IO){
        context.mongoDatabase.getCollection<Evento>()
            .save(evento)
        evento
    }

    override suspend fun delete(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        context.mongoDatabase.getCollection<Evento>()
            .deleteOneById(id).let { true }
    }

    override suspend fun update(evento: Evento): Evento = withContext(Dispatchers.IO) {
        context.mongoDatabase.getCollection<Evento>()
            .updateOne(evento)
        evento
    }
}