package com.proyecto.routes

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.proyecto.dto.CreateEvento
import com.proyecto.dto.EventoDTO
import com.proyecto.dto.UpdateEvento
import com.proyecto.mappers.toEvento
import com.proyecto.mappers.toEventoDTO
import com.proyecto.models.Evento
import com.proyecto.models.Ranking
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
import io.github.smiley4.ktorswaggerui.dsl.*

fun Application.eventoRoutes(){
    val service : IEventoService by inject()

    routing {
        route("/evento"){
            authenticate {
                get({
                    description = "Obtener todos los eventos"
                    response {
                        HttpStatusCode.OK to {
                            description = "Obtención de todos los eventos."
                            body<List<Evento>> { description = "Obtención de datos" }
                        }
                        HttpStatusCode.Forbidden to {
                            description = "No tiene permisos para realizar la accion."
                            body<String> { description = "No tiene permisos." }
                        }
                        HttpStatusCode.NotFound to {
                            description = "No se ha encontrado eventos a mostrar"
                            body<String> { description = "No hay eventos a mostrar." }
                        }
                    }
                }) {
                    val rol = getRol()
                    if (rol.equals("ADMIN") || rol.equals("USER")){
                        val eventos = service.getAll().toList()
                        if (eventos.isNotEmpty()){
                            call.respond(HttpStatusCode.OK,eventos)
                        }else{
                            call.respond(HttpStatusCode.NotFound,"No hay eventos.")
                        }
                    }
                    call.respond(HttpStatusCode.Forbidden,"No tiene permisos suficientes.")
                }

                get("/{id}", {
                    description = "Obtener un evento por id."
                    request {
                        pathParameter<String>("id") {
                            description = "Id del evento a buscar"
                            required = true
                        }
                    }
                    response {
                        HttpStatusCode.OK to {
                            description = "Obtención del evento por id."
                            body<EventoDTO> { description = "Evento obtenido." }
                        }
                        HttpStatusCode.Forbidden to {
                            description = "No tiene permisos para realizar la accion."
                            body<String> { description = "No tiene permisos." }
                        }
                        HttpStatusCode.NotFound to {
                            description = "No se ha encontrado el evento."
                            body<String> { description = "No existe el evento con la id" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "No ha sido posible buscar el evento."
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

                post({
                    description = "Crear un evento."
                    request {
                        body<CreateEvento> {
                            required = true
                        }
                    }
                    response {
                        HttpStatusCode.Created to {
                            description = "Evento creado"
                            body<EventoDTO> { description = "Evento obtenido." }
                        }
                        HttpStatusCode.Forbidden to {
                            description = "No tiene permisos para realizar la accion."
                            body<String> { description = "No tiene permisos." }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "No ha sido posible crear el evento."
                            body<String> { description = "Los datos del evento no son correctos." }
                        }
                    }
                }) {
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
                put("/{id}", {
                    description = "Actualizar datos de un evento por id."
                    request {
                        pathParameter<String>("id") {
                            description = "Id del evento a actualizar"
                            required = true
                        }
                        body<UpdateEvento> {
                            required = true
                        }
                    }
                    response {
                        HttpStatusCode.OK to {
                            description = "Actualizado correctamente el evento"
                            body<EventoDTO> { description = "Evento actualizado" }
                        }
                        HttpStatusCode.Forbidden to {
                            description = "No tiene permisos para realizar la accion."
                            body<String> { description = "No tiene permisos." }
                        }
                        HttpStatusCode.NotFound to {
                            description = "No se ha encontrado el evento."
                            body<String> { description = "No existe el evento con la id" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "No ha sido posible actualizar el evento."
                            body<String> { description = "Los datos del evento no son correctos." }
                        }
                    }
                }) {
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

                delete("/{id}", {
                    description = "Eliminar evento por id."
                    request {
                        pathParameter<String>("id") {
                            description = "Id del evento a eliminar"
                            required = true
                        }
                    }
                    response {
                        HttpStatusCode.NoContent to {
                            description = "Evento eliminado."
                        }
                        HttpStatusCode.Forbidden to {
                            description = "No tiene permisos para realizar la accion."
                            body<String> { description = "No tiene permisos." }
                        }
                        HttpStatusCode.NotFound to {
                            description = "No se ha encontrado el evento."
                            body<String> { description = "No existe el evento con la id" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "No ha sido posible eliminar el evento."
                            body<String> { description = "Los datos del evento no son correctos." }
                        }
                    }
                }) {
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
        route("/ranking"){
            put("/{id}",  {
                description = "Actualizar ranking de un evento por id."
                request {
                    pathParameter<String>("id") {
                        description = "Id del evento a actualizar el ranking"
                        required = true
                    }
                    body<Ranking> {
                        description = "Datos del ranking a añadir."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Actualizado correctamente el evento"
                        body<EventoDTO> { description = "Evento actualizado" }
                    }
                    HttpStatusCode.Forbidden to {
                        description = "No tiene permisos para realizar la accion."
                        body<String> { description = "No tiene permisos." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se ha encontrado el evento."
                        body<String> { description = "No existe el evento con la id" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No ha sido posible actualizar el evento."
                        body<String> { description = "Los datos del evento no son correctos." }
                    }
                }
            }){
                try {
                    val id = ObjectId(call.parameters["id"])
                    val ranking = call.receive<Ranking>()

                    service.updateRanking(id,ranking)
                        .onSuccess {
                            call.respond(HttpStatusCode.OK)
                        }
                        .onFailure {
                            call.respond(HttpStatusCode.NotFound,it.message)
                        }
                } catch (e: RequestValidationException){
                    call.respond(HttpStatusCode.BadRequest,e.reasons)
                }catch (e: BadRequestException){
                    call.respond(HttpStatusCode.BadRequest,"Los datos no son correctos.")
                }catch (e: IllegalArgumentException){
                    e.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest,"No ha sido posible buscar el evento.")
                }
            }
        }
    }
}
private fun PipelineContext<Unit, ApplicationCall>.getRol(): String {
    val jwt = call.principal<JWTPrincipal>()
    return jwt?.payload?.getClaim("rol").toString().replace("\"","")
}