package com.proyecto.routes

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.proyecto.dto.CreateEvento
import com.proyecto.mappers.toEvento
import com.proyecto.mappers.toEventoDTO
import com.proyecto.services.eventos.IEventoService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject
import java.lang.IllegalArgumentException

fun Application.eventoRoutes(){
    val service : IEventoService by inject()

    routing {
        route("/evento"){
            get {
                val eventos = service.getAll().toList()

                if (eventos.isNotEmpty()){
                    call.respond(HttpStatusCode.OK,eventos)
                }else{
                    call.respond(HttpStatusCode.NotFound,"No hay eventos.")
                }
            }
            get("/{id}") {
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
                    call.respond(HttpStatusCode.BadRequest,"No ha si posible buscar el evento.")
                }

            }

            post {
                val evento = call.receive<CreateEvento>()
                service.create(evento.toEvento())
                    .onSuccess {
                        call.respond(HttpStatusCode.Created,it.toEventoDTO())
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
                    call.respond(HttpStatusCode.BadRequest,"No ha si posible eliminar el evento.")
                }

            }
        }
    }
}