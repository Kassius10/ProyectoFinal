package com.proyecto.routes

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.proyecto.dto.CreateEvento
import com.proyecto.mappers.toEvento
import com.proyecto.mappers.toEventoDTO
import com.proyecto.models.Evento
import com.proyecto.models.UserInfo
import com.proyecto.models.UserSession
import com.proyecto.services.eventos.IEventoService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.p
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
//        authenticate("auth-oauth-google") {
//            get("/login") {
//                // Redirects to 'authorizeUrl' automatically
//            }
//
//            get("/callback") {
//                val redirects = mutableMapOf<String, String>()
//                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
//                call.sessions.set(UserSession(principal!!.state!!, principal.accessToken))
//                val redirect = redirects[principal.state!!]
//                call.respondRedirect(redirect!!)
//            }
//
//        }
//        get("/in") {
//            call.respondHtml {
//                body {
//                    p {
//                        a("/login") { +"Login with Google" }
//                    }
//                }
//            }
//        }
//        get("/{path}") {
//            val userSession: UserSession? = call.sessions.get()
//            if (userSession != null) {
//                val userInfo: UserInfo = HttpClient().get("https://www.googleapis.com/oauth2/v2/userinfo") {
//                    headers {
//                        append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
//                    }
//                }.body()
//                call.respondText("Hello, ${userInfo.name}!")
//            } else {
//                val redirectUrl = URLBuilder("http://0.0.0.0:8080/login").run {
//                    parameters.append("redirectUrl", call.request.uri)
//                    build()
//                }
//                call.respondRedirect(redirectUrl)
//            }
//        }
    }
}