package com.moneyxx;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TransferRechargeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer_recharge);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transfer_recharge, menu);
		return true;
	}

}
