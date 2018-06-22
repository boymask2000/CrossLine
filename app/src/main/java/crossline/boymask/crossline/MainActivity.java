package crossline.boymask.crossline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends Activity {

    private SurfacePanel surface;
    private Table table = null;

    private TextView textTentativi;

    private int screenWidth;
    private int screenHeight;


    private int gameSize = 8;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main1);
        setBanner();
        textTentativi = (TextView) findViewById(R.id.tentativi);

        FrameLayout lay = findViewById(R.id.board);

        getDims();
        surface = new SurfacePanel(getBaseContext(), null, this);
        lay.addView(surface);
        table = new Table(this, 8);
        loadIcons();
        handleButtons();

    }


    public void setTentativi(int n) {
        textTentativi.setText("" + n);
    }

    private void setBanner() {
        MobileAds.initialize(this, "ca-app-pub-6114671792914206/3953667046");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    private void handleButtons() {


        Button cleanAll = findViewById(R.id.cleanAll);
        cleanAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                table.init();
                setTentativi(0);
            }
        });
    }

    private void loadIcons() {

        loadIcon(0, R.mipmap.up);
        loadIcon(1, R.mipmap.down);
        loadIcon(2, R.mipmap.right);
        loadIcon(3, R.mipmap.left);
    }

    private void loadIcon(int i, int id) {
        int s = table.getCellSize() - 2;
        Bitmap icon = BitmapFactory.decodeResource(getResources(), id);

        Bitmap bMapScaled = Bitmap.createScaledBitmap(icon, s, s, true);

        Heap.setIcon(i, bMapScaled);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    private static final int RESULT_SETTINGS = 16;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
/*            case R.id.usaimmagini:
                useImages = true;
                return true;
            case R.id.usanumeri:
                useImages = false;
                return true;*/
            case R.id.size4:
                gameSize = 4;
                table = new Table(this, gameSize);
                setTentativi(0);
                loadIcons();
                break;
            case R.id.size6:
                gameSize = 6;
                table = new Table(this, gameSize);
                setTentativi(0);
                loadIcons();
                break;
            case R.id.size8:
                gameSize = 8;
                table = new Table(this, gameSize);
                setTentativi(0);
                loadIcons();
                break;
/*            case R.id.preview:

                table.preview();
                break;*/
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:
                showUserSettings();
                break;

        }

    }

    private void showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        String livello = sharedPrefs.getString("Livello", "NULL");


        //  Heap.getActivity().finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, RESULT_SETTINGS);


    }

    public SurfacePanel getSurface() {
        return surface;
    }


    public Table getTable() {
        return table;
    }

    public void update() {
        surface.update();
    }

    private void getDims() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();
        Point size = new Point();


        display.getSize(size);

        int v1 = getStatusBarHeight();
        int v2 = getNavigationBarHeight();

        screenWidth = size.x;
        //   screenWidth= (int) (0.9*screenWidth);
        screenHeight = size.y - v1 - v2;
        float f = (float) screenHeight / screenWidth;


    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getNavigationBarHeight() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)//&& !hasMenuKey)
        {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public int getScreenWidth() {
        return screenWidth;
    }


    public void clean() {
        surface.update();
    }
}
