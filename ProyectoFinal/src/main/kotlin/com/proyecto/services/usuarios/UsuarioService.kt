package com.proyecto.services.usuarios

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.proyecto.dto.UpdateUsuario
import com.proyecto.errors.EventoError
import com.proyecto.errors.UsuarioError
import com.proyecto.models.Usuario
import com.proyecto.repositories.usuarios.IUsuarioRepository
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.koin.core.annotation.Single

@Single
class UsuarioService(
    private val repository: IUsuarioRepository
): IUsuarioService {
    override fun getAll(): Flow<Usuario> {
        return repository.getAll()
    }

    override suspend fun getById(id: ObjectId): Result<Usuario, UsuarioError> {
        val usuario = repository.getById(id)
        usuario?.let {
            return Ok(it)
        }.run {
            return Err(UsuarioError.NoExist("No existe el usuario con id: $id"))
        }
    }

    override suspend fun getByUsername(username: String): Result<Usuario, UsuarioError>{
        val usuario = repository.getByUsername(username)
        usuario?.let {
            return Ok(it)
        }.run {
            return Err(UsuarioError.NoExist("No existe el usuario"))
        }
    }

    override suspend fun create(usuario: Usuario): Result<Usuario, UsuarioError> {
        val exist = repository.getByUsername(usuario.userName)
        val userEmail = repository.getByEmail(usuario.email)

        exist?.let {
            return Err(UsuarioError.Exist("Ya existe el usuario."))
        }.run {
            userEmail?.let {
                return Err(UsuarioError.Exist("Ya existe el correo."))
            }.run{
                return Ok(repository.create(usuario))
            }
        }
    }

    override suspend fun update(id: ObjectId, usuario: UpdateUsuario): Result<Usuario, UsuarioError> {
        val exist = repository.getById(id)

        exist?.let {
            val userName = repository.getByUsername(usuario.userName)
            val userEmail = repository.getByEmail(usuario.email)

            if(userName != null && exist.id != userName.id) return Err(UsuarioError.Exist("Ya existe el nombre de usuario."))
            if(userEmail != null && exist.id != userEmail.id) return Err(UsuarioError.Exist("Ya existe el correo."))

            val usuarioUpdate = it.apply {
                it.userName=  usuario.userName
                it.name = usuario.name
                it.surname = usuario.surname
                it.email = usuario.email
                it.dateBirth = usuario.dateBirth
            }
            return Ok(repository.update(usuarioUpdate))
        }.run {
            return Err(UsuarioError.NoExist("No existe el usuario."))
        }
    }

    override suspend fun delete(id: ObjectId): Result<Boolean, UsuarioError> {
        val exist = repository.getById(id)
        exist?.let {
            repository.delete(id)
            return Ok(true)
        }.run {
            return Err(UsuarioError.Exist("No existe el usuario con id: $id"))
        }
    }
}