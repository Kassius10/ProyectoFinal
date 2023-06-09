package com.proyecto.routes

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.proyecto.dto.CreateUsuario
import com.proyecto.dto.UpdateUsuario
import com.proyecto.dto.UserLogin
import com.proyecto.mappers.toUsuario
import com.proyecto.mappers.toUsuarioDTO
import com.proyecto.mappers.toUsuarioDtoWithToken
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

fun Application.usuarioRoutes(){
    val service : IUsuarioService by inject()
    val tokenService: TokenService by inject()

    routing {
        route("/usuario"){
            authenticate {
                get {
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

                get("/{id}") {
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

                post {
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

                put("/{id}") {
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

                delete("/{id}") {
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


        post("/login") {
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
        post("/register") {
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