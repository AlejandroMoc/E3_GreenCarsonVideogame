/*
    Nombre del archivo: ExplosionComponent.java
    Nombre del proyecto: Green Carson Reecicla! Game

    Creado y Desarrollado por:

    César Guerra Martínez
    Alejandro Daniel Moctezuma Cruz

    En colaboración con el Instituto Tecnológico y
    de Estudios Superiores de Monterrey y la empresa Green Carson.
*/

package greencarson.videojuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ExplosionComponent {
    final Bitmap[] explosion = new Bitmap[4];
    int explosionFrame = 0;
    float explosionX, explosionY;

    //Constructor
    public ExplosionComponent(Context context){
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explote0);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explote1);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explote2);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explote3);
    }

    public Bitmap getFrame(int explosionFrame){
        return explosion[explosionFrame];
    }
}
