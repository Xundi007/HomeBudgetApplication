package com.example.sanya.homebudgetapplication.util;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.example.sanya.homebudgetapplication.R;

/**
 * Created by Sanya.
 */
public abstract class DyntellAppCompatActivity extends AppCompatActivity implements DyntellActivity {
	protected Toolbar toolbar;

	@Override
	public void setContentView(@LayoutRes int layoutResID) {
		super.setContentView(layoutResID);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("");
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
			case 0:
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}
