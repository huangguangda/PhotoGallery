package cn.edu.gdmec.android.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2017/11/22.
 */

public class PhotoGalleryFragment extends Fragment{
    private static final String TAG = "PhotoGalleryFragment";
    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<> (  );

    public static PhotoGalleryFragment newInstance(){
        return new PhotoGalleryFragment ();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate ( savedInstanceState );
        setRetainInstance ( true );
        new FetchItemsTask ().execute (  );
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate ( R.layout.fragment_photo_gallery, container, false );
        mPhotoRecyclerView = (RecyclerView) v.findViewById ( R.id.fragment_photo_gallery_recycler_view );
        mPhotoRecyclerView.setLayoutManager ( new GridLayoutManager ( getActivity (), 3 ) );
        setupAdapter();
        return v;
    }
    //添加setupAdapter
    private void setupAdapter(){
        if (isAdded ()){
            mPhotoRecyclerView.setAdapter ( new PhotoAdapter(mItems) );
        }
    }

    //383
    private class PhotoHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTextView;

        public PhotoHolder(View itemView){
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
        public void bindGalleryItem(GalleryItem item){
            mTitleTextView.setText ( item.toString () );
        }
    }



    private class FetchItemsTask extends AsyncTask<Void,Void,List<GalleryItem>>{
        @Override
        protected List<GalleryItem> doInBackground(Void... params){
            /*try {
                String result = new FlickrFetchr ().getUrlString ( "https://www.bignerdranch.com" );
                Log.i ( TAG, "Fetched contents of URL: " + result );
            }catch (IOException ioe){
                Log.e ( TAG, "Failed to fetch URL: ", ioe );
            }*/
            //调用
            return new FlickrFetchr ().fetchItems ();
            //return null;
        }
        @Override
        protected void onPostExecute(List<GalleryItem> items){
            mItems = items;
            setupAdapter();
        }
    }
}