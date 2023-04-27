package com.proyecto.mappers

import com.proyecto.dto.CreateUsuario
import com.proyecto.dto.UsuarioDTO
import com.proyecto.models.Usuario
import org.bson.types.ObjectId
import org.mindrot.jbcrypt.BCrypt

fun CreateUsuario.toUsuario(): Usuario{
    return Usuario(
        id = ObjectId(),
        userName = this.userName,
        name = this.name,
        surname = this.surname,
        password = BCrypt.hashpw(this.password,BCrypt.gensalt(12)),
        dateBirth = this.dateBirth
    )
}
fun Usuario.toUsuarioDTO(): UsuarioDTO {
    return UsuarioDTO(
        userName = this.userName,
        name = this.name,
        surname = this.surname
    )
}