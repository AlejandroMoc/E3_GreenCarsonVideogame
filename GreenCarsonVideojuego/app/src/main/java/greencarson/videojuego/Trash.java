package greencarson.videojuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Trash {
    public final int trashTypeMine;
    final Bitmap[] trash =new Bitmap[9];
    final Random random = new Random();
    int trashFrame;
    float trashX, trashY, trashVelocity, oldTrashX, oldTrashY, oldX, oldY, shifX, shiftY;

    //Constructor
    public Trash(Context context, int trashType){

        trashTypeMine= trashType;

        //Basura tipo A
        trash[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a1);
        trash[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a2);
        trash[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a3);

        //Basura tipo B
        trash[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b1);
        trash[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b2);
        trash[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b3);

        //Basura tipo C
        trash[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c1);
        trash[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c2);
        trash[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c3);

        //Crear estado y posición inicial
        resetTrash(trashType);
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

    public void resetTrash(int trashType) {
        trashX = random.nextInt(GameView.dWidth - getTrashWidth());
        trashY = -200 + random.nextInt(600) * -1;
        //Falta ver si vale la pena o no tener velocidad individual o por categoría
        trashVelocity = 4 + random.nextInt(4);

        //Aleatorizar gráfico de acuerdo con el tipo
        //AQUI AHORA falta colapsar esto para usar un solo valor +algo
        if (trashType == 1){
            trashFrame = new Random().nextInt(3);
        } else if (trashType == 2){
            trashFrame = new Random().nextInt(3) + 3;
        } else if (trashType == 3){
            trashFrame = new Random().nextInt(3) + 6;
        } else if (trashType == 4){
            trashFrame = new Random().nextInt(3);
        }
    }
}
