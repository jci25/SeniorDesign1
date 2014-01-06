package com.example.seniordesign1;


import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoFeed extends Fragment {
    int fragVal;
    //private String path = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
    private String path = "http://192.168.1.114:8080/?action=stream";
    VideoView mVideoView;
    
 
    static VideoFeed init(int val) {
    	VideoFeed VideoFeed = new VideoFeed();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        VideoFeed.setArguments(args);
        
        return VideoFeed;
    }
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
        
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.vid_frag, container,
                false);
        
        mVideoView = (VideoView) layoutView.findViewById(R.id.video_view);
		/*
         * Alternatively,for streaming media you can use
         * mVideoView.setVideoURI(Uri.parse(URLstring));
         */
       //mVideoView.setVideoPath(path);
		
		//MediaController mediacontroller1 = new MediaController(null);
		//mediacontroller1.setAnchorView(mVideoView);
		
        //mVideoView.setMediaController(mediacontroller1);
        mVideoView.setVideoPath(path);
        
        mVideoView.requestFocus();
        
        System.out.println(mVideoView.getBufferPercentage());
        
        mVideoView.setOnPreparedListener(new OnPreparedListener(){

			@Override
			public void onPrepared(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				//mVideoView.start();
				System.out.println(mVideoView.getBufferPercentage());
				mVideoView.start();
			}
        });
        
        System.out.println(mVideoView.getBufferPercentage());
       // mVideoView.start();
        System.out.println(mVideoView.getBufferPercentage());
        View tv = layoutView.findViewById(R.id.section_label);
        ((TextView) tv).setText("Temp Data" + fragVal);
        
        
        return layoutView;
    }
    
    
}