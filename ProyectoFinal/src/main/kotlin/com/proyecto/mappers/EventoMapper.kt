package com.proyecto.mappers

import com.proyecto.dto.CreateEvento
import com.proyecto.dto.EventoDTO
import com.proyecto.models.Desafio
import com.proyecto.models.Evento
import org.bson.types.ObjectId
import java.time.LocalDateTime

fun CreateEvento.toEvento(): Evento{
    return Evento(
        id = ObjectId(),
        nombre = this.name,
        descripcion = this.descripcion,
        fecha = LocalDateTime.now(),
        lugar = this.lugar,
        imagen = this.imagen ?: "url", //TODO poner url por defecto
        desafios = this.desafios
    )
}
fun Evento.toEventoDTO(): EventoDTO {
    return EventoDTO(
        name = this.nombre,
        descripcion = this.descripcion,
        fecha = this.fecha,
        lugar = this.lugar,
        imagen = this.imagen ?: "",
        desafios = this.desafios
    )
}