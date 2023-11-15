package greencarson.videojuego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

public class GameView extends View {

    Trash draggedTrash;
    final Bitmap background;
    final Bitmap ground;
    Bitmap dumpsterA, dumpsterB, dumpsterC, dumpsterD;
    final Bitmap heart;
    final Rect rectBackground;
    final Rect rectGround;
    final Rect rectHeart;
    final Drawable heartDrawable = ContextCompat.getDrawable(getContext(), R.drawable.logo_heart);
    final Drawable heartDrawableGolden = ContextCompat.getDrawable(getContext(), R.drawable.logo_heart_golden);
    final Context context;
    final Handler handler;
    final long UPDATE_MILLIS = 30;
    final Runnable runnable;
    final Paint pointsNumber = new Paint();
    final Paint lifeNumber = new Paint();
    final int pointsTextSize = 120;
    final int lifeTextSize = 70;
    final int dumpsterAX;
    final int dumpsterBX;
    final int dumpsterCX;
    final int dumpsterDX;
    final int dumpstersY;
    float newtrashyX, newtrashyY, touchX, touchY, dumpsterX;
    int points, pointsSum, action, i, trashType, winningState, minPoints, trashDensity, life;
    final int levelNumber;
    static int dWidth;
    static int dHeight;
    static final int heartSize=120;
    static final int heartMargin=100;
    boolean gameOver = false;
    final Random random;
    //FALTA AQUI VER CÓMO CONVERTIR A MAPA
    final ArrayList<Trash> trashesA;
    final ArrayList<Trash> trashesB;
    final ArrayList<Trash> trashesC;
    final ArrayList<Trash> trashesD;
    final ArrayList<Explosion> explosions;
    Explosion explosion;
    Trash trash;
    Iterator<Explosion> iterator;

