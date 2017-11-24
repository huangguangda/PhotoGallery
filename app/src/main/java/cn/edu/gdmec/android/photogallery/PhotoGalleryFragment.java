package cn.edu.gdmec.android.photogallery;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;

    public static PhotoGalleryFragment newInstance(){
        return new PhotoGalleryFragment ();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate ( savedInstanceState );
        setRetainInstance ( true );
        new FetchItemsTask ().execute (  );

        mThumbnailDownloader = new ThumbnailDownloader<> ();
        mThumbnailDownloader.start ();
        mThumbnailDownloader.getLooper ();
        Log.i ( TAG, "Background thread started" );
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
    @Override
    public void onDestroy(){
        super.onDestroy ();
        mThumbnailDownloader.quit ();
        Log.i ( TAG, "Background thread destroyed" );
    }
    //添加setupAdapter
    private void setupAdapter(){
        if (isAdded ()){
            mPhotoRecyclerView.setAdapter ( new PhotoAdapter(mItems) );
        }
    }

    //383
    private class PhotoHolder extends RecyclerView.ViewHolder{
        //391
        private ImageView mItemImageView;
        //private TextView mTitleTextView;

        public PhotoHolder(View itemView){
            super(itemView);
            mItemImageView = (ImageView) itemView
                    .findViewById ( R.id.fragment_photo_gallery_recycler_view );
            //mTitleTextView = (TextView) itemView;
        }
        public void bindDrawable(Drawable drawable){
            mItemImageView.setImageDrawable ( drawable );
        }
       /* public void bindGalleryItem(GalleryItem item){
            //mTitleTextView.setText ( item.toString () );
        }*/
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<GalleryItem> mGalleryItems;
        public PhotoAdapter(List<GalleryItem> galleryItems){
            mGalleryItems = galleryItems;
        }
        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            LayoutInflater inflater = LayoutInflater.from ( getActivity () );
            View view = inflater.inflate ( R.layout.gallery_item, viewGroup, false );
            return new PhotoHolder ( view );
            /*TextView textView = new TextView ( getActivity () );
            return new PhotoHolder ( textView );*/
        }
        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position){
            GalleryItem galleryItem = mGalleryItems.get ( position );
            Drawable placeholder = getResources ().getDrawable ( R.drawable.bill_up_close );
            photoHolder.bindDrawable ( placeholder );
            mThumbnailDownloader.queueThumbnail ( photoHolder, galleryItem.getUrl() );
            //photoHolder.bindGalleryItem ( galleryItem );
        }
        @Override
        public int getItemCount(){
            return mGalleryItems.size ();
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
