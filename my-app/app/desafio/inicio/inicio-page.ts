import { Button, EventData, Label, NavigatedData, Page } from "@nativescript/core";
import { Evento } from "~/home/shared/evento";
import { SecureStorage } from "@nativescript/secure-storage";

export function onNavigatingTo(args: NavigatedData) {
   const page = args.object as Page;
   const event = args.context as Evento;
   page.bindingContext = event;
   page.actionBarHidden = true;


}
export function nextPage(args: EventData) {
   const button = args.object as Button;
   const page = button.page as Page;
   const text = page.getViewById("text") as Label;
   const event = page.bindingContext as Evento;

   if (button.text != "Comenzar") {

      text.text = "Como en todos los eventos, tendras que encontrar las pistas que vamos dejando para poder finalizar el desafio y pasar con el siguiente. \n" +
      "Veras un mapa que te indicara la ruta donde debes ir y dentro del area deberas buscar nuestra pista y escanearla. \n" +
      "Esperamos que te lo pases genial y disfrutes de la experiencia. \n" +
      "Mucha suerte !!"
      text.fontSize = 20;
      button.text = "Comenzar";
      button.fontSize = 20;
   } else {
      page.frame.navigate({
         moduleName: "./desafio/desafio-page",
         context: event,
         clearHistory: true
      });
      const secureStorage = new SecureStorage();
      secureStorage.setSync({key: "tiempo_inicial", value: new Date().getTime().toString()});
   }

}