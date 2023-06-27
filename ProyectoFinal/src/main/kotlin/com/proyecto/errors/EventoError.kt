package com.proyecto.errors

sealed class EventoError(val message: String){
    class NoExist(message: String): EventoError(message)
    class Exist(message: String): EventoError(message)
}
