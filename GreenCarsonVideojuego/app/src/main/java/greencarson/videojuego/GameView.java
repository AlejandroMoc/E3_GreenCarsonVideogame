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
    final Bitmap dumpsterA;
    final Bitmap dumpsterB;
    final Bitmap dumpsterC;
    final Bitmap dumpsterD;
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
    float newtrashyX;
    float newtrashyY;
    float touchX;
    float touchY;
    float dumpsterX;
    int points;
    int winningState;
    final int minPoints = 500;
    int life = 5;
    final int trashDensity=2;
    int action;
    int i;
    int trashType;
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

    public GameView(Context context){
        super(context);
        this.context = context;

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_tiles);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground2);
        heart = BitmapFactory.decodeResource(getResources(), R.drawable.logo_heart);

        dumpsterA = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_1);
        dumpsterB = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_2);
        dumpsterC = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_3);
        dumpsterD = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_4);

        //Falta aqui redimensionar para cuatro botes
        //dumpsterB = Bitmap.createScaledBitmap(dumpsterB, dumpsterB.getWidth()-dumpsterB.getWidth()/3, dumpsterB.getHeight()-dumpsterB.getHeight()/3, true);

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
        dumpsterAX= Math.floorDiv(dWidth,20);
        dumpsterBX = Math.floorDiv(dWidth,2)-Math.floorDiv(dumpsterB.getWidth(),2);
        dumpsterCX = dWidth-dumpsterB.getWidth()-dumpsterAX;
        //Falta cambiar para nivel avanzado
        dumpsterDX = dWidth-dumpsterC.getWidth()-dumpsterBX;
        dumpstersY = dHeight - ground.getHeight() - dumpsterB.getHeight()+100;

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
            trash = new Trash(context, 1);
            trashesA.add(trash);
            trash = new Trash(context, 2);
            trashesB.add(trash);
            trash = new Trash(context, 3);
            trashesC.add(trash);
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
                floorCollision(trashesA);
                floorCollision(trashesB);
                floorCollision(trashesC);
            }

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
            heartDrawable.draw(canvas);
            canvas.drawText(""+points+"/500", Math.floorDiv(dWidth,2)-200, Math.floorDiv(dHeight,7)-pointsTextSize, pointsNumber);
            canvas.drawText("x"+life, dWidth-150, Math.floorDiv(dHeight, 6)-lifeTextSize-80, lifeNumber);
            handler.postDelayed(runnable, UPDATE_MILLIS);
        }
    }

    //Función para enviar a gameOver
    private void setGameOver() {
        //Mandar a pantalla de reinicio o de aceptación
        if(life==0){
            if(points >= minPoints){winningState=1;}
            else{winningState=0;}
            Intent intent = new Intent(context, GameOver.class);

            intent.putExtra("points", points);
            intent.putExtra("winningState", winningState);
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
        return true;
    }

    //Funciones colisiones
    private void movementCollision(MotionEvent event, ArrayList<Trash> trashy) {
        for (Trash trashNow : trashy) {

            //Obtener tipo de basura
            trashType = trashNow.trashTypeMine;

            //Si se está tocando la basura
            if (touchY >= trashNow.trashY && touchY <= (trashNow.trashY + trashNow.getTrashHeight())
                    && touchX >= trashNow.trashX && touchX <= (trashNow.trashX + trashNow.getTrashWidth()))
            {
                action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    //Obtener toque
                    trashNow.oldX = event.getX();
                    trashNow.oldY = event.getY();
                    //Obtener posición de basura
                    trashNow.oldTrashX = trashNow.trashX;
                    trashNow.oldTrashY = trashNow.trashY;
                } else if (action == MotionEvent.ACTION_MOVE) {
                    trashNow.shifX = trashNow.oldX - touchX;
                    trashNow.shiftY = trashNow.oldY - touchY;
                    newtrashyX = trashNow.oldTrashX - trashNow.shifX;
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

                    //Falta cambiar a cases
                    if (trashType==1){
                        dumpsterCollision(trashNow, dumpsterA, true);
                        dumpsterCollision(trashNow, dumpsterB, false);
                        dumpsterCollision(trashNow, dumpsterC, false);
                    } else if (trashType==2){
                        dumpsterCollision(trashNow, dumpsterA, false);
                        dumpsterCollision(trashNow, dumpsterB, true);
                        dumpsterCollision(trashNow, dumpsterC, false);
                    } else if (trashType==3){
                        dumpsterCollision(trashNow, dumpsterA, false);
                        dumpsterCollision(trashNow, dumpsterB, false);
                        dumpsterCollision(trashNow, dumpsterC, true);
                    } else if (trashType==4){

                        //Falta Bote correcto D
                        dumpsterCollision(trashNow, dumpsterA, false);
                        dumpsterCollision(trashNow, dumpsterB, false);
                        dumpsterCollision(trashNow, dumpsterC, false);

                    }


                }
            }
        }
    }

    //AQUI AHORA VERIFICAR COMO SIMPLIFICAR DUMPSTER Y DUMPSTER X EN UN SOLO VALOR (sin ifs)
    private void dumpsterCollision(Trash trashNow, Bitmap dumpster, boolean state) {

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

            //Checar si es el bote correcto o no
            if (state)
                points +=10;
            else life --;

            trashNow.resetTrash(trashType);
        }
    }

    private void floorCollision(ArrayList<Trash> trashy) {
        if (trashy.get(i).trashY + trashy.get(i).getTrashHeight()>=dHeight-ground.getHeight()){
            life--;
            explosion = new Explosion(context);
            explosion.explosionX = trashy.get(i).trashX;
            explosion.explosionY = trashy.get(i).trashY;
            explosions.add(explosion);
            trashy.get(i).resetTrash(trashy.get(i).trashTypeMine);
        }
    }
}
