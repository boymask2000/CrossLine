package crossline.boymask.crossline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by gposabella on 30/05/2016.
 */
public class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback {
    private final Context context;
    private final MainActivity mainActivity;
    private MyThread mythread;
    private int screenWidth;
    //   private int screenHeight;
    private int i = 0;
    private Paint mPaint = new Paint();
    private int tentativi;
    private Pair firstCell = null;
    private boolean firstMove = true;
    private int coppie;


    public SurfacePanel(Context ctx, AttributeSet attrSet, MainActivity mainActivity) {
        super(ctx, attrSet);
        context = ctx;
        this.mainActivity = mainActivity;
        screenWidth = mainActivity.getScreenWidth();
        //   getDims();
        // setClipBounds(new Rect(0, 0, screenWidth, screenWidth));
        getHolder().setFixedSize(screenWidth, screenWidth);
//setBackgroundColor(Color.RED);
        SurfaceHolder holder = getHolder();

        holder.addCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        doDraw(canvas);
    }

    //***************************************************************************************
    void doDraw(Canvas canvas) {
        Table tab = mainActivity.getTable();
        int size = tab.getSize();

        int step = screenWidth / size;
        for (int i = 0; i <= size; i++) {
            canvas.drawLine(i * step, 0, i * step, screenWidth, mPaint);
            canvas.drawLine(0, i * step, screenWidth, i * step, mPaint);
        }
        Table table = mainActivity.getTable();
        table.draw(this, canvas, mPaint, screenWidth);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mythread.setRunning(false);
        boolean retry = true;
        while (retry) {
            try {
                mythread.join();

                retry = false;
            } catch (Exception e) {

                Log.v("Exception Occured", e.getMessage());
            }
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Table tab = mainActivity.getTable();
        if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

        float x = event.getX();
        float y = event.getY();

        Pair cella = getCella(x, y);
        if (cella == null) return false;
        TableCell cc = tab.getCell(cella.getX(), cella.getY());
        if (cc.isShow()) return false;

        //   Heap.selectedCell = mainActivity.getTable().getCellByCoord(cella.getX(), cella.getY());

        if (firstMove) {

            firstCell = cella;

            TableCell c1 = tab.getCell(firstCell.getX(), firstCell.getY());
            c1.setShow(true);
            c1.setCandidate(true);

        } else {
            tentativi++;
            mainActivity.setTentativi(tentativi);

            TableCell c1 = tab.getCell(firstCell.getX(), firstCell.getY());
            TableCell c2 = tab.getCell(cella.getX(), cella.getY());
            c2.setCandidate(true);
            c2.setShow(true);


            if (c1.getCurrentVal() != c2.getCurrentVal()) {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c1.setShow(false);
                c2.setShow(false);
            } else coppie++;
            mainActivity.setCoppie(coppie);
            c1.setCandidate(false);
            c2.setCandidate(false);
        }
        if (mainActivity.getTable().isRisolto()) {
            PopupMessage.info(mainActivity, "Completato !");
        }


        firstMove = !firstMove;

        return true;
    }


    private Pair getCella(float x, float y) {
        Table tab = mainActivity.getTable();
        int size = tab.getSize();
        int posx = (int) (x * size / screenWidth);
        int posy = (int) (y * size / screenWidth);

        if (posx < 0 || posx > size) return null;
        if (posy < 0 || posy > size) return null;


        return new Pair(posx, posy);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mythread = new MyThread(holder, context, this);

        mythread.setRunning(true);

        mythread.start();
    }

    public void update() {
        invalidate();
    }

    public void reset() {
        tentativi = 0;
        coppie = 0;
    }
}
