package crossline.boymask.crossline;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Objects;

public class TableCell {
    private final Table table;
    private final int screenWidth;
    private boolean show = true;
    private boolean showPreview = false;
    private int x;
    private int y;
    private int currentVal;

    private Bitmap bitmap=null;


    private boolean candidate;

    private int backgroundColor=Color.WHITE;

    public TableCell(int x, int y, Table table, int screenWidth) {
        this.table=table;
        this.screenWidth=screenWidth;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableCell tableCell = (TableCell) o;
        return getX() == tableCell.getX() &&
                getY() == tableCell.getY();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getX(), getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCurrentVal() {

        return currentVal;
    }

    public void setCurrentVal(int currentVal) {

        this.currentVal = currentVal;

    }


    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isCandidate() {
        return candidate;
    }

    public void setCandidate(boolean b) {
        candidate = b;
    }

    public boolean isShowPreview() {
        return showPreview;
    }

    public void setShowPreview(boolean showPreview) {
        this.showPreview = showPreview;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void draw(Canvas canvas,  Paint pp){
        int fatt = screenWidth / table.getSize();
        int xx = x * fatt + 2;
        int yy = y * fatt + 2;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        canvas.drawRect(xx, yy, xx+fatt , yy+fatt , paint);
        
        canvas.drawBitmap(bitmap, xx, yy, pp);

    }
}
