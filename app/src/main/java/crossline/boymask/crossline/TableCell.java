package crossline.boymask.crossline;

import java.util.Objects;

public class TableCell {
    private boolean show = true;
    private boolean showPreview = false;
    private int x;
    private int y;
    private int currentVal;


    private boolean candidate;

    public TableCell(int x, int y) {
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

}
