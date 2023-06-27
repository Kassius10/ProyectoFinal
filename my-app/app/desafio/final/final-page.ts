import { Button, EventData, NavigatedData, Page } from "@nativescript/core";
import { SecureStorage } from "@nativescript/secure-storage";
import { Evento } from "~/home/shared/evento";
import {Http } from "@nativescript/core";
import { Usuario } from "~/login/shared/usuario";

var evento: Evento
var tiempofinal: string
var username: string
export function onNavigatingTo(args: NavigatedData){
   const page = args.object as Page;
   evento = args.context as Evento;
   page.actionBarHidden = true;  

   getTime(page)
}

function getTime(page: Page) {
   const secureStorage = new SecureStorage();
   const usuario = secureStorage.getSync({key: "usuario"})

   const user = JSON.parse(usuario) as Usuario
   username = user.userName

   const timeInicial = secureStorage.getSync({key: "tiempo_inicial"})
   secureStorage.removeSync({key: "tiempo_inicial"})

   const timeFinal = new Date().getTime().toString();

   const time = parseInt(timeFinal) - parseInt(timeInicial);
   
   const horas = Math.floor(time / 3600000);
   const minutos = Math.floor((time % 3600000) / 60000);
   const segundos = Math.floor(((time % 3600000) % 60000) / 1000);
   const milisegundos = Math.floor((((time % 3600000) % 60000) % 1000) / 10);

   tiempofinal = `${horas}:${minutos}:${segundos}:${milisegundos}`;

   page.bindingContext = {time: tiempofinal};
}

export function onTap(args: EventData){
   const button = args.object as Button;
   const page = button.page as Page;

   console.log(username)
   console.log(tiempofinal)
   Http.request({
      url: "https://api-ginkanago.onrender.com/ranking/"+ evento.id,
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      content: JSON.stringify({
         userName : username,
         tiempo: tiempofinal
     })
   }).then((response) => {
      console.log(response.content.toString);
      console.log(response.statusCode);
   }, (e) => {
      console.log(e);
   });

   page.frame.navigate({
      moduleName: "./app-root",
      clearHistory: true
   });
}