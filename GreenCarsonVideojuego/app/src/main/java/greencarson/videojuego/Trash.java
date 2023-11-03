package greencarson.videojuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Trash {
    public final int trashTypeMine;
    final Bitmap[] trash =new Bitmap[14];
    final Random random = new Random();
    int trashFrame, trashSum;
    float trashX, trashY, trashVelocity, oldTrashX, oldTrashY, oldX, oldY, shiftX, shiftY;

    //Constructor
    public Trash(Context context, int trashType, int levelNumber){

        trashTypeMine= trashType;

        //Lo esperado es que se tengan 9 elementos de cada tipo de basura (5 en el último bote hasta ahora)

        //Basura tipo A - Valorizables
        trash[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a1);
        trash[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a2);
        trash[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a3);

        //Basura tipo B - Orgánicos
        trash[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b1);
        trash[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b2);
        trash[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b3);

        //Basura tipo C - Inorgánicos
        trash[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c1);
        trash[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c2);
        trash[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c3);
        trash[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c4);
        trash[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c5);

        //Basura tipo D - Manejo Especial
        trash[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_d1);
        trash[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_d2);
        trash[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_d3);

        //Crear estado y posición inicial
        resetTrash(trashType, levelNumber);
    }

    public Bitmap getTrash(int trashFrame){
        return trash[trashFrame];
    }

    public int getTrashWidth(){
        return trash[0].getWidth();
    }

    public int getTrashHeight(){
        return trash[0].getHeight();
    }

    public void resetTrash(int trashType, int levelNumber) {
        trashX = random.nextInt(GameView.dWidth - getTrashWidth());
        trashY = -200 + random.nextInt(600) * -1;
        //Falta ver si vale la pena o no tener velocidad individual o por categoría
        trashVelocity = 4 + random.nextInt(4);

        //Aleatorizar gráfico de acuerdo con el tipo
/*        if (trashType == 1){
            trashSum=0;
        } else if (trashType == 2){
            trashSum=3;
        } else if (trashType == 3){
            trashSum=8;
        } else if (trashType == 4){
            trashSum=11;
        }*/

        switch (trashType) {
            case 1: trashSum = 0; break;
            case 2: trashSum = 3; break;
            case 3: trashSum = 8; break;
            case 4: trashSum = 11; break;
            default: break;
        }
        //AQUI EL 3 DEBE SER CAMBIADO A 9 CUANDO TENGAMOS SUFICIENTES ASSETS
        trashFrame = new Random().nextInt(3 + trashSum);
        //trashFrame = new Random().nextInt(levelNumber + trashSum);
    }

}
