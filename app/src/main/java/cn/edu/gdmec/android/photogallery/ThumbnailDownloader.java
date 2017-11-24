package cn.edu.gdmec.android.photogallery;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Jack on 2017/11/24.
 */

public class ThumbnailDownloader<T> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    private Boolean mHasQuit = false;
    private Handler mRequestHandler;
    private ConcurrentMap<T,String> mRequestMap = new ConcurrentHashMap<> (  );

    public ThumbnailDownloader() {
        super(TAG);
    }
    @Override
    public boolean quit(){
        mHasQuit = true;
        return super.quit ();
    }
    public void queueThumbnail(T target, String url){
        Log.i ( TAG, "Got a URL: " + url );
    }

}
