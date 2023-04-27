package com.proyecto.routes

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.proyecto.dto.CreateUsuario
import com.proyecto.mappers.toUsuario
import com.proyecto.mappers.toUsuarioDTO
import com.proyecto.services.usuarios.IUsuarioService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject
import java.lang.IllegalArgumentException

fun Application.usuarioRoutes(){
    val service : IUsuarioService by inject()

    routing {
        route("/usuario"){
            get {
                val usuarios = service.getAll().toList()

                if (usuarios.isNotEmpty()){
                    call.respond(HttpStatusCode.OK,usuarios)
                }else{
                    call.respond(HttpStatusCode.NotFound,"No hay usuarios.")
                }

            }

            get("/{id}") {
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
                    call.respond(HttpStatusCode.BadRequest,"No ha si posible buscar el usuario.")
                }

            }

            post {
                val usuario = call.receive<CreateUsuario>()
                service.create(usuario.toUsuario())
                    .onSuccess {
                        call.respond(HttpStatusCode.Created,it.toUsuarioDTO())
                    }
                    .onFailure {
                        call.respond("ERROR ${it.message}")
                    }

            }
            delete("/{id}") {
                try {
                    val id = ObjectId(call.parameters["id"])
                    service.delete(id)
                        .onSuccess {
                            call.respond(HttpStatusCode.NoContent)
                        }
                        .onFailure {
                            call.respond(HttpStatusCode.NotFound,"ERROR ${it.message}")
                        }
                }catch(e: IllegalArgumentException){
                    call.respond(HttpStatusCode.BadRequest,"No ha si posible eliminar el usuario.")
                }

            }
        }
    }

}