package com.proyecto.routes

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.proyecto.dto.CreateEvento
import com.proyecto.dto.UpdateEvento
import com.proyecto.mappers.toEvento
import com.proyecto.mappers.toEventoDTO
import com.proyecto.mappers.toUsuarioDTO
import com.proyecto.models.Ranking
import com.proyecto.services.database.DatabaseContext
import com.proyecto.services.eventos.IEventoService
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
import java.lang.IllegalArgumentException

fun Application.eventoRoutes(){
    val service : IEventoService by inject()

    routing {
        route("/evento"){
            authenticate {
                get {
                    val rol = getRol()
                    if (rol.equals("ADMIN")){
                        val eventos = service.getAll().toList()
                        if (eventos.isNotEmpty()){
                            call.respond(HttpStatusCode.OK,eventos)
                        }else{
                            call.respond(HttpStatusCode.NotFound,"No hay eventos.")
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
                                    call.respond(HttpStatusCode.OK,it.toEventoDTO())
                                }
                                .onFailure {
                                    call.respond(HttpStatusCode.NotFound,"ERROR ${it.message}")
                                }
                        }catch(e: IllegalArgumentException){
                            call.respond(HttpStatusCode.BadRequest,"No ha sido posible buscar el evento.")
                        }
                    }
                    call.respond(HttpStatusCode.Forbidden,"No tiene permisos suficientes.")
                }

                post {
                    val rol = getRol()
                    if (rol.equals("ADMIN")){
                        try {
                            val evento = call.receive<CreateEvento>()
                            service.create(evento.toEvento())
                                .onSuccess {
                                    call.respond(HttpStatusCode.Created,it.toEventoDTO())
                                }
                                .onFailure {
                                    call.respond("ERROR ${it.message}")
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
                    if (rol.equals("ADMIN")){
                        try {
                            val id = ObjectId(call.parameters["id"])
                            val evento = call.receive<UpdateEvento>()

                            service.update(id, evento)
                                .onSuccess {
                                    call.respond(HttpStatusCode.OK,it.toEventoDTO())
                                }
                                .onFailure {
                                    if (it.message.contains("No existe")){
                                        call.respond(HttpStatusCode.NotFound,it.message)
                                    }else{
                                        call.respond(HttpStatusCode.BadRequest,it.message)
                                    }
                                }

                        }catch (e: IllegalArgumentException){
                            call.respond(HttpStatusCode.BadRequest,"No ha sido posible buscar el evento.")
                        }catch (e: BadRequestException){
                            call.respond(HttpStatusCode.BadRequest,"Los datos no son correctos.")
                        }
                    }
                    call.respond(HttpStatusCode.Forbidden,"No tiene permisos suficientes.")
                }

                delete("/{id}") {
                    val rol = getRol()
                    if(rol.equals("ADMIN")){
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
                            call.respond(HttpStatusCode.BadRequest,"No ha sido posible eliminar el evento.")
                        }
                    }
                    call.respond(HttpStatusCode.Forbidden,"No tiene permisos suficientes.")
                }
            }
        }
    }
}
private fun PipelineContext<Unit, ApplicationCall>.getRol(): String {
    val jwt = call.principal<JWTPrincipal>()
    return jwt?.payload?.getClaim("rol").toString().replace("\"","")
}