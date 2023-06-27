import { EventData, NavigatedData, Page } from '@nativescript/core'
import { LoginViewModel } from './login-view-model'
import { TextField } from '@nativescript/core/ui/text-field'
import { Button } from '@nativescript/core/ui/button'
import { Label } from '@nativescript/core/ui/label'
import { SecureStorage } from '@nativescript/secure-storage'
import { Http } from '@nativescript/core'
import { Usuario } from './shared/usuario'

export function onNavigatingTo(args: NavigatedData) {
  const page = <Page>args.object
  page.bindingContext = new LoginViewModel()
  page.actionBarHidden = true
}

export function onLogin(args: EventData) {
  const button = <Button>args.object;
  const page = <Page>button.page;
  const password = <TextField>page.getViewById("password");
  const username = <TextField>page.getViewById("username");

  if (password.text === "" || username.text === "") {
    alert("Los campos no pueden estar vacios.");
    password.text = "";
    username.text = "";
  } else {
    Http.request({
      url: "https://api-ginkanago.onrender.com/login",
      method: "POST",
      headers: { "Content-Type": "application/json" },
      content: JSON.stringify({ userName: username.text, password: password.text })
    }).then(
      (response) => {

        if (response.statusCode != 200) {
          alert("Usuario o contraseña incorrectos");
          password.text = "";
          username.text = "";
          return;
        }
        const usuario: Usuario = response.content.toJSON()
        const secureStorage = new SecureStorage();
        secureStorage.setSync({key: "usuario", value: JSON.stringify(usuario)})
        secureStorage.setSync({ key: "token", value: usuario.token });
        console.log("Almacenando token");

        navigationToHome(page, button)
      }, (e) => {
        console.log(e)
        alert("Error de conexión");
      })

  }
}

export function onRegister(args: EventData) {
  const label = <Label>args.object;
  const page = <Page>label.page;

  page.frame.navigate({
    moduleName: 'login/register/register-page',
    context: label,
    animated: true,
    clearHistory: false,
    transition: {
      name: 'slide',
      duration: 200,
      curve: 'ease',
    },
  })
}

function navigationToHome(page: Page, button: Button) {
  page.frame.navigate({
    moduleName: 'app-root',
    context: button,
    animated: true,
    clearHistory: true,
    transition: {
      name: 'slide',
      duration: 200,
      curve: 'ease',
    },
  })
}