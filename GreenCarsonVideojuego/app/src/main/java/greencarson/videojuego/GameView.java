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
    final Bitmap background;
    final Bitmap ground;
    Bitmap dumpsterA, dumpsterB, dumpsterC, dumpsterD;
    final Bitmap heart;
    final Rect rectBackground;
    final Rect rectGround;
    final Rect rectHeart;
    final Drawable heartDrawable = ContextCompat.getDrawable(getContext(), R.drawable.logo_heart);
    final Context context;
    final Handler handler;
    final long UPDATE_MILLIS = 30;
    final Runnable runnable;
    final Paint pointsNumber = new Paint();
    final Paint lifeNumber = new Paint();
    //Falta verificar cuáles deben ser float y cuáles int
    final float pointsTextSize = 120;
    final float lifeTextSize = 70;
    final float dumpsterAX;
    final float dumpsterBX;
    final float dumpsterCX;
    final float dumpsterDX;
    final float dumpstersY;
    float newtrashyX, newtrashyY, touchX, touchY, dumpsterX;
    int points, action, i, trashType, winningState, minPoints, trashDensity, life;
    //FALTA AQUI CAMBIAR DEPENDIENDO DEL Nivel
    final int levelNumber;
    static int dWidth;
    static int dHeight;
    static final int heartSize=120;
    static final int heartMargin=100;
    boolean gameOver = false;
    final Random random;
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
        //Recuperar número de nivel
        this.levelNumber = levelNumber;

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_tiles);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground2);
        heart = BitmapFactory.decodeResource(getResources(), R.drawable.logo_heart);

        dumpsterA = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_1);
        dumpsterB = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_2);
        dumpsterC = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_3);
        dumpsterD = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_4);

        //Redimensionar cuatro botes
        if (levelNumber ==4){
            Log.d("4", "Se envia a nivel avanzado");
            dumpsterA = Bitmap.createScaledBitmap(dumpsterA, dumpsterA.getWidth()-dumpsterA.getWidth()/3, dumpsterA.getHeight()-dumpsterA.getHeight()/3, true);
            dumpsterB = Bitmap.createScaledBitmap(dumpsterB, dumpsterB.getWidth()-dumpsterB.getWidth()/3, dumpsterB.getHeight()-dumpsterB.getHeight()/3, true);
            dumpsterC = Bitmap.createScaledBitmap(dumpsterC, dumpsterC.getWidth()-dumpsterC.getWidth()/3, dumpsterC.getHeight()-dumpsterC.getHeight()/3, true);
            dumpsterD = Bitmap.createScaledBitmap(dumpsterD, dumpsterD.getWidth()-dumpsterD.getWidth()/3, dumpsterD.getHeight()-dumpsterD.getHeight()/3, true);
            minPoints=1000;
            trashDensity=2;
            life=5;

        } else if (levelNumber==3){
            Log.d("3", "Se envia a nivel avanzado");
            minPoints=800;
            trashDensity=2;
            life=5;
        } else if (levelNumber==2){
            Log.d("2", "Se envia a nivel intermedio");
            minPoints=500;
            trashDensity=2;
            life=5;
        } else if (levelNumber==1){
            Log.d("1", "Se envia a nivel básico");
            minPoints=200;
            trashDensity=1;
            life=5;
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
        heartDrawable.setBounds(dWidth - heartSize - heartMargin, heartMargin, dWidth - heartMargin, heartMargin + heartSize);

        random = new Random();

        //Posición de los botes
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
            //Falta añadir tipo de basura C y D
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
            canvas.drawBitmap(background, null, rectBackground, null);
            canvas.drawBitmap(ground, null, rectGround, null);

            //Dibujar basuras, por ahora todas con la misma densidad
            for (i = 0; i< trashDensity; i++){

                canvas.drawBitmap(trashesA.get(i).getTrash(trashesA.get(i).trashFrame), trashesA.get(i).trashX, trashesA.get(i).trashY, null);
                canvas.drawBitmap(trashesB.get(i).getTrash(trashesB.get(i).trashFrame), trashesB.get(i).trashX, trashesB.get(i).trashY, null);
                canvas.drawBitmap(trashesC.get(i).getTrash(trashesC.get(i).trashFrame), trashesC.get(i).trashX, trashesC.get(i).trashY, null);

                trashesA.get(i).trashY += trashesA.get(i).trashVelocity;
                trashesB.get(i).trashY += trashesB.get(i).trashVelocity;
                trashesC.get(i).trashY += trashesC.get(i).trashVelocity;

                //Checar colisión con piso
                floorCollision(trashesA, levelNumber);
                floorCollision(trashesB, levelNumber);
                floorCollision(trashesC, levelNumber);

                if (levelNumber==4){
                    canvas.drawBitmap(trashesD.get(i).getTrash(trashesD.get(i).trashFrame), trashesD.get(i).trashX, trashesD.get(i).trashY, null);
                    trashesD.get(i).trashY += trashesD.get(i).trashVelocity;
                    floorCollision(trashesD, levelNumber);
                }

            }

            //AQUI AHORA ACTUALIZAR ESTO PARA QUE CAMBIE SI ES NIVEL AVANZADO


            //Actualizar frames de explosiones
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
            }

            heartDrawable.draw(canvas);
            //CAMBIAR AQUI PARA PUNTAJE MINIMO POR NIVEL
            canvas.drawText(""+points+"/"+minPoints, Math.floorDiv(dWidth,2)-200, Math.floorDiv(dHeight,7)-pointsTextSize, pointsNumber);
            canvas.drawText("x"+life, dWidth-150, Math.floorDiv(dHeight, 6)-lifeTextSize-80, lifeNumber);
            handler.postDelayed(runnable, UPDATE_MILLIS);
        }
    }

    //Función para enviar a gameOver
    private void setGameOver() {
        //Mandar a pantalla de reinicio o de aceptación
        if(life<=0){
            if(points >= minPoints){winningState=1;}
            else{winningState=0;}

            //FALTA AQUI PASAR EL LEVELNUMBER
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            intent.putExtra("winningState", winningState);
            intent.putExtra("levelNumber", levelNumber);

            ((Activity)context).finish();
            context.startActivity(intent);
            gameOver=true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Obtener toque
        touchX = event.getX();
        touchY = event.getY();

        //Colisiones con botes
        //AQUI AHORA checar cómo hacer para que las basuras no se junten al presionarlas si están en el mismo lugar
        movementCollision(event, trashesA);
        movementCollision(event, trashesB);
        movementCollision(event, trashesC);

        if (levelNumber==4){
            movementCollision(event, trashesD);
        }

        return true;
    }

    //Funciones colisiones
    private int movementCollision(MotionEvent event, ArrayList<Trash> trashy) {
        for (Trash trashNow : trashy) {

            //Obtener tipo de basura
            trashType = trashNow.trashTypeMine;

            //Si se está tocando la basura
            if (touchY >= trashNow.trashY && touchY <= (trashNow.trashY + trashNow.getTrashHeight())
                    && touchX >= trashNow.trashX && touchX <= (trashNow.trashX + trashNow.getTrashWidth()))
            {
                Log.d("8", "Tocando " + trashType);
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

                    //chambeale
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

                    //Falta cambiar a cases
                    if (trashType==1){
                        dumpsterCollision(trashNow, dumpsterA, true, levelNumber);
                        dumpsterCollision(trashNow, dumpsterB, false, levelNumber);
                        dumpsterCollision(trashNow, dumpsterC, false, levelNumber);
                        dumpsterCollision(trashNow, dumpsterD, false, levelNumber);
                    } else if (trashType==2){
                        dumpsterCollision(trashNow, dumpsterA, false, levelNumber);
                        dumpsterCollision(trashNow, dumpsterB, true, levelNumber);
                        dumpsterCollision(trashNow, dumpsterC, false, levelNumber);
                        dumpsterCollision(trashNow, dumpsterD, false, levelNumber);
                    } else if (trashType==3){
                        dumpsterCollision(trashNow, dumpsterA, false, levelNumber);
                        dumpsterCollision(trashNow, dumpsterB, false, levelNumber);
                        dumpsterCollision(trashNow, dumpsterC, true, levelNumber);
                        dumpsterCollision(trashNow, dumpsterD, false, levelNumber);

                    } else if (trashType==4){
                        dumpsterCollision(trashNow, dumpsterA, false, levelNumber);
                        dumpsterCollision(trashNow, dumpsterB, false, levelNumber);
                        dumpsterCollision(trashNow, dumpsterC, false, levelNumber);
                        dumpsterCollision(trashNow, dumpsterD, true, levelNumber);
                    }


                }
            }
        }
    }

    //AQUI AHORA VERIFICAR COMO SIMPLIFICAR DUMPSTER Y DUMPSTER X EN UN SOLO VALOR (sin ifs)
    private void dumpsterCollision(Trash trashNow, Bitmap dumpster, boolean state, int levelNumber) {

        //Falta cambiar a cases
        if (dumpster==dumpsterA){
            dumpsterX=dumpsterAX;
        } else if (dumpster==dumpsterB){
            dumpsterX=dumpsterBX;
        } else if (dumpster==dumpsterC){
            dumpsterX=dumpsterCX;
        } else if (dumpster==dumpsterD){
            dumpsterX=dumpsterDX;
        }

        if (trashNow.trashX + trashNow.getTrashWidth()>=dumpsterX
                && trashNow.trashX <= dumpsterX + dumpster.getWidth()
                && trashNow.trashY + trashNow.getTrashWidth()>=dumpstersY
                && trashNow.trashY + trashNow.getTrashWidth()<=dumpstersY + dumpster.getHeight()){

            //FALTA CAMBIAR DE ACUERDO CON EL NIVEL (SOBRE T0D0 EL +10)
            if (state)
                points +=10;
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
