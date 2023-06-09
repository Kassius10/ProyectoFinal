import { View, ItemEventData, NavigatedData, Page, StackLayout, Style, PanGestureEventData, EventData, Button } from '@nativescript/core'
import { Http } from '@nativescript/core'
import { format } from "date-fns"

import { HomeViewModel } from './home-view-model'
import { Evento } from './shared/evento'
import { SecureStorage } from '@nativescript/secure-storage'


var secure = new SecureStorage()

export function onNavigatingTo(args: NavigatedData) {
  const page = <Page>args.object
  const token = secure.getSync({key: "token"})
  load(page, token)
}
export function tap(args: PanGestureEventData) {
  const state = args.state
  const page = args.object as Page
  const token = secure.getSync({key: "token"})

  if (state === 3) {
    load(page, token)
  }
}
export function onLoaded(args: EventData) {
  const page = args.object as Page
  const token = secure.getSync({key: "token"})

  load(page, token)
}



export function onItemSelected(args: ItemEventData) {
  const view = <View>args.view
  const page = <Page>view.page
  const tappedItem = <Evento>view.bindingContext

  page.frame.navigate({
    moduleName: 'home/home-evento-detail/home-evento-detail-page',
    context: tappedItem,
    animated: true,
    transition: {
      name: 'slide',
      duration: 200,
      curve: 'ease',
    },
  })
}

export function lookRanking(args: EventData){
  const button = args.object as Button
  const page = button.page
  const evento = button.bindingContext as Evento

  page.frame.navigate({
    moduleName: 'home/evento-ranking/evento-ranking-page',
    context: evento,
    animated: true,
    transition: {
      name: 'slide',
      duration: 200,
      curve: 'ease',
    },
  })
}

function load(page: Page, token: string){
  Http.request({
    url: "http:192.168.3.25:8080/evento",
    method: "GET",
    headers: { "Authorization": "Bearer " + token }
  })
  .then(
    (response) =>{
    const viewModel = new HomeViewModel()
    viewModel.datosApi = []
    viewModel.datosApi = response.content.toJSON()
    viewModel.datosApi.forEach(dato =>{
      dato.fecha = format(new Date(dato.fecha), 'dd/MM/yyyy hh:mm')
    })
    page.bindingContext = viewModel

    
  },(e) =>{
    console.log(e)
  })
}
