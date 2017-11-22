package cn.edu.gdmec.android.photogallery;

/**
 * Created by Jack on 2017/11/22.
 */

public class GalleryItem {
    private String mCaption;
    private String mId;
    private String mUrl;
    
    @Override
    public String toString(){
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption=caption;
    }

    public void setId(String id) {
        mId=id;
    }

    public void setUrl(String url) {
        mUrl=url;
    }
}
