import { EventData, View, NavigatedData, Page, Button } from '@nativescript/core'
import { Evento } from '../shared/evento'
import { SecureStorage } from '@nativescript/secure-storage'

export function onNavigatingTo(args: NavigatedData) {
  const page = args.object as Page
  const item = args.context as Evento
  page.bindingContext = item
}

export function onBackButtonTap(args: EventData) {
  const view = args.object as View
  const page = view.page as Page

  page.frame.goBack()
}

export function onTap(args: EventData) {
  const button = args.object as Button
  const page = button.page as Page
  const event = page.bindingContext as Evento
  const secureStorage = new SecureStorage()

  const page_root = page.frame.parent.page
  const exist = secureStorage.getSync({ key: 'desafio' })

  if (exist != null) {
    page_root.frame.navigate({
      moduleName: './desafio/desafio-page',
      context: event,
      clearHistory: true,
    })
  } else{
    page_root.frame.navigate({
      moduleName: './desafio/inicio/inicio-page',
      context: event,
      clearHistory: false,
    })
  }
}
