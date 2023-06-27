import { EventData, View, NavigatedData, Page, ObservableArray } from '@nativescript/core'
import { Evento } from '../shared/evento'

export function onNavigatingTo(args: NavigatedData) {
  const page = args.object as Page
  const item = args.context as Evento

  const rankingConPosicion = item.ranking.map((usuario, index) => {
    return { ...usuario, posicion: index + 1 };
  });

  const viewModel = {
    ranking: new ObservableArray(rankingConPosicion),
  }
  page.bindingContext = viewModel
}

export function onBackButtonTap(args: EventData) {
  const view = args.object as View
  const page = view.page as Page

  page.frame.goBack()
}