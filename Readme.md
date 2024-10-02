Eliminada API KEY para Google Maps por motivos de seguridad. Para la reproducción de este proyecto es necesario introducir una nueva Key para el funcionamiento de los mapas.

EJERCICIO 1

El ejercicio 1 se compone de dos clases, la propia clase de la actividad, Ejercicio1, y la clase AreaDibujo que será la encargada de recoger los trazos realizados en la pantalla.

Esta clase AreaDibujo hereda de View, e incorpora los métodos onDraw() y onTouchEvent() de la clase View. Estos métodos han sido modificados para darle la funcionalidad deseada. También incorpora los métodos que borran el dibujo completo y borra el último trazo realizado. Esta clase la he hecho en Java, ya que en Kotlin no funcionaba de la misma forma.

Al heredar de View, podemos introducir la vista en el layout de la actividad. Mediante esta actividad podremos usar la clase AreaDibujo para realizar los dibujos que queramos.

He añadido un Toolbar con 5 iconos (un poco recargado pero me gustaba más de esta manera que con una lista). Los iconos de izquierda a derecha realizan las tareas de borrar el último trazo, borrar todo el dibujo, guardar el dibujo, cambiar el color y cambiar el tamaño del trazo.

Por último, al pulsar el botón para guardar el dibujo, se crea una imagen Bitmap, a la cual añado un fondo blanco, ya que por defecto no hay fondo de imagen. Se guarda en el directorio Pictures mediante el nombre "dibujo_x.png", siendo "x" el número del dibujo, previamente comprobado que esté libre para así no sobreescribir ningún otro dibujo anterior.

Los permisos utilizados son write y read external storage.


EJERCICIO 2

Este ejercicio es similar y complementario a la tarea online 5 de HLC. He usado básicamente casi la misma estructura, añadiendole la comprobación de los permisos de localización. He realizado el mismo proceso que en esta otra tarea de añadir la clave API de Google Maps, que son los mapas utilizados para la realización del ejercicio.

Al igual que la tarea 5 de HLC, muestra un marcador previamente establecido, haciendo zoom con una animación sobre él. Cuando se pulsa el botón relativo a la ubicación en tiempo real (parte superior derecha del mapa), se visualiza un TextView con la latitud y la longitud en la parte inferior que se queda establecido y el mapa navega hasta la ubicación actual. En caso de cambiar de ubicación, sería necesario volver a pulsar el botón par que cambie de nuevo el contenido del TextView.

Los permisos necesarios para el funcionamiento de la ubicación son ACCESS_FINE_LOCATION y ACCESS_COARSE_LOCATION. Este último era requerido por el Manifest para que funcionara correctamente.