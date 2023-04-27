package com.proyecto.repositories.usuarios

import com.proyecto.models.Evento
import com.proyecto.models.Usuario
import com.proyecto.services.database.DatabaseContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.koin.core.annotation.Single

@Single
class UsuarioRepository(
    private val context: DatabaseContext
): IUsuarioRepository {
    override fun getAll(): Flow<Usuario> {
        return context.mongoDatabase.getCollection<Usuario>()
            .find().publisher.asFlow()
    }

    override suspend fun getById(id: ObjectId): Usuario? = withContext(Dispatchers.IO) {
        context.mongoDatabase.getCollection<Usuario>()
            .findOneById(id)
    }

    override suspend fun create(usuario: Usuario): Usuario = withContext(Dispatchers.IO) {
        context.mongoDatabase.getCollection<Usuario>()
            .save(usuario)
        usuario
    }

    override suspend fun delete(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        context.mongoDatabase.getCollection<Usuario>()
            .deleteOneById(id).let { true }
    }

    override suspend fun update(usuario: Usuario): Usuario {
        TODO("Not yet implemented")
    }
}