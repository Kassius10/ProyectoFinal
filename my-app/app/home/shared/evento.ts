export interface Evento {
  desafios: Desafio[];
  ranking: Ranking[];
  descripcion: string;
  fecha: string;
  id: string;
  imagen: string;
  lugar: string;
  nombre: string;
}
export interface Ranking {
  userName: string;
  tiempo: string;
}

export interface Desafio {
  descripcion: string;
  direccion: Direccion;
  nombreDesafio: string;
  numDesafio: number;
  resultado: string;
  clave: string;
}

export interface Direccion {
  latitud: string;
  longitud: string;
}
