package example.foodapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.widget.ListAdapter;

import java.util.ArrayList;  
import java.util.Arrays;  
  
import android.app.Activity;  
import android.content.Intent;
import android.os.Bundle;  
import android.widget.ArrayAdapter;  
import android.widget.ListView;  
  
public class Delloc extends ListActivity {  
    
  private ListView mainListView ;  
  private ArrayAdapter<String> listAdapter ; 
  
  String[] dellocation = new String[] { "HSR Layout", "Koramangala", "Indiranagar", "AKR Tech Park(Lunch)",  
          "Sarjapur Road", "Domlur(Lunch)", "Bellandur(Lunch)", "Embassy Tech Village(Lunch)"}; 
    
  /** Called when the activity is first created. */  
  @Override  
  public void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
    setContentView(R.layout.main);  
      
    // Find the ListView resource.   
    mainListView = (ListView) findViewById( R.id.mainListView );  
  
    mainListView.setSelector( R.layout.listitembackground); 
       
    ArrayList<String> locList = new ArrayList<String>();  
    locList.addAll( Arrays.asList(dellocation) );  
      
    
    listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, locList); 
    mainListView.setAdapter( listAdapter );
  
  
}
  public void onListItemClick(ListView Parent, View v, int position,long id)
  {
      startActivity(new Intent(Delloc.this, Menu.class)); 
      //Toast.makeText(this, "Clicked on : " + names[position], Toast.LENGTH_LONG).show();
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
  }
}