import { Button, Color, EventData, NavigatedData, Page, TextField } from "@nativescript/core";
import { Usuario } from "~/login/shared/usuario";
import { Http } from "@nativescript/core";
import { SecureStorage } from "@nativescript/secure-storage";

var user: Usuario
var secure = new SecureStorage();
export function onNavigatingTo(args: NavigatedData) {
  const page = <Page>args.object
  user = args.context as Usuario
  page.bindingContext = user
}

export function onLoaded(args: EventData) {
  const page = <Page>args.object
}

export function cancel(args: EventData) {
  const button = <Button>args.object;
  const page = <Page>button.page;

  page.frame.goBack()
}

export function focus(args: EventData) {
  const text = args.object as TextField;
  text.backgroundColor = "white"
  text.color = new Color("#253649")
}

export function blur(args: EventData) {
  const text = args.object as TextField;
  text.backgroundColor = "#253649"
  text.color = new Color("#ffffff")
}

export function save(args: EventData) {
  const button = <Button>args.object;
  const page = <Page>button.page;
  const name = page.getViewById<TextField>("name")
  const surname = page.getViewById<TextField>("surname")
  const username = page.getViewById<TextField>("username")
  const email = page.getViewById<TextField>("email")
  const dateBirth = page.getViewById<TextField>("fecha")
  const token = secure.getSync({ key: "token" })

  const usuario: Usuario = {
    id: user.id,
    email : email.text,
    name : name.text,
    surname : surname.text,
    token : token,
    userName : username.text,
    dateBirth : new Date(dateBirth.text)
  }

  console.log(usuario.id)
  Http.request({
    url: "http:192.168.3.25:8080/usuario/" + user.id,
    method: "PUT",
    headers: { "Content-Type": "application/json", "Authorization": "Bearer " + token },
    content: JSON.stringify({
      email : email.text,
      name : name.text,
      surname : surname.text,
      userName : username.text,
      dateBirth : dateBirth.text
    })
  }).then(
    (response) => {
      if(response.statusCode < 300){
        const usuarioJson = JSON.stringify(usuario)
        secure.setSync({key: "usuario", value: usuarioJson })
        alert("Usuario actualizado correctamente")
        page.frame.goBack()
      }else{
        alert("Error al actualizar el usuario")
        console.log(response.statusCode)
        console.log(response.content)
      }
    }
  ), (e) => {
    console.log(e)
  }
}