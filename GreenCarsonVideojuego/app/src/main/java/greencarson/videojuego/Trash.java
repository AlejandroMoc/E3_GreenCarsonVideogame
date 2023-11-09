package greencarson.videojuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Trash {
    public final int trashTypeMine;
    final Bitmap[] trash =new Bitmap[32];
    final Random random = new Random();
    int trashFrame, trashSum, trashVelocity;
    float trashX, trashY, oldTrashX, oldTrashY, oldX, oldY, shiftX, shiftY;

    //Constructor
    public Trash(Context context, int trashType, int levelNumber){

        //FALTA AQUI ESTO SIMPLIFICAR (PARECE QUE NO SE PUEDE)
        trashTypeMine= trashType;

        //FALTA AQUI reemplazar por otros assets (algunos, no todos)

        //Basura tipo A - Valorizables
        trash[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a1);
        trash[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a2);
        trash[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a3);
        trash[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a1);
        trash[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a2);
        trash[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a3);
        trash[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a1);
        trash[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a2);
        trash[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_a3);

        //Basura tipo B - Orgánicos
        trash[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b1);
        trash[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b2);
        trash[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b3);
        trash[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b1);
        trash[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b2);
        trash[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b3);
        trash[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b1);
        trash[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b2);
        trash[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_b3);

        //Basura tipo C - Inorgánicos
        trash[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c1);
        trash[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c2);
        trash[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c3);
        trash[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c4);
        trash[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c5);
        trash[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c1);
        trash[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c2);
        trash[25] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c3);
        trash[26] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_c4);

        //Basura tipo D - Manejo Especial
        trash[27] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_d1);
        trash[28] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_d2);
        trash[29] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_d3);
        trash[30] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_d1);
        trash[31] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_d2);

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

        //Gráfico aleatorio de acuerdo con el tipo
        switch (trashType) {
            case 1:
                trashSum = 0;
                break;
            case 2:
                trashSum = 9;
                break;
            case 3:
                trashSum = 18;
                break;
            case 4:
                trashSum = 27;
                break;
            default:
                break;
        }

        //AQUI AHORA ESTO SE DEBE AJUSTAR DE ACUERDO CON EL NIVEL (3 BASURAS INICIALES PARA UN NIVEL, 6 PARA EL 2, 9 PARA EL 3)
        if (levelNumber < 4) {
            trashFrame = new Random().nextInt(levelNumber * 3) + trashSum;
        } else {
            trashFrame = new Random().nextInt(levelNumber + 1) + trashSum;
        }

        //FALTA VER SI ESTÁ BIEN ASÍ
        trashVelocity = levelNumber*2 + random.nextInt(3);
        //trashVelocity = 4 + random.nextInt(4);
    }

}
