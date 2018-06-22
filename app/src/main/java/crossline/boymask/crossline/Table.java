package crossline.boymask.crossline;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;


public class Table {

    private int backColor = Color.WHITE;
    private final MainActivity main;
    private int size = 0;
    private TableCell[][] table = null;


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
        clear(canvas, screenWidth);
        surfacePanel.drawLines(canvas);

        int cSize = screenWidth / (size + 2);
        mPaint.setTextSize(cSize);
        int fattX = screenWidth / size;
        int fattY = screenWidth / size;
        for (int i = 1; i < size - 1; i++) {
            int x = i * fattX + 2;
            int y = 2;
            canvas.drawBitmap(Heap.getIcon(0), x, 2, new Paint());
            canvas.drawBitmap(Heap.getIcon(1), x, fattY * (size - 1), new Paint());

            canvas.drawBitmap(Heap.getIcon(3), 2, x, new Paint());
            canvas.drawBitmap(Heap.getIcon(2), fattY * (size - 1), x, new Paint());
        }
        for (int i = 1; i < size - 1; i++)
            for (int j = 1; j < size - 1; j++) {
                TableCell cell = table[i][j];

                int x = i * fattX + 2;
                int y = j * fattY + 2;
                fill(canvas, screenWidth, x, y, backColor);

                if (cell.isShow() || cell.isShowPreview()) {

                    drawText(canvas, mPaint, "" + cell.getCurrentVal(), i * fattX, (j + 1) * fattY, cSize);
                    //       canvas.drawText("" + cell.getCurrentVal(), i * fattX + 2, (j + 1) * fattY - 4, mPaint);


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
                    fill(canvas, screenWidth, x, y, backColor);

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

    private void clear(Canvas canvas, int screenWidth) {
        int ss = screenWidth;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backColor);
        canvas.drawRect(0, 0, ss, ss, paint);
    }


    public void init() {
        main.clean();
    //    main.getSurface().setTentativi(0);
        Random rand = new Random();
        int val = 1;
        for (int i = 1; i < size - 1; i++)
            for (int j = 1; j < size - 1; j++) {
                table[i][j].setCurrentVal(val++);
            }

        for (int i = 0; i < 50; i++) {
            int n = rand.nextInt(size);
            rotateCol(n, 1);
            n = rand.nextInt(size);
            rotateRow(n, 1);
        }
    }

    public int getSize() {
        return size;
    }

    public int getCellSize() {
        return main.getScreenWidth() / size;
    }

    public boolean isRisolto() {
        int val = 1;
        for (int i = 1; i < size - 1; i++)
            for (int j = 1; j < size - 1; j++) {
                if (table[i][j].getCurrentVal() != val) return false;
                val++;
            }
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

        new Thread() {
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

    public void rotateCol(int y, int dd) {
        int vals[] = new int[size];

        if (dd == 1) {
            for (int i = 1; i < size - 2; i++)
                vals[i + 1] = table[y][i].getCurrentVal();
            vals[1] = table[y][size - 2].getCurrentVal();
        } else {
            for (int i = 2; i < size - 1; i++)
                vals[i - 1] = table[y][i].getCurrentVal();
            vals[size - 2] = table[y][1].getCurrentVal();
        }
        for (int i = 1; i < size - 1; i++)
            table[y][i].setCurrentVal(vals[i]);
    }

    public void rotateRow(int y, int dd) {
        int vals[] = new int[size];
        if (dd == 1) {
            for (int i = 1; i < size - 2; i++)
                vals[i + 1] = table[i][y].getCurrentVal();
            vals[1] = table[size - 2][y].getCurrentVal();
        } else {
            for (int i = 2; i < size - 1; i++)
                vals[i - 1] = table[i][y].getCurrentVal();
            vals[size - 2] = table[1][y].getCurrentVal();
        }
        for (int i = 1; i < size - 1; i++)
            table[i][y].setCurrentVal(vals[i]);
    }

}
