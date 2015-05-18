package example.foodapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

public class Menu extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);
        Toast.makeText(this, "Swipe Right to Go to Your Past Orders", Toast.LENGTH_LONG).show();
        SharedPreferences get = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

}
	@Override
    public boolean onTouchEvent(MotionEvent event) {
         return gestureDetector.onTouchEvent(event);
    }

	SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener(){

		   @Override
		   public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
		     float sensitvity = 18;
		    
		    if((e1.getX() - e2.getX()) > sensitvity){
		    	Intent in=new Intent(Menu.this,History.class);
		    	startActivity(in);
		    }else if((e2.getX() - e1.getX()) > sensitvity){
		    	Toast.makeText(getApplicationContext(),"Swipe Right", Toast.LENGTH_LONG).show();
		    }
		       
		    return super.onFling(e1, e2, velocityX, velocityY);
		   }
		    };
		   
		    @SuppressWarnings("deprecation")
			GestureDetector gestureDetector= new GestureDetector(simpleOnGestureListener);
		    private long lastBackPressTime = 0;
	    	private Toast toast;
	    	@Override
	    	public void onBackPressed() {
	    	 if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
	    	    toast = Toast.makeText(this, "Press back again to close this app", 4000);
	    	    toast.show();
	    	    this.lastBackPressTime = System.currentTimeMillis();
	    	  } else {
	    	    if (toast != null) {
	    	    toast.cancel();
	    	    }
	    	   super.finish();
	    	 }
	    	}
	    	
	    	
	    	
	    
	    	
	    	int start = 0;
   		 int limit = 9999;
   		 String response;
   	  	String build;
   	     String[] catname;
   	     String[] cat;
	    	
	    	public void grabURL(String url) {
  			  Log.v("Android Spinner JSON Data Activity", url);
  			  new GrabURL().execute(url);
  			 }
	    	
	    	private class GrabURL extends AsyncTask<String, Void, String> {
  			  private static final int REGISTRATION_TIMEOUT = 3 * 1000;
  			  private static final int WAIT_TIMEOUT = 30 * 1000;
  			  private final HttpClient httpclient = new DefaultHttpClient();
  			  final HttpParams params = httpclient.getParams();
  			   private boolean error = false;
  			  private ProgressDialog dialog =   new ProgressDialog(Menu.this);
  			 
  			  protected void onPreExecute() {
  			   dialog.setMessage("Fetching Categories...");
  			   dialog.show();
  			  }
  			  
  			protected String doInBackground(String... urls) {
   			 
 			   String URL = null;
 			   try {
 				   
 				   URL = urls[0];
 				   HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
 				   HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
 				   ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);
 				   HttpGet get = new HttpGet(URL);
 				   HttpClient client=new DefaultHttpClient();
 				   HttpResponse responseGet;
 				   try{
 				   responseGet = client.execute(get);
 				   HttpEntity resEntityGet = responseGet.getEntity();  
     	   	       
	   	        		if (resEntityGet != null) {
	   	        		InputStream instream = resEntityGet.getContent();
	   	        		BufferedReader str = new BufferedReader(new InputStreamReader(instream));
	   	   				String ans = new String("");
	   	   				build = new String("");
	   	   				while ((ans = str.readLine()) != null) {
	   	   					build = build + ans;
	   	   					Log.d("JSON", ans);
	   	   					}
	   	        		}
	   	        		return build;
 				   }
 				   catch(Exception e)
 				   {
 					   
 					   grabURL(URL);
 				   }
 				   
 	   	        	
 			   }
 			   catch(Exception e){
 				   
 				   error = true;
 			   }
 			return "I am not responding";
 			  }
 				 
 				  protected void onCancelled() {
 				   dialog.dismiss();
 				   Toast toast = Toast.makeText(Menu.this, 
 				     "Error connecting to Server", Toast.LENGTH_LONG);
 				   toast.setGravity(Gravity.TOP, 25, 400);
 				   toast.show();
 				 
 				  }
 				 
 				  protected void onPostExecute(String content) {
 				   dialog.dismiss();
 				   Toast toast;
 				   if (error) {
 				    toast = Toast.makeText(Menu.this, 
 				      content, Toast.LENGTH_LONG);
 				    toast.setGravity(Gravity.TOP, 25, 400);
 				    toast.show();
 				   } else {
 				   populate(content);
 				   }
 				  }
 		 }

 				private void populate(String content2) {
 					try
 					{
 						JSONArray arr = new JSONArray(content2);
 						String arrlen = Integer.toString(arr.length());
 						Log.d("Array Length", arrlen);
 						int len=Integer.parseInt(arrlen);
 						catname=new String[len];
 						cat=new String[len];
 						for(int i=0;i<arr.length();i++)
 						{
 							JSONObject food = null;
 							food = arr.getJSONObject(i);
 							String category = food.getString("Menu_category");
 							String catid=food.getString("cat_id");
 							catname[i]=category;
 							cat[i]=catid;
 							Log.d("category",category);
 							Log.d("cat_id",catid);
 							Log.d("list",catname[i]);
 							Log.d("list",cat[i]);
 						
 						}
 					}catch(JSONException je)
 					{
 						je.printStackTrace();
 					}
 					
 					ListView lv = (ListView)findViewById(R.id.listview);
	 	    	    catadapter adapter = new catadapter(this, catname);
	 	    		lv.setAdapter(adapter);
	 	            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	 					public void onItemClick(AdapterView<?> arg0, View arg1,
	 							int position, long arg3) {
	 						String keyword=cat[position].toString();
	 						Toast.makeText(getApplicationContext(), "Category="+keyword,Toast.LENGTH_LONG).show();
	 					}
	 				});
	 	      
					
    	        }
 				
}