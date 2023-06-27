import { Observable } from '@nativescript/core'
import { Evento } from './shared/evento'

export class HomeViewModel extends Observable {
  datosApi: Evento[]

  constructor() {
    super()
  }
}