    public GameView(Context context, int levelNumber){

        super(context);
        this.context = context;
        this.levelNumber = levelNumber;

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_tiles);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);

        //Para medidas de corazón
        heart = BitmapFactory.decodeResource(getResources(), R.drawable.logo_heart);
        dumpsterA = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_1);
        dumpsterB = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_2);
        dumpsterC = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_3);
        dumpsterD = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_4);

        //Por niveles
        if (levelNumber ==4){
            Log.d("4", "Se envia a nivel avanzado");
            dumpsterA = Bitmap.createScaledBitmap(dumpsterA, dumpsterA.getWidth()-dumpsterA.getWidth()/3, dumpsterA.getHeight()-dumpsterA.getHeight()/3, true);
            dumpsterB = Bitmap.createScaledBitmap(dumpsterB, dumpsterB.getWidth()-dumpsterB.getWidth()/3, dumpsterB.getHeight()-dumpsterB.getHeight()/3, true);
            dumpsterC = Bitmap.createScaledBitmap(dumpsterC, dumpsterC.getWidth()-dumpsterC.getWidth()/3, dumpsterC.getHeight()-dumpsterC.getHeight()/3, true);
            dumpsterD = Bitmap.createScaledBitmap(dumpsterD, dumpsterD.getWidth()-dumpsterD.getWidth()/3, dumpsterD.getHeight()-dumpsterD.getHeight()/3, true);
            minPoints = Integer.MAX_VALUE;
            trashDensity=2;
            life=10;
            pointsSum=10;

        } else if (levelNumber==3){
            Log.d("3", "Se envia a nivel avanzado");
            minPoints=300;
            trashDensity=2;
            life=5;
            pointsSum=10;

        } else if (levelNumber==2){
            Log.d("2", "Se envia a nivel intermedio");
            minPoints=250;
            trashDensity=2;
            life=5;
            pointsSum=10;

        } else if (levelNumber==1){
            Log.d("1", "Se envia a nivel básico");
            minPoints=200;
            trashDensity=1;
            life=5;
            pointsSum=10;
        }

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        //Rectangulos para fondo y piso
        rectBackground = new Rect(0,0,dWidth,dHeight);
        rectGround= new Rect(0,dHeight-ground.getHeight(),dWidth,dHeight);
        rectHeart = new Rect(0,0,dWidth,dHeight);
        handler = new Handler();
        runnable = this::invalidate;

        pointsNumber.setColor(ContextCompat.getColor(context, R.color.black));
        pointsNumber.setTextSize(pointsTextSize);
        pointsNumber.setTextAlign(Paint.Align.LEFT);
        pointsNumber.setTypeface(Typeface.DEFAULT_BOLD);
        //pointsNumber.setTypeface(ResourcesCompat.getFont(context, R.font.kenney_blocks));

        lifeNumber.setColor(ContextCompat.getColor(context, R.color.black));
        lifeNumber.setTextSize(lifeTextSize);
        lifeNumber.setTextAlign(Paint.Align.LEFT);
        lifeNumber.setTypeface(Typeface.DEFAULT_BOLD);

        Objects.requireNonNull(heartDrawable).setBounds(rectHeart.left, rectHeart.top, rectHeart.left + rectHeart.width(), rectHeart.top + rectHeart.height());
        Objects.requireNonNull(heartDrawableGolden).setBounds(rectHeart.left, rectHeart.top, rectHeart.left + rectHeart.width(), rectHeart.top + rectHeart.height());
        heartDrawable.setBounds(dWidth - heartSize - heartMargin, heartMargin, dWidth - heartMargin, heartMargin + heartSize);
        heartDrawableGolden.setBounds(dWidth - heartSize - heartMargin, heartMargin, dWidth - heartMargin, heartMargin + heartSize);

        random = new Random();

        //Posición de los botes
        //FALTA AQUI SIMPLIFICAR ESTO CON EL IF DE ARRIBA (no se si se pueda)
        if (levelNumber==4){
            dumpsterAX= Math.floorDiv(dWidth,20);
            dumpsterBX = Math.floorDiv(dWidth,3)-Math.floorDiv(dumpsterB.getWidth(),3);
            dumpsterCX = dWidth-dumpsterB.getWidth()-dumpsterAX;
            dumpsterDX = dWidth-dumpsterC.getWidth()-dumpsterBX;
            dumpstersY = dHeight - ground.getHeight() - dumpsterB.getHeight()+100;
        }
        else{
            dumpsterAX= Math.floorDiv(dWidth,20);
            dumpsterBX = Math.floorDiv(dWidth,2)-Math.floorDiv(dumpsterB.getWidth(),2);
            dumpsterCX = dWidth-dumpsterB.getWidth()-dumpsterAX;
            dumpsterDX = dWidth-dumpsterC.getWidth()-dumpsterBX;
            dumpstersY = dHeight - ground.getHeight() - dumpsterB.getHeight()+100;
        }

        //Arrays para elementos
        trashesA= new ArrayList<>();
        trashesB = new ArrayList<>();
        trashesC = new ArrayList<>();
        trashesD = new ArrayList<>();
        explosions = new ArrayList<>();

        //Generar basuras en arreglos
        //Falta ver si se puede convertir a un mapa
        for (i=0; i<trashDensity; i++){

            trash = new Trash(context, 1, levelNumber);
            trashesA.add(trash);
            trash = new Trash(context, 2, levelNumber);
            trashesB.add(trash);
            trash = new Trash(context, 3, levelNumber);
            trashesC.add(trash);

            if (levelNumber==4){
                trash = new Trash(context, 4, levelNumber);
                trashesD.add(trash);
            }

        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        //Checar si es gameOver
        setGameOver();

        if (!gameOver){
            super.onDraw(canvas);
            //Falta cambiar fondo para que se dibuje sin estiramiento
            canvas.drawBitmap(background, null, rectBackground, null);
            canvas.drawBitmap(ground, null, rectGround, null);

            //Dibujar basuras
            for (i = 0; i< trashDensity; i++){

                canvas.drawBitmap(trashesA.get(i).getTrash(trashesA.get(i).trashFrame), trashesA.get(i).trashX, trashesA.get(i).trashY, null);
                canvas.drawBitmap(trashesB.get(i).getTrash(trashesB.get(i).trashFrame), trashesB.get(i).trashX, trashesB.get(i).trashY, null);
                canvas.drawBitmap(trashesC.get(i).getTrash(trashesC.get(i).trashFrame), trashesC.get(i).trashX, trashesC.get(i).trashY, null);

                trashesA.get(i).trashY += trashesA.get(i).trashVelocity;
                trashesB.get(i).trashY += trashesB.get(i).trashVelocity;
                trashesC.get(i).trashY += trashesC.get(i).trashVelocity;

                floorCollision(trashesA, levelNumber);
                floorCollision(trashesB, levelNumber);
                floorCollision(trashesC, levelNumber);

                if (levelNumber==4){
                    canvas.drawBitmap(trashesD.get(i).getTrash(trashesD.get(i).trashFrame), trashesD.get(i).trashX, trashesD.get(i).trashY, null);
                    trashesD.get(i).trashY += trashesD.get(i).trashVelocity;
                    floorCollision(trashesD, levelNumber);
                }
            }

            //Actualizar frames explosiones
            iterator = explosions.iterator();
            while (iterator.hasNext()) {
                explosion = iterator.next();
                canvas.drawBitmap(explosion.getExplosion(explosion.explosionFrame), explosion.explosionX, explosion.explosionY, null);
                explosion.explosionFrame++;
                if (explosion.explosionFrame > 3) {
                    iterator.remove();
                }
            }

            //Dibujar elementos
            canvas.drawBitmap(dumpsterA, dumpsterAX, dumpstersY, null);
            canvas.drawBitmap(dumpsterB, dumpsterBX, dumpstersY, null);
            canvas.drawBitmap(dumpsterC, dumpsterCX, dumpstersY, null);

            if (levelNumber==4){
                canvas.drawBitmap(dumpsterD, dumpsterDX, dumpstersY, null);
                heartDrawableGolden.draw(canvas);
                canvas.drawText(""+points, Math.floorDiv(dWidth,2)-200, Math.floorDiv(dHeight,7)-pointsTextSize, pointsNumber);

            } else {
                heartDrawable.draw(canvas);
                canvas.drawText(""+points+"/"+minPoints, Math.floorDiv(dWidth,2)-200, Math.floorDiv(dHeight,7)-pointsTextSize, pointsNumber);
            }

            canvas.drawText("x"+life, dWidth-150, Math.floorDiv(dHeight, 6)-lifeTextSize-80, lifeNumber);
            handler.postDelayed(runnable, UPDATE_MILLIS);
        }
    }

    //Función para enviar a gameOver
    private void setGameOver() {
        if(life<=0 || points >= minPoints){
            gameOver=true;
            if(points >= minPoints){winningState=1;}
            else{winningState=0;}

            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            intent.putExtra("winningState", winningState);
            intent.putExtra("levelNumber", levelNumber);

            ((Activity)context).finish();
            context.startActivity(intent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {



        switch (event.getAction()) {

            case (MotionEvent.ACTION_MOVE):

                if (draggedTrash == null) {
                    touchX = event.getX();
                    touchY = event.getY();

                    //FALTA AQUI AHORA SIMPLIFICAR A UN SOLO CICLO (PARECE QUE NO SE PUEDE)
                    for (Trash trashNow : trashesA) {
                        if (isTouchWithinTrash(touchX, touchY, trashNow)) {
                            draggedTrash = trashNow;

                            //FALTA VER SI ESTO ES NECESARIO (PARECIERA QUE NO PERO NO ESTOY SEGURO)
                            draggedTrash.setTrashX(touchX - draggedTrash.getTrashWidth() / 2);
                            draggedTrash.setTrashY(touchY - draggedTrash.getTrashHeight() / 2);
                            //Ver si poner draggedTrash
                            movementCollision(event, trashNow);

                            //FALTA VER SI LOS BREAKS SON NECESARIOS
                            break;
                        }
                    }

                    for (Trash trashNow : trashesB) {
                        if (isTouchWithinTrash(touchX, touchY, trashNow)) {
                            draggedTrash = trashNow;
                            draggedTrash.setTrashX(touchX - draggedTrash.getTrashWidth() / 2);
                            draggedTrash.setTrashY(touchY - draggedTrash.getTrashHeight() / 2);
                            movementCollision(event, trashNow);
                            break;
                        }
                    }

                    for (Trash trashNow : trashesC) {
                        if (isTouchWithinTrash(touchX, touchY, trashNow)) {
                            draggedTrash = trashNow;
                            draggedTrash.setTrashX(touchX - draggedTrash.getTrashWidth() / 2);
                            draggedTrash.setTrashY(touchY - draggedTrash.getTrashHeight() / 2);
                            movementCollision(event, trashNow);
                            break;
                        }
                    }

                    for (Trash trashNow : trashesD) {
                        if (isTouchWithinTrash(touchX, touchY, trashNow)) {
                            draggedTrash = trashNow;
                            draggedTrash.setTrashX(touchX - draggedTrash.getTrashWidth() / 2);
                            draggedTrash.setTrashY(touchY - draggedTrash.getTrashHeight() / 2);
                            movementCollision(event, trashNow);
                            break;
                        }
                    }

                    //Resetear el draggedTrash
                    draggedTrash = null;

                    //AQUI AHORA checar cómo hacer para que las basuras no se junten al presionarlas si están en el mismo lugar
                }


            case (MotionEvent.ACTION_DOWN):

                if (draggedTrash == null) {
                    touchX = event.getX();
                    touchY = event.getY();

                    //FALTA AQUI AHORA SIMPLIFICAR A UN SOLO CICLO (PARECE QUE NO SE PUEDE)
                    for (Trash trashNow : trashesA) {
                        if (isTouchWithinTrash(touchX, touchY, trashNow)) {
                            draggedTrash = trashNow;

                            //FALTA VER SI ESTO ES NECESARIO (PARECIERA QUE NO PERO NO ESTOY SEGURO)
                            draggedTrash.setTrashX(touchX - draggedTrash.getTrashWidth() / 2);
                            draggedTrash.setTrashY(touchY - draggedTrash.getTrashHeight() / 2);
                            //Ver si poner draggedTrash
                            movementCollision(event, trashNow);

                            //FALTA VER SI LOS BREAKS SON NECESARIOS
                            break;
                        }
                    }

                    for (Trash trashNow : trashesB) {
                        if (isTouchWithinTrash(touchX, touchY, trashNow)) {
                            draggedTrash = trashNow;
                            draggedTrash.setTrashX(touchX - draggedTrash.getTrashWidth() / 2);
                            draggedTrash.setTrashY(touchY - draggedTrash.getTrashHeight() / 2);
                            movementCollision(event, trashNow);
                            break;
                        }
                    }

                    for (Trash trashNow : trashesC) {
                        if (isTouchWithinTrash(touchX, touchY, trashNow)) {
                            draggedTrash = trashNow;
                            draggedTrash.setTrashX(touchX - draggedTrash.getTrashWidth() / 2);
                            draggedTrash.setTrashY(touchY - draggedTrash.getTrashHeight() / 2);
                            movementCollision(event, trashNow);
                            break;
                        }
                    }

                    for (Trash trashNow : trashesD) {
                        if (isTouchWithinTrash(touchX, touchY, trashNow)) {
                            draggedTrash = trashNow;
                            draggedTrash.setTrashX(touchX - draggedTrash.getTrashWidth() / 2);
                            draggedTrash.setTrashY(touchY - draggedTrash.getTrashHeight() / 2);
                            movementCollision(event, trashNow);
                            break;
                        }
                    }

                    //Resetear el draggedTrash
                    draggedTrash = null;

                    //AQUI AHORA checar cómo hacer para que las basuras no se junten al presionarlas si están en el mismo lugar
                }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Reset the dragged trash
                draggedTrash = null;
                invalidate();
                break;

        }


            //AQUI VER SI DEBE IR EL CASES



        return true;
    }

    //Función para reemplazar el toque
    private boolean isTouchWithinTrash(float touchX, float touchY, Trash trash) {
        // Check if the touch coordinates are within the bounds of the given trash
        return touchX >= trash.getTrashX() &&
                touchX <= trash.getTrashX() + trash.getTrashWidth() &&
                touchY >= trash.getTrashY() &&
                touchY <= trash.getTrashY() + trash.getTrashHeight();
    }

    //Funciones colisiones
    private void movementCollision(MotionEvent event, Trash trashNow) {
        trashType = trashNow.trashTypeMine;
        action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            //Obtener toque
            trashNow.oldX = event.getX();
            trashNow.oldY = event.getY();
            //Obtener posición de basura
            trashNow.oldTrashX = trashNow.trashX;
            trashNow.oldTrashY = trashNow.trashY;
        } else if (action == MotionEvent.ACTION_MOVE) {
            trashNow.shiftX = trashNow.oldX - touchX;
            trashNow.shiftY = trashNow.oldY - touchY;
            newtrashyX = trashNow.oldTrashX - trashNow.shiftX;
            newtrashyY = trashNow.oldTrashY - trashNow.shiftY;

            //Mover en ejes
            if (newtrashyX <= 0)
                trashNow.trashX = 0;
            else if (newtrashyX >= dWidth - trashNow.getTrashWidth())
                trashNow.trashX = dWidth - trashNow.getTrashWidth();
            else
                trashNow.trashX = newtrashyX;
            if (newtrashyY <= 0)
                trashNow.trashY = 0;
            else if (newtrashyY >= dHeight - trashNow.getTrashHeight())
                trashNow.trashY = dHeight - trashNow.getTrashHeight();
            else
                trashNow.trashY = newtrashyY;

            switch (trashType) {
                case 1:
                    dumpsterCollision(trashNow, dumpsterA, true, levelNumber);
                    dumpsterCollision(trashNow, dumpsterB, false, levelNumber);
                    dumpsterCollision(trashNow, dumpsterC, false, levelNumber);
                    dumpsterCollision(trashNow, dumpsterD, false, levelNumber);
                    break;
                case 2:
                    dumpsterCollision(trashNow, dumpsterA, false, levelNumber);
                    dumpsterCollision(trashNow, dumpsterB, true, levelNumber);
                    dumpsterCollision(trashNow, dumpsterC, false, levelNumber);
                    dumpsterCollision(trashNow, dumpsterD, false, levelNumber);
                    break;
                case 3:
                    dumpsterCollision(trashNow, dumpsterA, false, levelNumber);
                    dumpsterCollision(trashNow, dumpsterB, false, levelNumber);
                    dumpsterCollision(trashNow, dumpsterC, true, levelNumber);
                    dumpsterCollision(trashNow, dumpsterD, false, levelNumber);
                    break;
                case 4:
                    dumpsterCollision(trashNow, dumpsterA, false, levelNumber);
                    dumpsterCollision(trashNow, dumpsterB, false, levelNumber);
                    dumpsterCollision(trashNow, dumpsterC, false, levelNumber);
                    dumpsterCollision(trashNow, dumpsterD, true, levelNumber);
                    break;
            }

        }
    }

    //AQUI AHORA VERIFICAR COMO SIMPLIFICAR DUMPSTER Y DUMPSTER X EN UN SOLO VALOR (sin ifs)
    private void dumpsterCollision(Trash trashNow, Bitmap dumpster, boolean state, int levelNumber) {

        if (dumpster.equals(dumpsterA)) {
            dumpsterX = dumpsterAX;
        } else if (dumpster.equals(dumpsterB)) {
            dumpsterX = dumpsterBX;
        } else if (dumpster.equals(dumpsterC)) {
            dumpsterX = dumpsterCX;
        } else if (dumpster.equals(dumpsterD)) {
            dumpsterX = dumpsterDX;
        }

        if (trashNow.trashX + trashNow.getTrashWidth()>=dumpsterX
                && trashNow.trashX <= dumpsterX + dumpster.getWidth()
                && trashNow.trashY + trashNow.getTrashWidth()>=dumpstersY
                && trashNow.trashY + trashNow.getTrashWidth()<=dumpstersY + dumpster.getHeight()){

            if (state)
                points +=pointsSum;
            else life --;

            trashNow.resetTrash(trashType, levelNumber);
        }
    }

    private void floorCollision(ArrayList<Trash> trashy, int levelNumber) {
        if (trashy.get(i).trashY + trashy.get(i).getTrashHeight()>=dHeight-ground.getHeight()){
            life--;
            explosion = new Explosion(context);
            explosion.explosionX = trashy.get(i).trashX;
            explosion.explosionY = trashy.get(i).trashY;
            explosions.add(explosion);
            trashy.get(i).resetTrash(trashy.get(i).trashTypeMine, levelNumber);
        }
    }
}
