package com.proyecto.routes

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.proyecto.dto.*
import com.proyecto.mappers.toUsuario
import com.proyecto.mappers.toUsuarioDTO
import com.proyecto.mappers.toUsuarioDtoWithToken
import com.proyecto.models.Evento
import com.proyecto.models.Usuario
import com.proyecto.services.TokenService
import com.proyecto.services.usuarios.IUsuarioService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt
import java.lang.IllegalArgumentException
import io.github.smiley4.ktorswaggerui.dsl.*

fun Application.usuarioRoutes(){
    val service : IUsuarioService by inject()
    val tokenService: TokenService by inject()

    routing {
        route("/usuario"){
            authenticate {
                get({
                    description = "Obtener todos los usuarios"
                    response {
                        HttpStatusCode.OK to {
                            description = "Obtención de todos los usuarios."
                            body<List<Usuario>> { description = "Obtención de todos los usuarios." }
                        }
                        HttpStatusCode.Forbidden to {
                            description = "No tiene permisos para realizar la accion."
                            body<String> { description = "No tiene permisos." }
                        }
                        HttpStatusCode.NotFound to {
                            description = "No se ha encontrado usuarios a mostrar"
                            body<String> { description = "No hay usuarios a mostrar." }
                        }
                    }
                }) {
                    val rol = getRol()
                    if (rol.equals("ADMIN")){
                        val usuarios = service.getAll().toList()

                        if (usuarios.isNotEmpty()){
                            call.respond(HttpStatusCode.OK,usuarios)
                        }else{
                            call.respond(HttpStatusCode.NotFound,"No hay usuarios.")
                        }
                    }
                    call.respond(HttpStatusCode.Forbidden,"No tiene permisos suficientes.")
                }

                get("/{id}",  {
                    description = "Obtener un usuario por id."
                    request {
                        pathParameter<String>("id") {
                            description = "Id del usuario a buscar"
                            required = true
                        }
                    }
                    response {
                        HttpStatusCode.OK to {
                            description = "Obtención del evento por id."
                            body<UsuarioDTO> { description = "Evento obtenido." }
                        }
                        HttpStatusCode.Forbidden to {
                            description = "No tiene permisos para realizar la accion."
                            body<String> { description = "No tiene permisos." }
                        }
                        HttpStatusCode.NotFound to {
                            description = "No se ha encontrado el usuario."
                            body<String> { description = "No existe el ususario con la id" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "No ha sido posible buscar el usuario."
                            body<String> { description = "La id no existe." }
                        }
                    }
                }) {
                    val rol = getRol()

                    if (rol.equals("ADMIN")){
                        try {
                            val id = ObjectId(call.parameters["id"])
                            service.getById(id)
                                .onSuccess {
                                    call.respond(HttpStatusCode.OK,it.toUsuarioDTO())
                                }
                                .onFailure {
                                    call.respond(HttpStatusCode.NotFound,"ERROR ${it.message}")
                                }
                        }catch(e: IllegalArgumentException){
                            call.respond(HttpStatusCode.BadRequest,"No ha sido posible buscar el usuario.")
                        }
                    }
                    call.respond(HttpStatusCode.Forbidden,"No tiene permisos suficientes.")
                }

                post({
                    description = "Crear un usuario."
                    request {
                        body<CreateUsuario> {
                            required = true
                        }
                    }
                    response {
                        HttpStatusCode.Created to {
                            description = "Usuario creado"
                            body<UsuarioDTO> { description = "Usuario obtenido." }
                        }
                        HttpStatusCode.Forbidden to {
                            description = "No tiene permisos para realizar la accion."
                            body<String> { description = "No tiene permisos." }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "No ha sido posible crear el usuario."
                            body<String> { description = "Los datos del usuario no son correctos." }
                        }
                    }
                }) {
                    val rol = getRol()

                    if (rol.equals("ADMIN")){
                        try {
                            val usuario = call.receive<CreateUsuario>()
                            service.create(usuario.toUsuario())
                                .onSuccess {
                                    call.respond(HttpStatusCode.Created,it.toUsuarioDTO())
                                }
                                .onFailure {
                                    call.respond(HttpStatusCode.BadRequest,"ERROR ${it.message}")
                                }
                        }catch (e: BadRequestException){
                            call.respond(HttpStatusCode.BadRequest,"Los datos no son correctos.")
                        }catch (e: RequestValidationException){
                            call.respond(HttpStatusCode.BadRequest,e.reasons)
                        }

                    }
                    call.respond(HttpStatusCode.Forbidden,"No tiene permisos suficientes.")
                }

                put("/{id}", {
                    description = "Actualizar datos de un usuario por id."
                    request {
                        pathParameter<String>("id") {
                            description = "Id del usuario a actualizar"
                            required = true
                        }
                        body<UpdateUsuario> {
                            required = true
                        }
                    }
                    response {
                        HttpStatusCode.OK to {
                            description = "Actualizado correctamente el usuario"
                            body<UsuarioDTO> { description = "Evento actualizado" }
                        }
                        HttpStatusCode.Forbidden to {
                            description = "No tiene permisos para realizar la accion."
                            body<String> { description = "No tiene permisos." }
                        }
                        HttpStatusCode.NotFound to {
                            description = "No se ha encontrado el usuario."
                            body<String> { description = "No existe el usuario con la id" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "No ha sido posible actualizar el usuario."
                            body<String> { description = "Los datos del usuario no son correctos." }
                        }
                    }
                }) {
                    val rol = getRol()
                    if(rol.equals("ADMIN") || rol.equals("USER")){
                        try{
                            val id = ObjectId(call.parameters["id"])
                            val usuario = call.receive<UpdateUsuario>()
                            service.update(id,usuario)
                                .onSuccess {
                                    call.respond(HttpStatusCode.OK,it.toUsuarioDTO())
                                }.onFailure {
                                    if (it.message.contains("No existe")){
                                        call.respond(HttpStatusCode.NotFound,it.message)
                                    }else{
                                        call.respond(HttpStatusCode.BadRequest,it.message)
                                    }
                                }

                        }catch(e: BadRequestException){
                            call.respond(HttpStatusCode.BadRequest,"Los datos no son correctos.")
                        }catch(e: IllegalArgumentException){
                            e.printStackTrace()
                            call.respond(HttpStatusCode.BadRequest,"No ha sido posible actualizar el usuario.")
                        }catch (e: RequestValidationException){
                            call.respond(HttpStatusCode.BadRequest,e.reasons)
                        }
                        call.respond(HttpStatusCode.Forbidden,"No tiene permisos suficientes.")
                    }
                }

                delete("/{id}",  {
                    description = "Eliminar usuario por id."
                    request {
                        pathParameter<String>("id") {
                            description = "Id del usuario a eliminar"
                            required = true
                        }
                    }
                    response {
                        HttpStatusCode.NoContent to {
                            description = "Usuario eliminado."
                        }
                        HttpStatusCode.Forbidden to {
                            description = "No tiene permisos para realizar la accion."
                            body<String> { description = "No tiene permisos." }
                        }
                        HttpStatusCode.NotFound to {
                            description = "No se ha encontrado el usuario."
                            body<String> { description = "No existe el usuario con la id" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "No ha sido posible eliminar el usuario."
                            body<String> { description = "Los datos del usuario no son correctos." }
                        }
                    }
                }) {
                    val rol = getRol()
                    if (rol.equals("ADMIN")){
                        try {
                            val id = ObjectId(call.parameters["id"])
                            service.delete(id)
                                .onSuccess {
                                    call.respond(HttpStatusCode.NoContent)
                                }
                                .onFailure {
                                    call.respond(HttpStatusCode.NotFound, it.message)
                                }
                        }catch(e: IllegalArgumentException){
                            call.respond(HttpStatusCode.BadRequest,"No ha sido posible eliminar el usuario.")
                        }
                    }
                    call.respond(HttpStatusCode.Forbidden,"No tiene permisos suficientes.")
                }
            }
        }


        post("/login", {
            description = "Logueo del usuario."
            request {
                body<UserLogin> {
                    required = true
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Se ha logueado correctamente."
                    body<UsuarioDTOWithToken> { description = "Usuario con token." }
                }
                HttpStatusCode.Unauthorized to {
                    description = "El nombre de usuario o contraseña son incorrectos."
                    body<String> { description = "El nombre de usuario o contraseña son incorrectos." }
                }
                HttpStatusCode.BadRequest to {
                    description = "No ha sido posible loguearse."
                    body<String> { description = "Los datos del usuario no son correctos." }
                }
            }
        }) {
            try{
                val login = call.receive<UserLogin>()
                service.getByUsername(login.userName)
                    .onSuccess {
                        if (BCrypt.checkpw(login.password,it.password)){
                            val token = tokenService.generateJWT(it)
                            call.respond(HttpStatusCode.OK,it.toUsuarioDtoWithToken(token))
                        }else{
                            call.respond(HttpStatusCode.Unauthorized, "El nombre de usuario o contraseña son incorrectos.")
                        }
                    }
                    .onFailure {
                        if (it.message.contains("No existe")){
                            call.respond(HttpStatusCode.NotFound,it.message)
                        }else{
                            call.respond(HttpStatusCode.BadRequest,it.message)
                        }

                    }
            }catch (e:BadRequestException){
                call.respond(HttpStatusCode.BadRequest,"Los datos no son correctos.")
            }

        }
        post("/register", {
            description = "Registro de usuario."
            request {
                body<CreateUsuario> {
                    required = true
                }
            }
            response {
                HttpStatusCode.Created to {
                    description = "Se ha creado correctamente el usuario."
                    body<UsuarioDTOWithToken> { description = "Usuario con token." }
                }
                HttpStatusCode.BadRequest to {
                    description = "No ha sido posible registrarse."
                    body<String> { description = "Los datos del usuario no son correctos." }
                }
            }
        }) {
            try {
                val usuario = call.receive<CreateUsuario>()
                service.create(usuario.toUsuario())
                    .onSuccess {
                        val token = tokenService.generateJWT(it)
                        call.respond(HttpStatusCode.Created,it.toUsuarioDtoWithToken(token))
                    }
                    .onFailure {
                        call.respond(HttpStatusCode.BadRequest, it.message)
                    }
            }catch (e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest,e.reasons)
            }catch (e: BadRequestException){
                call.respond(HttpStatusCode.BadRequest,"Los datos no son correctos.")
            }

        }

    }

}
private fun PipelineContext<Unit, ApplicationCall>.getRol(): String {
    val jwt = call.principal<JWTPrincipal>()
    return jwt?.payload?.getClaim("rol").toString().replace("\"","")
}