package citizen.police.radio04;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
 
 
public class SingleItemView extends Activity {
	private static final String  BrowserBug = null;
	// Declare Variables
	TextView txtname;
	String name;
	  private AdView adView2;

 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		 
	    // ?????? adView???
	    adView2 = (AdView)findViewById(R.id.adView2);
 
	    // ?????????????????????
//	    AdRequest adRequest = new AdRequest.Builder().build();
	    AdRequest request = new AdRequest.Builder()
	    .setGender(AdRequest.GENDER_MALE)
	    .setBirthday(new GregorianCalendar(1995, 1, 1).getTime())
	    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	     .addTestDevice("2DA6E0791384A56BFA49EC62B3880F42") // ?????? HTC C300 ????????????
	    .build();
	    // ????????????????????? adView???
	    adView2.loadAd(request);
	    
		// Retrieve data from MainActivity on item click event
		Intent i = getIntent();
		
		// Get the name
		name = i.getStringExtra("name");
		
		// Locate the TextView in singleitemview.xml
		txtname = (TextView) findViewById(R.id.name);
		
		// Load the text into the TextView
		txtname.setText(name);
		
		
	//Search URL
//		Uri uri = Uri.parse("https://www.google.com.tw/search?q="+name);
//		 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		 startActivity(intent);
		 
		 //AV Browser
		   String TAG = BrowserBug;
		 WebView avBrowser=(WebView)findViewById(R.id.AVbrowser);
		 WebSettings websettings = avBrowser.getSettings();  
	        websettings.setSupportZoom(true);  
	        websettings.setBuiltInZoomControls(false);   
	        websettings.setJavaScriptEnabled(true);  	         
	        avBrowser.setWebViewClient(new WebViewClient());  
	        avBrowser.loadUrl("https://www.google.com.tw/search?q="+name);  
	        getActionBar().setTitle(name);
	        //getSupportActionBar().setTitle("Hello world App");  // provide compatibility to all the versions   
	        Log.v(TAG, "WebView OK");

	        avBrowser.setWebViewClient(new MyWebViewClient());
 

 
	           
	}
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView avBrowser, String url) {
	
		if (Uri.parse(url).getHost().equals("www.example.com")) {
		//???????????????????????????????????????????????????WebView?????????
		  
			Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	  			startActivity(intent1);
		        Log.v("MyWebViewClient", "c" );
		return false;
		}
		// ?????????????????????????????????????????????Activity????????????URL
        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
  			startActivity(intent2);
	        Log.v("MyWebViewClient", "d");
		return true;
		}
	}
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
 
	        return super.onCreateOptionsMenu(menu);
	    }
 
 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	        case android.R.id.home:
	            this.finish();
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
    }
