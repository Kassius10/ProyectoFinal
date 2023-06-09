import { Button, EventData, Label, NavigatedData, Page, ScrollView } from "@nativescript/core";
import { GoogleMap, MapReadyEvent, MapView, Coordinate } from "@nativescript/google-maps";
import { requestPermission, hasPermission } from "nativescript-permissions";
import { getCurrentLocation, Location } from "@nativescript/geolocation";
import { Http } from "@nativescript/core";
import { BarcodeScanner } from "nativescript-barcodescanner";
import {decode} from "base-64";
import { Desafio, Direccion, Evento } from "~/home/shared/evento";
import { SecureStorage } from "@nativescript/secure-storage";

var page: Page
var evento: Evento
var desafio: Desafio
var numDesafios= 0
var numDesafio= 0

export function onNavigatingTo(args: NavigatedData) {
   page = args.object as Page;
   evento = args.context as Evento;
   const map = page.getViewById("mapView") as MapView;
   const button = page.getViewById("btnNext") as Button;
   const result = page.getViewById("txtResultado") as Label;
   const scroll = page.getViewById("scrollView") as ScrollView;
   scroll.scrollToVerticalOffset(0, false);

   const secureStorage = new SecureStorage();
   const exist = secureStorage.getSync({key: "desafio"})

   if(exist!= null) {
      numDesafio = parseInt(exist);
   }

   numDesafios = evento.desafios.length;
   desafio = evento.desafios[numDesafio];

   result.visibility = "hidden";
   button.visibility = "hidden";

   page.bindingContext = desafio;
   page.actionBarHidden = true;
   map.zoom = 15;
}

export function onReady(event: MapReadyEvent) {
   const fineLocationPermissionGranted = android.Manifest.permission.ACCESS_FINE_LOCATION;
   const page = event.object as Page;
   const mapView = page.getViewById("mapView") as MapView;
   const desafio = page.bindingContext as Desafio;

   if (!hasPermission(fineLocationPermissionGranted)) {
      requestPermission(fineLocationPermissionGranted)
         .then(() => {
            const map: GoogleMap = event.map;
            map.myLocationEnabled = true;
            map.uiSettings.zoomControlsEnabled = true;

            getCurrentLocation({ desiredAccuracy: 3, updateDistance: 10, maximumAge: 20000, timeout: 20000 })
               .then((location) => {
                  mapView.lat = location.latitude;
                  mapView.lng = location.longitude;
                  drawApi(location, map, desafio.direccion)
               })

            console.log("Permiso concedido despues de pedirlo")
         })
         .catch((e) => console.log("Permiso denegado, error " + e))
   } else {
      const map: GoogleMap = event.map;
      map.myLocationEnabled = true;
      map.uiSettings.zoomControlsEnabled = true;
      getCurrentLocation({ desiredAccuracy: 3, updateDistance: 10, maximumAge: 20000, timeout: 20000 })
         .then((location) => {
            mapView.lat = location.latitude;
            mapView.lng = location.longitude;
            drawApi(location, map, desafio.direccion)
         })
   }
}

function drawApi(location: Location, map: GoogleMap, direccion: Direccion ) {
   const origin = location.latitude + "," + location.longitude;
   const destination = direccion.latitud + "," + direccion.longitud;
   const key = "AIzaSyD49pC3uEyoPTgcsHTzdF2naW9xjDEXveg"
   Http.request({
      url: "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&key=" + key + "&mode=walking",
      method: "GET",
   }).then((response) => {

      map.addMarker(
         {
            position: {
               lat: Number(direccion.latitud),
               lng: Number(direccion.longitud)
            },
            title: "LLegada",
            color: "#253649"
         })

      const data = response.content.toJSON();
      const points: string = data.routes[0].overview_polyline.points;
      const decodedPoints: Coordinate[] = decodePolyline(points);

      map.addPolyline({
         points: decodedPoints,
         color: "#5b81ac",
      });

   })
}

function decodePolyline(points: string): Coordinate[] {
   const polyline = require('@mapbox/polyline');
   return polyline.decode(points).map((point: number[]) => ({ lat: point[0], lng: point[1] }));
}

export function onScanQR(args: EventData) {
   const barcodescanner = new BarcodeScanner();
   barcodescanner.requestCameraPermission().then(() => {
      barcodescanner.scan({
         formats: "QR_CODE",
         cancelLabel: "Cancelar",
      }).then((result) => {
         const decodificado: string = decode(result.text)

         if(desafio.clave === decodificado){
            const button = args.object as Button;
            const page = button.page as Page;
            const buttonNext = page.getViewById("btnNext") as Button;
            const result = page.getViewById("txtResultado") as Label;

            result.visibility= "visible"
            buttonNext.visibility = "visible"
         }
      });
   })
}

export function onNextPage(args: EventData){
   numDesafio++
   const secureStorage = new SecureStorage();

   if(numDesafio < numDesafios){
      page.frame.navigate({
         moduleName: "desafio/desafio-page",
         context: evento,
         clearHistory: true,
      })
      secureStorage.setSync({key: "desafio", value: numDesafio.toString()})

   }else{
      numDesafio = 0
      page.frame.navigate({
         moduleName: "desafio/final/final-page",
         clearHistory: true,
         context: evento
      })
      secureStorage.removeSync({key: "desafio"})
   }
}

