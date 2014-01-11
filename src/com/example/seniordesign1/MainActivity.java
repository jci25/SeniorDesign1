package com.example.seniordesign1;

import java.net.URI;
import java.util.Locale;

import com.codebutler.android_websockets.WebSocketClient;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	VideoFeed VF;

	String path;
	
	Context thisContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		thisContext = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    	path = sharedPrefs.getString("prefURL", "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov");
    	//System.out.println(path);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.requestTransparentRegion(mViewPager);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.action_settings:
	    	VF.mVideoView.pause();
	    	Intent i = new Intent(this, UserSettingActivity.class);
	    	startActivity(i);
	    	SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
	    	path = sharedPrefs.getString("prefURL", "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov");
	    	if(path.equals("")){
	    		sharedPrefs.edit().putString("prefURL", "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov").commit();
	    	}
	    	VF.mVideoView.setVideoPath(path);
	    	VF.mVideoView.requestFocus();
	    	VF.mVideoView.start();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment;
			Bundle args;
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			switch (position) {
            /*case 0: // Fragment # 0 - This will show image
                //return ImageFragment.init(position);
            	VF = VideoFeed.init(position);
            	
    	    	//VF.mVideoView.setVideoPath(path);
    	    	//VF.mVideoView.start();
    			return VF;*/
            case 1: // Fragment # 1 - This will show map
    			return MapActivity_.init(position, thisContext);
            	// return SupportMapFragment.newInstance();
            case 0:
            	final MjpegFeed MF = MjpegFeed.init(position);
            	WebSocketClient client = new WebSocketClient(URI.create("ws://192.168.1.192:8000/ws"), new WebSocketClient.Listener(){
                    @Override
                    public void onConnect() {
                    	System.out.println("commected");
                        //Log.d(TAG, "Connected!");
                    }

                    @Override
                    public void onMessage(final String message) {
                        //Log.d(TAG, String.format("Got string message! %s", message));
                    	System.out.println(message);
                    	runOnUiThread(new Runnable() {
                    	     @Override
                    	     public void run() {

                    	    	 MF.setText(message+" cpm");

                    	    }
                    	});
                    	
                    }

                    @Override
                    public void onMessage(byte[] data) {
                        //Log.d(TAG, String.format("Got binary message! %s", toHexString(data));
                    }

                    @Override
                    public void onDisconnect(int code, String reason) {
                        //Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
                    }

                    @Override
                    public void onError(Exception error) {
                        //Log.e(TAG, "Error!", error);
                    	System.out.println("ERROR:: "+error);
                    }
                }, null);
                
                client.connect();
            	return MF;
            default:// Fragment # 2-9 - Will show list
            	fragment = new DummySectionFragment();
    			args = new Bundle();
    			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
    			fragment.setArguments(args);
    			return fragment;
            }
			
			
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

}
