package com.example.seniordesign1;


import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.codebutler.android_websockets.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MjpegFeed extends Fragment {
    int fragVal;
    //private String path = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
    private String path = "http://192.168.1.135:8080/?action=stream";
    
    private MjpegView mv = null;
    
    private TextView tv = null;
    
    private int width = 640;
    private int height = 480;
    
    private boolean suspending = false;
    
 
    static MjpegFeed init(int val) {
    	MjpegFeed MjpegFeed = new MjpegFeed();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        MjpegFeed.setArguments(args);
        
        return MjpegFeed;
    }
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
        
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View layoutView = inflater.inflate(R.layout.mjpeg_view, container,
                false);
        
        mv = (MjpegView) layoutView.findViewById(R.id.mv);
        tv = (TextView) layoutView.findViewById(R.id.Gieger);
        
        new DoRead().execute(path);
        
        return layoutView;
    }
    
    
    public void setText(String item) {
        TextView view = (TextView) getActivity().findViewById(R.id.Gieger);
        view.setText(item);
      }
    
    public void onStart() {
        super.onStart();
    }
    public void onPause() {
        super.onPause();
        if(mv!=null){
        	mv.stopPlayback();
        }
    }
    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
    	
    	
        super.onDestroy();
    }
    
    
    public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
        protected MjpegInputStream doInBackground(String... url) {
            //TODO: if camera has authentication deal with it and don't just not work
            HttpResponse res = null;
            DefaultHttpClient httpclient = new DefaultHttpClient(); 
            HttpParams httpParams = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 5*1000);
            try {
                res = httpclient.execute(new HttpGet(URI.create(url[0])));
                if(res.getStatusLine().getStatusCode()==401){
                    //You must turn off camera User Access Control before this will work
                    return null;
                }
                return new MjpegInputStream(res.getEntity().getContent());  
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                //Error connecting to camera
            } catch (IOException e) {
                e.printStackTrace();
                //Error connecting to camera
            }
            return null;
        }

        protected void onPostExecute(MjpegInputStream result) {
            mv.setSource(result);
            //if(result!=null) result.setSkip(1);
            mv.setDisplayMode(MjpegView.SIZE_FULLSCREEN);
            mv.showFps(false);
        }
    }
    
    
}