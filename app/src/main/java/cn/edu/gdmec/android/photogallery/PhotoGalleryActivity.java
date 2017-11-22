package cn.edu.gdmec.android.photogallery;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PhotoGalleryActivity extends SingleFragmentActivity {
 /* @Override
    protected Fragment createFragment{
        return NerdLauncherFragment.newInstance();
    }*/
    //创建

    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment.newInstance ();
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.fragment_nerd_launcher );
    }*/
}