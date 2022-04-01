package rhodonite.com.jsonsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;


import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.InputStream;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private ArrayList<ListModel> mListModel;
    Context mContext;
    public MyAdapter(Context c, ArrayList<ListModel> data) {
        this.mListModel = data;
        this.mContext = c;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mListModel.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mListModel.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.customlistview, null);
        ImageView iv = (ImageView) vi.findViewById(R.id.icon);
        TextView nickName = (TextView) vi.findViewById(R.id.nickName);
        nickName.setText(mListModel.get(position).getName());
        iv.setImageResource(0);
        TagFlowLayout mFlowLayout = (TagFlowLayout) vi.findViewById(R.id.id_flowlayout);


        mFlowLayout.setAdapter(new TagAdapter<String>(mListModel.get(position).getTag()) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }

        });

        new DownloadImageTask(iv)
                .execute(mListModel.get(position).getUrl());

        return vi;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
