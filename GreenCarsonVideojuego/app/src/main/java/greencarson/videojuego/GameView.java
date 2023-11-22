package greencarson.videojuego;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
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
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameView extends View {

    private boolean trashTouched = false;
    private float trashTouchOffsetX, trashTouchOffsetY;
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
    float dumpsterX;
    int points, pointsSum, action, i, trashType, winningState, minPoints, trashDensity, life;
    final int levelNumber;
    static int dWidth;
    static int dHeight;
    static final int heartSize = 120;
    static final int heartMargin = 100;
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
    MediaPlayer mediaPlayer;
    final MediaPlayer trashcan_ad;

    public GameView(Context context, int levelNumber) {

        super(context);
        //getHolder().addCallback(this);
        this.context = context;
        this.levelNumber = levelNumber;

        int[] imageList = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4, R.drawable.bg5};
        Random rand = new Random();
        int randomImage = imageList[rand.nextInt(imageList.length)];
        background = BitmapFactory.decodeResource(getResources(), randomImage);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);

        //mediaPlayer = MediaPlayer.create(context, R.raw.lvl_music);
        //mediaPlayer.setLooping(true);  // Set looping
        //mediaPlayer.start();
        startMusic(context);
        //stopAudio(context);

        trashcan_ad = MediaPlayer.create(context, R.raw.trashcan);

        //Para medidas de corazón
        heart = BitmapFactory.decodeResource(getResources(), R.drawable.logo_heart);
        dumpsterA = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_1);
        dumpsterB = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_2);
        dumpsterC = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_3);
        dumpsterD = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan_4);

        //Por niveles
        if (levelNumber == 4) {
            Log.d("4", "Se envia a nivel avanzado");
            dumpsterA = Bitmap.createScaledBitmap(dumpsterA, dumpsterA.getWidth() - dumpsterA.getWidth() / 3, dumpsterA.getHeight() - dumpsterA.getHeight() / 3, true);
            dumpsterB = Bitmap.createScaledBitmap(dumpsterB, dumpsterB.getWidth() - dumpsterB.getWidth() / 3, dumpsterB.getHeight() - dumpsterB.getHeight() / 3, true);
            dumpsterC = Bitmap.createScaledBitmap(dumpsterC, dumpsterC.getWidth() - dumpsterC.getWidth() / 3, dumpsterC.getHeight() - dumpsterC.getHeight() / 3, true);
            dumpsterD = Bitmap.createScaledBitmap(dumpsterD, dumpsterD.getWidth() - dumpsterD.getWidth() / 3, dumpsterD.getHeight() - dumpsterD.getHeight() / 3, true);
            minPoints = Integer.MAX_VALUE;
            trashDensity = 2;
            //life=10;
            //pointsSum=10;
            life = 25;
            pointsSum = 30;

        } else if (levelNumber == 3) {
            Log.d("3", "Se envia a nivel avanzado");
            //minPoints=300;
            //life=5;
            //pointsSum=10;
            minPoints = 800;
            life = 20;
            pointsSum = 20;

        } else if (levelNumber == 2) {
            Log.d("2", "Se envia a nivel intermedio");
            trashDensity = 2;
            //minPoints=250;
            //life=5;
            //pointsSum=10;
            minPoints = 500;
            life = 15;
            pointsSum = 15;

        } else if (levelNumber == 1) {
            Log.d("1", "Se envia a nivel básico");
            trashDensity = 1;
            //minPoints=200;
            //life=5;
            //pointsSum=10;
            minPoints = 200;
            life = 10;
            pointsSum = 10;
        }

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        //Rectangulos para fondo y piso
        rectBackground = new Rect(0, 0, dWidth, dHeight);
        rectGround = new Rect(0, dHeight - ground.getHeight(), dWidth, dHeight);
        rectHeart = new Rect(0, 0, dWidth, dHeight);
        handler = new Handler();
        runnable = this::invalidate;

        pointsNumber.setColor(ContextCompat.getColor(context, R.color.black));
        pointsNumber.setTextSize(pointsTextSize);
        pointsNumber.setTextAlign(Paint.Align.LEFT);
        pointsNumber.setTypeface(Typeface.DEFAULT_BOLD);
        //En caso de querer poner una fuente, es aquí
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
        if (levelNumber == 4) {
            dumpsterAX = Math.floorDiv(dWidth, 20);
            dumpsterBX = Math.floorDiv(dWidth, 3) - Math.floorDiv(dumpsterB.getWidth(), 3);
        } else {
            dumpsterAX = Math.floorDiv(dWidth, 20);
            dumpsterBX = Math.floorDiv(dWidth, 2) - Math.floorDiv(dumpsterB.getWidth(), 2);
        }
        //Esto siempre pasa
        dumpsterCX = dWidth - dumpsterB.getWidth() - dumpsterAX;
        dumpsterDX = dWidth - dumpsterC.getWidth() - dumpsterBX;
        dumpstersY = dHeight - ground.getHeight() - dumpsterB.getHeight() + 100;

        //Arrays para elementos
        trashesA = new ArrayList<>();
        trashesB = new ArrayList<>();
        trashesC = new ArrayList<>();
        trashesD = new ArrayList<>();
        explosions = new ArrayList<>();

        //Generar basuras en arreglos
        //FALTA ver si se puede convertir a un mapa
        for (i = 0; i < trashDensity; i++) {

            trash = new Trash(context, 1, levelNumber);
            trashesA.add(trash);
            trash = new Trash(context, 2, levelNumber);
            trashesB.add(trash);
            trash = new Trash(context, 3, levelNumber);
            trashesC.add(trash);

            if (levelNumber == 4) {
                trash = new Trash(context, 4, levelNumber);
                trashesD.add(trash);
            }
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        //Checar si es gameOver en cada iteración
        setGameOver();

        if (!gameOver) {
            super.onDraw(canvas);
            //Falta cambiar fondo para que se dibuje sin estiramiento
            canvas.drawBitmap(background, null, rectBackground, null);
            canvas.drawBitmap(ground, null, rectGround, null);

            //Dibujar basuras
            for (i = 0; i < trashDensity; i++) {

                canvas.drawBitmap(trashesA.get(i).getTrash(trashesA.get(i).trashFrame), trashesA.get(i).trashX, trashesA.get(i).trashY, null);
                canvas.drawBitmap(trashesB.get(i).getTrash(trashesB.get(i).trashFrame), trashesB.get(i).trashX, trashesB.get(i).trashY, null);
                canvas.drawBitmap(trashesC.get(i).getTrash(trashesC.get(i).trashFrame), trashesC.get(i).trashX, trashesC.get(i).trashY, null);

                trashesA.get(i).trashY += trashesA.get(i).trashVelocity;
                trashesB.get(i).trashY += trashesB.get(i).trashVelocity;
                trashesC.get(i).trashY += trashesC.get(i).trashVelocity;

                floorCollision(trashesA, levelNumber);
                floorCollision(trashesB, levelNumber);
                floorCollision(trashesC, levelNumber);

                if (levelNumber == 4) {
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

            if (levelNumber == 4) {
                canvas.drawBitmap(dumpsterD, dumpsterDX, dumpstersY, null);
                heartDrawableGolden.draw(canvas);
                canvas.drawText("" + points, Math.floorDiv(dWidth, 2) - 200, Math.floorDiv(dHeight, 7) - pointsTextSize, pointsNumber);

            } else {
                heartDrawable.draw(canvas);
                canvas.drawText("" + points + "/" + minPoints, Math.floorDiv(dWidth, 2) - 200, Math.floorDiv(dHeight, 7) - pointsTextSize, pointsNumber);
            }

            canvas.drawText("x" + life, dWidth - 150, Math.floorDiv(dHeight, 6) - lifeTextSize - 80, lifeNumber);
            handler.postDelayed(runnable, UPDATE_MILLIS);
        }
    }

    //Función para enviar a gameOver
    private void setGameOver() {

        //Falta checar qué condición dejar
        if (life <= 0 || points >= minPoints) {
            //if(life<=0){
            gameOver = true;
            if (points >= minPoints) {
                winningState = 1;
            } else {
                winningState = 0;
            }

            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            intent.putExtra("winningState", winningState);
            intent.putExtra("levelNumber", levelNumber);

            //stopAudio(context);

            ((Activity) context).finish();
            context.startActivity(intent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float touchX = event.getX();
        float touchY = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                //AQUI CHECAR LÍMITES DEL TOUCH
                if (touchY < dHeight - ground.getHeight() - 200) {
                    Log.d("11", "Tocando");
                    for (Trash trash : trashesA) {
                        if (isTouchWithinTrash(trash, touchX, touchY)) {

                            //FALTA AQUÍ SIMPLIFICAR CON UNA SOLA FUNCIÓN
                            trashTouched = true;

                            //Parece que con estas lineas funciona por alguna razón moviendose al centro
                            trashTouchOffsetX = touchX - trash.trashX;
                            trashTouchOffsetY = touchY - trash.trashY;
                            draggedTrash = trash;
                            //movementCollision(event, draggedTrash);
                            break;
                        }
                    }
                    for (Trash trash : trashesB) {
                        if (isTouchWithinTrash(trash, touchX, touchY)) {
                            trashTouched = true;
                            trashTouchOffsetX = touchX - trash.trashX;
                            trashTouchOffsetY = touchY - trash.trashY;
                            draggedTrash = trash;
                            break;
                        }
                    }
                    for (Trash trash : trashesC) {
                        if (isTouchWithinTrash(trash, touchX, touchY)) {
                            trashTouched = true;
                            trashTouchOffsetX = touchX - trash.trashX;
                            trashTouchOffsetY = touchY - trash.trashY;
                            draggedTrash = trash;
                            break;
                        }
                    }
                    if (levelNumber == 4) {
                        for (Trash trash : trashesD) {
                            if (isTouchWithinTrash(trash, touchX, touchY)) {
                                trashTouched = true;
                                trashTouchOffsetX = touchX - trash.trashX;
                                trashTouchOffsetY = touchY - trash.trashY;
                                draggedTrash = trash;
                                break;
                            }
                        }
                    }
                }
                break;

            //Checar si se está tocando alguna basura
            case MotionEvent.ACTION_MOVE:
                if (touchY < dHeight - ground.getHeight() - 200) {
                    if (trashTouched) {
                        draggedTrash.trashX = touchX - trashTouchOffsetX;
                        draggedTrash.trashY = touchY - trashTouchOffsetY;
                    }
                }
                break;

            //Cuando el dedo se levanta
            case MotionEvent.ACTION_UP:
                //Si basura seleccionada existe, checa colisiones
                if (draggedTrash != null) {
                    movementCollision(event, draggedTrash);
                }
                trashTouched = false;
                draggedTrash = null;
                break;
        }

        //Redibujar vista
        invalidate();
        return true;
    }

    //Checar si se toca basura
    private boolean isTouchWithinTrash(Trash trash, float touchX, float touchY) {
        //Checar si el touch está tocando el área de la basura
        return touchX >= trash.trashX && touchX <= (trash.trashX + trash.getTrash(trash.trashFrame).getWidth()) &&
                touchY >= trash.trashY && touchY <= (trash.trashY + trash.getTrash(trash.trashFrame).getHeight());
    }

    private void movementCollision(MotionEvent event, Trash trashNow) {

        trashType = trashNow.trashTypeMine;
        action = event.getAction();

        //FALTA AQUÍ SIMPLIFICAR PARA SOLO USAR LOS TRUES Y DEJAR EL RESTO EN FALSES
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

        //Limitar movimientos
        if (trashNow.trashX + trashNow.getTrashWidth() >= dumpsterX
                && trashNow.trashX <= dumpsterX + dumpster.getWidth()
                && trashNow.trashY + trashNow.getTrashWidth() >= dumpstersY
                && trashNow.trashY + trashNow.getTrashWidth() <= dumpstersY + dumpster.getHeight()) {

            if (state) {
                points += pointsSum;
            } else {
                life--;
            }

            trashNow.resetTrash(trashType, levelNumber);
            trashcan_ad.start();
        }
    }

    private void floorCollision(ArrayList<Trash> trashy, int levelNumber) {
        if (trashy.get(i).trashY + trashy.get(i).getTrashHeight() >= dHeight - ground.getHeight()) {
            //Restar vida
            life--;
            explosion = new Explosion(context);
            explosion.explosionX = trashy.get(i).trashX;
            explosion.explosionY = trashy.get(i).trashY;
            explosions.add(explosion);
            trashy.get(i).resetTrash(trashy.get(i).trashTypeMine, levelNumber);
            if (draggedTrash != null) {
                trashTouched = false;
                draggedTrash = null;
            }
        }
    }

    /*@Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                retry = false;
            } catch (Exception e) {
                // manejar excepción
            }
        }
    }*/

    //Para manejar la música
    private void startMusic(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.lvl_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void stopAudio(Context context) {
        //FALTA AQUI URGENTÍSIMO AÑADIR LA CAPACIDAD DE DETECTAR SI LA PANTALLA ESTÁ APAGADA, SI SE ESTÁ EN SEGUNDO PLANO, ETC
        //Y PODER RETOMARLO
        //if (mediaPlayer != null || !isAppInForeground(context)) {
        if (mediaPlayer != null) {
        //if (isScreenOff()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
