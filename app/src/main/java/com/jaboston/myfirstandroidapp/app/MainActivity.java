package com.jaboston.myfirstandroidapp.app;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.jaboston.myfirstandroidapp.app.SearchClass.SearchResults;
import com.jaboston.myfirstandroidapp.app.adapters.ImageSearchResultsAdapter;
import com.jaboston.myfirstandroidapp.app.adapters.StoredImageResultsAdapter;
import com.jaboston.myfirstandroidapp.app.common.JBApplication;
import com.jaboston.myfirstandroidapp.app.database.ImageDataSource;
import com.jaboston.myfirstandroidapp.app.database.models.Image;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private int FIRST_PAGE_CONSTANT = 0;
    private int SECOND_PAGE_CONSTANT = 1;
    private int THIRD_PAGE_CONSTANT = 2;
    private int FOURTH_PAGE_CONSTANT = 3;



    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(position == FIRST_PAGE_CONSTANT){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }

        if(position == SECOND_PAGE_CONSTANT){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, SecondpageFragment.newInstance(position + 1))
                    .commit();
        }

        if(position == THIRD_PAGE_CONSTANT){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ThirdpageFragment.newInstance(position + 1))
                    .commit();
        }

        if(position == FOURTH_PAGE_CONSTANT){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, FourthpageFragment.newInstance(position + 1))
                            .commit();
        }


    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textViewSectionLabel = (TextView)rootView.findViewById(R.id.section_label);
            textViewSectionLabel.setText("Cat 1");



            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */



    public static class SecondpageFragment extends Fragment {

        private RequestQueue queue;
        private ImageLoader imageLoader;
        private ImageListener imageListener;
        private ImageView imageView;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static SecondpageFragment newInstance(int sectionNumber) {
            SecondpageFragment fragment = new SecondpageFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public SecondpageFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_page_2, container, false);
            TextView textViewSectionLabel = (TextView)rootView.findViewById(R.id.section_label);
            textViewSectionLabel.setText("cat 2");
            imageView = (ImageView)rootView.findViewById(R.id.successCatImageView);
            queue = ((JBApplication)this.getActivity().getApplicationContext()).getQueue();
            imageLoader = ((JBApplication)this.getActivity().getApplicationContext()).getImageLoader();

            String url = "http://icons.iconarchive.com/icons/visualpharm/ios7v2/256/Computer-Ip-address-icon.png";

            imageListener = ImageLoader.getImageListener(imageView, R.drawable.loading_wall, R.drawable.loading_wall);
            imageLoader.get(url, imageListener);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ThirdpageFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ThirdpageFragment newInstance(int sectionNumber) {
            ThirdpageFragment fragment = new ThirdpageFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ThirdpageFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_page_3, container, false);
            TextView textViewSectionLabel = (TextView)rootView.findViewById(R.id.section_label);
            textViewSectionLabel.setText("cat 3");




            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class FourthpageFragment extends Fragment {

        private String url ="http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=cats";
        private ListView resultList;
        private RequestQueue queue;
        private Gson gson;
        private JsonObjectRequest searchRequest, extendedRequest;
        private ImageSearchResultsAdapter adapter;
        private String searchText = "cats";
        private List<SearchResults> imageResults;
        private List<Image> cachedImageList;
        private ProgressBar progressBar;

        //Datasource
        private ImageDataSource dataSource;


        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FourthpageFragment newInstance(int sectionNumber) {
            FourthpageFragment fragment = new FourthpageFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public FourthpageFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_page_4, container, false);
//            TextView textViewSectionLabel = (TextView)rootView.findViewById(R.id.section_label);
//            textViewSectionLabel.setText("cat 3");
            resultList = (ListView)rootView.findViewById(R.id.listView);
            progressBar=(ProgressBar)rootView.findViewById(R.id.progressBar);
            resultList.setVisibility(View.GONE);


            // make reference to our images datasource.
            // get all previously stored images if they exist.
            dataSource = new ImageDataSource(this.getActivity());
            try {
                dataSource.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            List<Image> values = dataSource.getAllImages();
            if(values.size() > 0) {
                cachedImageList = dataSource.getAllImages();
                cachedImageList.addAll(values);
                StoredImageResultsAdapter cachedAdapter = new StoredImageResultsAdapter(this.getActivity().getApplicationContext(), cachedImageList);
                resultList.setAdapter(cachedAdapter);
                resultList.setVisibility(View.VISIBLE);

            }

            doSearch();

            return rootView;
        }

        public void doSearch(){
            String searchURL = url + searchText+"&rsz=8";
            searchRequest = new JsonObjectRequest(Request.Method.GET, searchURL, null, new ResponseListener(), new ErrorListener());
            queue = ((JBApplication)this.getActivity().getApplicationContext()).getQueue();

            queue.add(searchRequest);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        private class ResponseListener implements Response.Listener<JSONObject>{
            @Override
            public void onResponse(JSONObject response) {
                setUpResults(response);
                Log.d("ResponseListener", "finished onResponse");
            }
        }
        private class ExtendedResponseListener implements Response.Listener<JSONObject>{
            @Override
            public void onResponse(JSONObject response) {
                addExtendedResults(response);
            }
        }
        private class ErrorListener implements Response.ErrorListener{
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Error, Please Try Again", Toast.LENGTH_LONG).show();
            }
        }

        public int lastCount = 8;

        private void setUpResults(JSONObject response){
            imageResults = new ArrayList<SearchResults>();
            gson = new Gson();
            SearchClass searchClass = gson.fromJson(response.toString(), SearchClass.class);
            //make sure there is data in the response
            Log.d("search results", "did get search results as follows: " + response.toString());

            if(searchClass.getResponse()!=null && getActivity() != null){
                SearchResults[] results = searchClass.getResponse().getResults();
                List<SearchResults> tempList = Arrays.asList(results);

//                List<Image> values = dataSource.getAllImages();
//                if(values.size() > 0) {
//                    cachedImageList.addAll(values);
//                    StoredImageResultsAdapter cachedAdapter = new StoredImageResultsAdapter(this.getActivity().getApplicationContext(), cachedImageList);
//                    resultList.setAdapter(cachedAdapter);
//                    resultList.setVisibility(View.VISIBLE);
//                }

                for(int i = 0; i < tempList.size(); i++){
                    SearchResults result = tempList.get(i);
                    Image image = new Image();
                    image.setId(i);
                    image.setUrl(result.getURL());
                    dataSource.createImage(result.getURL());
                    cachedImageList = dataSource.getAllImages();
                }

                imageResults.addAll(tempList);
                adapter = new ImageSearchResultsAdapter(this.getActivity().getApplicationContext(), imageResults);
                resultList.setAdapter(adapter);
                resultList.setVisibility(View.VISIBLE);
                Log.d("search results", "did set adapter with imageResults");
                progressBar.setVisibility(View.GONE);
                //get the next 8 results in the list
                String searchURL = url + searchText+"&start=8&rsz=8";
                extendedRequest = new JsonObjectRequest(Request.Method.GET, searchURL, null, new ExtendedResponseListener(), new ErrorListener());
                queue.add(extendedRequest);
                progressBar.setVisibility(View.GONE);
            }

        }

        public void addExtendedResults(JSONObject response){
            SearchClass searchClass = gson.fromJson(response.toString(), SearchClass.class);
            if(searchClass.getResponse()!=null){
                SearchResults[] results = searchClass.getResponse().getResults();
                List<SearchResults> tempList = Arrays.asList(results);
                imageResults.addAll(tempList);
                progressBar.setVisibility(View.GONE);
            }
        }

    }

}
