import { EventData, NavigatedData, Page } from '@nativescript/core'
import { Button } from '@nativescript/core/ui/button'
import { TextField } from '@nativescript/core/ui/text-field'
import { Http } from '@nativescript/core'

export function onNavigatingTo(args: NavigatedData) {
   const page = args.object as Page
   page.actionBarHidden = true
}

export function register(args: EventData) {
   const button = <Button>args.object;
   const page = <Page>button.page;

   const name = <TextField>page.getViewById("name");
   const firstSurname = <TextField>page.getViewById("firstSurname");
   const lastSurname = <TextField>page.getViewById("lastSurname");
   const username = <TextField>page.getViewById("username");
   const email = <TextField>page.getViewById("email");
   const password = <TextField>page.getViewById("password");
   const confirmPassword = <TextField>page.getViewById("confirmPassword");
   const fecha = page.getViewById("fecha") as TextField;

   var messageError: string = "";

   if (name.text === "") {
      messageError += "El campo nombre no puede estar vacio.\n";
   }
   if (firstSurname.text === "") {
      messageError += "El campo primer apellido no puede estar vacio.\n";
   }
   if (lastSurname.text === "") {
      messageError += "El campo segundo apellido no puede estar vacio.\n";
   }

   if (username.text === "") {
      messageError += "El campo nombre de usuario no puede estar vacio.\n";
   }
   if (email.text === "") {
      messageError += "El campo email no puede estar vacio.\n";
   }
   if (!validatePassword(password.text)) {
      messageError += "La contraseña debe tener al menos 8 caracteres, una mayuscula, una minuscula y un numero.\n";
   }
   if (password.text !== confirmPassword.text) {
      messageError += "Las contraseñas no coinciden.\n";
   }
   if (fecha.text === "") {
      messageError += "La fecha no puede estar vacia.\n";
   }


   if (messageError == "") {
      createUser(name.text, firstSurname.text, lastSurname.text, username.text, email.text, password.text, fecha.text, page, button);
     
   } else {
      alert(messageError);
   }
}

function validatePassword(password: string): boolean {
   const pattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}$/;
   return pattern.test(password);
}

function navigateToLogin(page: Page, button: Button) {
   page.frame.navigate({
      moduleName: 'login/login-page',
      context: button,
      animated: true,
      transition: {
         name: 'slide',
         duration: 200,
         curve: 'ease',
      },
   })
}

function createUser(name: string, firstSurname: string, lastSurname: string, username: string, email: string, password: string, fecha: string, page: Page, button: Button){
   Http.request({
      url: "https://api-ginkanago.onrender.com/register",
      method: "POST",
      headers: { "Content-Type": "application/json" },
      content: JSON.stringify({
         userName : username,
         name: name,
         surname: firstSurname + " " + lastSurname,
         email: email,
         password: password,
         dateBirth: fecha
     })
    }).then(
      (response) => {
        if (response.statusCode >= 400) {
          alert(response.content.toString());
          return;
        }
        navigateToLogin(page, button);
      }, (e) => {
        console.log(e)
      })
}
