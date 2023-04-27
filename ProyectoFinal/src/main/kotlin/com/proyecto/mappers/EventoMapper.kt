package com.proyecto.mappers

import com.proyecto.dto.CreateEvento
import com.proyecto.dto.EventoDTO
import com.proyecto.models.Evento
import org.bson.types.ObjectId
import java.time.LocalDateTime

fun CreateEvento.toEvento(): Evento{
    return Evento(
        id = ObjectId(),
        nombre = this.name,
        fecha = LocalDateTime.now(),
        lugar = this.lugar,
        imagen = this.imagen ?: "url" //TODO poner url por defecto
    )
}
fun Evento.toEventoDTO(): EventoDTO {
    return EventoDTO(
        name = this.nombre,
        fecha = this.fecha,
        lugar = this.lugar,
        imagen = this.imagen ?: ""
    )
}