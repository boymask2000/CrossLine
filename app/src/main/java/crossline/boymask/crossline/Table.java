package crossline.boymask.crossline;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class Table {


    private final MainActivity main;
    private int size = 0;
    private TableCell[][] table = new TableCell[10][10];


    public Table(MainActivity mainActivity, int size) {
        this.main = mainActivity;
        this.size = size;
        table = new TableCell[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                table[i][j] = new TableCell(i, j);

        init();
    }


    public TableCell getCell(int i, int j) {

        return table[i][j];
    }


    public TableCell getCellByCoord(int x, int y) {
        if (x < 0 || y < 0)
            return null;
        if (x > 450 || y > 450)
            return null;
        int xx = x / 50;
        int yy = y / 50;
        return table[xx][yy];
    }


    public void draw(SurfacePanel surfacePanel, Canvas canvas, Paint mPaint, int screenWidth) {
        //  setTextSizeForWidth(mPaint,300, "1");
        int cSize = screenWidth / (size + 2);
        mPaint.setTextSize(cSize);
        int fattX = screenWidth / size;
        int fattY = screenWidth / size;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                TableCell cell = table[i][j];

                int x = i * fattX + 2;
                int y = j * fattY + 2;
                fill(canvas, screenWidth, x, y, Color.WHITE);

                if (cell.isShow() || cell.isShowPreview()) {
                    if (main.isUseImages())
                        canvas.drawBitmap(Heap.getIcon(cell.getCurrentVal() - 1), x, y, new Paint());
                    else {
                        drawText(canvas, mPaint, "" + cell.getCurrentVal(), i * fattX, (j + 1) * fattY, cSize);
                        //       canvas.drawText("" + cell.getCurrentVal(), i * fattX + 2, (j + 1) * fattY - 4, mPaint);
                    }

                    if (cell.isCandidate()) {
                        Paint paint = new Paint();
                        paint.setStrokeWidth(4);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setColor(Color.GREEN);
                        canvas.drawRect(i * fattX + 4, j * fattY + 4,
                                (i + 1) * fattX - 2, (j + 1) * fattY - 2, paint);

                        //    canvas.drawRect(i * fattX+1, j  * fattY+1,(i+1) * fattX+1, j  * fattY+4,paint);
                        //    canvas.drawRect(i * fattX+1, (j+1)  * fattY+1,(i+1) * fattX+1, (j+1)  * fattY+4,paint);

                    }
                } else
                    fill(canvas, screenWidth, x, y, Color.WHITE);

            }
    }

    private void drawText(Canvas canvas, Paint paint, String text, int x, int y, int csize) {
        Rect r = new Rect();
        paint.getTextBounds(text, 0, text.length(), r);
        int h = csize - r.height();
        int w = csize - r.width();
        int yPos = y - h / 2;
        int xPos = x + w / 2;
        canvas.drawText(text, xPos, yPos, paint);

    }

    private void fill(Canvas canvas, int screenWidth, int x, int y, int color) {
        int ss = screenWidth / size - 2;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawRect(x, y, x + ss, y + ss, paint);
    }


    public void init() {
        Randomizer rand = new Randomizer(size * size);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                table[i][j].setCurrentVal(rand.getNextRandom());
            }

    }

    public int getSize() {
        return size;
    }

    public int getCellSize() {
        return main.getScreenWidth() / size;
    }

    public boolean isRisolto() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (!table[i][j].isShow()) return false;

        return true;
    }

    public void solve() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) table[i][j].setShow(true);
    }

    public void clean() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) table[i][j].setShow(false);
        init();
    }

    public void preview() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) table[i][j].setShowPreview(true);

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++) table[i][j].setShowPreview(false);
            }
        }.start();


    }
}
