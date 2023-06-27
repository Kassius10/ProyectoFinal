import { NavigatedData, Page, EventData} from '@nativescript/core'
import { SecureStorage } from '@nativescript/secure-storage'
import { Button } from '@nativescript/core'
import { Usuario } from '~/login/shared/usuario'
import { Dialogs } from '@nativescript/core'

const secureStorage = new SecureStorage()
var user: Usuario

export function onNavigatingTo(args: NavigatedData) {
  const page = <Page>args.object
}

export function onLoaded(args: EventData) {
  const page = <Page>args.object
  const usuario = secureStorage.getSync({key: "usuario"})

  user = JSON.parse(usuario) as Usuario

  page.bindingContext = {
    name: user.name,
    surname: user.surname,
    username: user.userName,
    email: user.email,
    dateBirth: new Date(user.dateBirth).toISOString().slice(0, 10)
  }

}


export function logout(args: EventData) {
  const button = <Button>args.object;
  const page = <Page>button.page;

  const confirmOptions = {
    title: 'Cerrar Sesión',
    message: '¿Deseas cerrar sesión?',
    okButtonText: 'Si',
    cancelButtonText: 'No',
    style: "dialog-style"
  }

  Dialogs.confirm(confirmOptions).then(result => {
    if(result){
      secureStorage.removeAll()

      const page_root = page.frame.parent.page

      page_root.frame.navigate({
        moduleName: 'login/login-page',
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
  })
}

export function onEdit(args: EventData){
  const button = <Button>args.object;
  const page = <Page>button.page;

  page.frame.navigate({
    moduleName: 'perfil/editar/editar-page',
    context: user,
    animated: true,
    transition: {
      name: 'slide',
      duration: 200,
      curve: 'ease',
    },
    clearHistory: false
  })

}