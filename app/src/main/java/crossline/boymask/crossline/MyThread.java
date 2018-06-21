package crossline.boymask.crossline;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by gposabella on 30/05/2016.
 */
public class MyThread extends Thread {

    private boolean mRun;
    private Canvas mcanvas;
    private SurfaceHolder surfaceHolder;
    private Context context;
    private SurfacePanel msurfacePanel;

    public MyThread(SurfaceHolder sholder, Context ctx, SurfacePanel spanel) {

        surfaceHolder = sholder;
        context = ctx;
        mRun = false;
        msurfacePanel = spanel;
    }

    void setRunning(boolean bRun) {
        mRun = bRun;
    }

    @Override
    public void run() {
        super.run();

        while (mRun) {
            mcanvas = surfaceHolder.lockCanvas();

            if (mcanvas != null) {
                msurfacePanel.doDraw(mcanvas);

                surfaceHolder.unlockCanvasAndPost(mcanvas);


            }
        }
    }

    public void update() {
  //      mcanvas = surfaceHolder.lockCanvas();

        if (mcanvas != null) {
            msurfacePanel.doDraw(mcanvas);

    //        surfaceHolder.unlockCanvasAndPost(mcanvas);


        }
    }
}
