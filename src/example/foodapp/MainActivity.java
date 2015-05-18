package example.foodapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity{
	
	Button Delivery;
	Button dishes;
	Button Order;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		Delivery=(Button) findViewById(R.id.DeliveryBut);
		dishes=(Button) findViewById(R.id.DishesBut);
		Order=(Button) findViewById(R.id.OrderBut);
		
		Delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	MainActivity.this.startActivity(new Intent(MainActivity.this,Delloc.class));
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
