package de.tudarmstadt.informatik.secuso.phishedu;

import de.tudarmstadt.informatik.secuso.phishedu.levelintros.Level1AddressBarActivity;
import de.tudarmstadt.informatik.secuso.phishedu.levelintros.Level2WebAddressesActivity;
import de.tudarmstadt.informatik.secuso.phishedu.levelintros.Level3IpNonsenseActivity;
import de.tudarmstadt.informatik.secuso.phishedu.levelintros.Level4SubdomainAddressesActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class LevelGridActivity extends Activity implements
		AdapterView.OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_grid_view);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		ActionBar ab = getActionBar();
		ab.setTitle("Level Überblick");
		// ab.setSubtitle("sub-title");

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(this);

		// gridview.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View v,
		// int position, long id) {
		// Toast.makeText(LevelGridActivity.this, "Level " + position,
		// Toast.LENGTH_SHORT).show();
		//
		// }
		// });

	}

	private class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return gridImages.length;
		}

		public Object getItem(int position) {
			/*
			 * Normally, getItem(int) should return the actual object at the
			 * specified position in the adapter, but it's ignored for this
			 * example. TODO: falls erforderlich
			 */
			return null;
		}

		public long getItemId(int position) {
			/*
			 * getItemId(int) should return the row id of the item, but it's not
			 * needed here. TODO: falls erforederlich
			 */
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(170, 170));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (ImageView) convertView;
			}

			/*
			 * TODO: depending on current Level, call different grid
			 */
			imageView.setImageResource(gridImages[position]);
			return imageView;
		}

		// references to our images
		private Integer[] gridImages = { R.drawable.rect, R.drawable.rect,
				R.drawable.rect, R.drawable.rect, R.drawable.rect,
				R.drawable.rect, R.drawable.rect, R.drawable.rect,
				R.drawable.rect, R.drawable.rect, R.drawable.rect,
				R.drawable.rect, R.drawable.rect, R.drawable.rect,
				R.drawable.rect };
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.level_grid, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		switch (position) {
		case 0:
			Intent level0 = new Intent(this, AwarenessActivity.class);
			startActivity(level0);
			break;

		case 1:
			Intent level1 = new Intent(this, Level1AddressBarActivity.class);
			startActivity(level1);
			break;

		case 2:
			Intent level2 = new Intent(this, Level2WebAddressesActivity.class);
			startActivity(level2);
			break;

		case 3:
			Intent level3 = new Intent(this, Level3IpNonsenseActivity.class);
			startActivity(level3);
			break;
			
		case 4:
			Intent level4 = new Intent(this, Level4SubdomainAddressesActivity.class);
			startActivity(level4);
		default:
			break;

		}
	}

}
