package com.jaboston.myfirstandroidapp.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jaboston.myfirstandroidapp.app.R;
import com.jaboston.myfirstandroidapp.app.common.JBApplication;

import com.jaboston.myfirstandroidapp.app.SearchClass.SearchResults;

import java.util.List;

/**
 * Created by josephboston on 12/05/2014.
 */
public class ImageSearchResultsAdapter extends BaseAdapter{
    private Context context;
    private List<SearchResults> results;
    private ImageLoader loader;

    public ImageSearchResultsAdapter(Context context, List<SearchResults> results) {
        this.context = context;
        this.results = results;
        loader = ((JBApplication)context.getApplicationContext()).getImageLoader();
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.image_results_layout, null, false);
            ViewHolder holder = new ViewHolder();
            holder.resultImage = (NetworkImageView)view.findViewById(R.id.imageView);
            view.setTag(holder);
        }
        SearchResults selectedResult = (SearchResults)getItem(position);
        ViewHolder holder = (ViewHolder)view.getTag();
        holder.resultImage.setImageUrl(selectedResult.getURL(), loader);
        //holder.resultImage.setScaleType(ImageView.ScaleType.CENTER);

        return view;
    }

    static class ViewHolder{
        NetworkImageView resultImage;
    }


}
