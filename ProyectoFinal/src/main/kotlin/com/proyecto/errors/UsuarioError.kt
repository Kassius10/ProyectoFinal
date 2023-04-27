package com.proyecto.errors

sealed class UsuarioError(val message: String){
    class NoExist(message: String): UsuarioError(message)
    class Exist(message: String): UsuarioError(message)
}