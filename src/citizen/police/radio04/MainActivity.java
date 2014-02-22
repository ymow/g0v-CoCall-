package citizen.police.radio04;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
 
public class MainActivity extends Activity {
	// Declare Variables
	ListView listview;
	List<ParseObject> ob;
	ProgressDialog mProgressDialog;
	ArrayAdapter<String> adapter;
	ArrayList<String>  AVGirlList= new ArrayList<String>();
	  private AdView adView;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_main);
	    adView = (AdView)findViewById(R.id.adView);
	    AdRequest request = new AdRequest.Builder()
	    .setGender(AdRequest.GENDER_MALE)
	    .setBirthday(new GregorianCalendar(1995, 1, 1).getTime())
	    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	     .addTestDevice("2DA6E0791384A56BFA49EC62B3880F42") // HTC C300
	    .build();
	    adView.loadAd(request);
	    
	    // Execute RemoteDataTask AsyncTask
		new RemoteDataTask().execute();

	}

	// RemoteDataTask AsyncTask
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(MainActivity.this);
			// Set progressdialog title
			mProgressDialog.setTitle("ＣＰＲ");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Locate the class table named "Country" in Parse.com
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"CPRStation");
			//add new video
//			ParseObject addGirl = new ParseObject("AVGirlList");
//			addGirl.put("GirlName", "XXX");
//			addGirl.put("VideoName", "Where should we go for lunch?");
//			addGirl.put("ClickNumber", "0");
//			addGirl.saveInBackground();
//			query.orderByDescending("_created_at");
			//Ranking
			query.orderByDescending("ClickNumber");
			
			try {
				ob = query.find();
			} catch (ParseException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into an ArrayAdapter
			adapter = new ArrayAdapter<String>(MainActivity.this,
					R.layout.listview_item);
			int i=0;
			ArrayList<String>  GirlList= new ArrayList<String>();
 
			String[] mStringArray ;
			
			// Retrieve object "name" from Parse.com database
			for (ParseObject AVgirl : ob) {
				i++;
				
				adapter.add(i+"."+(String) AVgirl.get("GirlName"));
				
//				Log.d("123",(String) AVgirl.get("GirlName"));
			//GirlList.add((String) AVgirl.get("GirlName"));
			}
		 
			
			// Binds the Adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
			// Capture button clicks on ListView items
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					 
 		 
					// Send single item click data to SingleItemView Class
					Intent i = new Intent(MainActivity.this,SingleItemView.class);
					// Pass data "name" followed by the position
					i.putExtra("name", ob.get(position).getString("GirlName")
							.toString());
					
					String girlname1 = "b";
					Intent girlname = new Intent(MainActivity.this,	SingleItemView.class); 
					girlname.putExtra(girlname1, ob.get(position).getString("GirlName")
							.toString());
					
			
					ParseObject CN = new ParseObject("AVGirlList");
					String ItemobjectId = "a";
					Intent Plus1 = new Intent(MainActivity.this,SingleItemView.class); 
					//Plus1.putExtra(ItemobjectId, ob.get(position).getString("objectId")
					//		.toString());
					 String Plus2 = CN.getObjectId();
					System.out.println("????????????"+girlname1+"ObjectId ="+Plus2);
					
					
					String PlusAdd =  Plus1.toString();
 
			        ParseQuery<ParseObject> query = ParseQuery.getQuery("AVGirlList");
			        query.getInBackground(PlusAdd, new GetCallback<ParseObject>() {
			          public void done(ParseObject object, ParseException e) {
			            if (e == null) {
			             int s = object.getInt("ClickNumber");
			             System.out.println(s);
			             s = s+1;
						 object.put("ClickNumber",  s);
			             object.saveInBackground();
			             
			            } else {
			              
			            }
			          }
			        });
			        
					// Open SingleItemView.java Activity
					startActivity(i);
				}
			});
		}
		
	}
	@Override
	protected void onStart() {
	  super.onStart();
	  ActionBar actionBar = this.getActionBar();
	  actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);
	}
	 @Override
	  public void onPause() {
	    adView.pause();
	    super.onPause();
	  }

	  @Override
	  public void onResume() {
	    super.onResume();
	    adView.resume();
	  }

	  @Override
	  public void onDestroy() {
	    adView.destroy();
	    super.onDestroy();
	  }
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {

		  getMenuInflater().inflate(R.menu.activity_main, menu);
	        ActionBar actionBar = getActionBar();  
	        actionBar.setDisplayHomeAsUpEnabled(false);  
	        MenuItem AddItem = menu.findItem(R.id.menu_add);
	     

	        return super.onCreateOptionsMenu(menu);
	    }
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	      // Handle presses on the action bar items
	      switch (item.getItemId()) {
	          case R.id.menu_add:
	                startActivity(new Intent(MainActivity.this,
	                        Record.class));
	              return true;
	 //         case R.id.menu_settings:
	            //  openSettings();
	             // return true;
	          default:
	              return super.onOptionsItemSelected(item);
	      }
	  }
}