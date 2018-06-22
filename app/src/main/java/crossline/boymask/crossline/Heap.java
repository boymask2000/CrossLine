package crossline.boymask.crossline;

import android.graphics.Bitmap;

class Heap {
    private static Bitmap[] icons=new Bitmap[36];

    public static void setIcon(int i,Bitmap icon) {
        Heap.icons[i] = icon;
    }

    public static Bitmap getIcon(int i) {
        return icons[i];
    }
}
