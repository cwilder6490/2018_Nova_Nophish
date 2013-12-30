package de.tudarmstadt.informatik.secuso.phishedu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendController;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendControllerImpl;

public abstract class PhishBaseActivity extends Fragment implements OnClickListener {
	
	/**
	 * Get the id of the Layout of this fragment
	 * @return the base layout 
	 */
	public abstract int getLayout();
	public void onClick(View view){};
	
	/**
	 * If the fragment wants to react on the backpressed button it can implements this function
	 * @return return true to continue with the back event. False otherwise. 
	 */
	public boolean onBackPressed(){return true;};
	/**
	 * If the Fragment wants to set the titles it can overwrite this
	 */
	private int getTitle(){
		return BackendControllerImpl.getInstance().getLevelInfo(getLevel()).titleId;
	};
	private int getSubTitle(){
		int subtitle = BackendControllerImpl.getInstance().getLevelInfo(getLevel()).subTitleId;
		if(subtitle == getTitle()){
			return 0;
		}
		return subtitle;
	};
	/**
	 * If there are clickable elements on this page you must list them here and implement {@link #onClick(View)}
	 * @return the list of resource IDs of the clickable elements.
	 */
	public int[] getClickables(){return new int[0];};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(getLayout(), container, false);

		for (int i : getClickables()) {
			v.findViewById(i).setOnClickListener(this);
		}
		
		setTitles();

		return v;		 
	}
	
	private void setTitles(){
		android.support.v7.app.ActionBar ab = ((ActionBarActivity)getActivity()).getSupportActionBar();
		if(getTitle()!=0){
			ab.setTitle(getTitle());
		}
		if(getSubTitle()!=0){
			ab.setSubtitle(getSubTitle());
		}
	}

	protected void updateScore(View view){
		if(view == null){
			return;
		}
		RelativeLayout scores = (RelativeLayout) view.findViewById(R.id.score_relative);
		if(scores != null){
			TextView urlsText = (TextView) scores.findViewById(R.id.urls);
			TextView urlsGoalText = (TextView) scores.findViewById(R.id.urls_goal);
			ImageView lifeOne = (ImageView) scores.findViewById(R.id.life_1);
			ImageView lifeTwo = (ImageView) scores.findViewById(R.id.life_2);
			ImageView lifeThree = (ImageView) scores.findViewById(R.id.life_3);
			TextView LevelScoreText = (TextView) scores.findViewById(R.id.level_score);

			urlsText.setText(Integer.toString(BackendControllerImpl.getInstance().getCorrectlyFoundURLs()));
			urlsGoalText.setText(Integer.toString(BackendControllerImpl.getInstance().levelCorrectURLs()));
			LevelScoreText.setText(Integer.toString(BackendControllerImpl.getInstance().getLevelPoints()));
			
			int remaininLives = BackendControllerImpl.getInstance().getLifes();
			
			//now hide hearts if required
			switch (remaininLives) {
			case 0:
				//hide all hearts
				lifeOne.setVisibility(View.INVISIBLE);
				lifeTwo.setVisibility(View.INVISIBLE);
				lifeThree.setVisibility(View.INVISIBLE);
				break;
			case 1:
				//hide heart 1 and 2
				lifeOne.setVisibility(View.INVISIBLE);
				lifeTwo.setVisibility(View.INVISIBLE);
				break;
			case 2:
				//hide only heart 1
				lifeOne.setVisibility(View.INVISIBLE);
			default:
				break;
			}
		}
	}
	
	
	protected void levelRestartWarning() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

		// Setting Dialog Title
		alertDialog.setTitle(getString(R.string.level_restart_title));

		// Setting Dialog Message
		alertDialog.setMessage(getString(R.string.level_restart_text));

		alertDialog.setPositiveButton(R.string.level_restart_positive_button, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				BackendControllerImpl.getInstance().restartLevel();
			}
		});

		alertDialog.setNegativeButton(R.string.level_restart_negative_button,
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	
	protected void levelCanceldWarning() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

		// Setting Dialog Title
		alertDialog.setTitle(getString(R.string.level_cancel_title));

		// Setting Dialog Message
		alertDialog.setMessage(getString(R.string.level_cancel_text));

		alertDialog.setPositiveButton(R.string.level_cancel_positive_button, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				NavUtils.navigateUpFromSameTask(getActivity());
			}
		});

		alertDialog.setNegativeButton(R.string.level_cancel_negative_button,
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(getActivity());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	int getLevel(){
		return BackendControllerImpl.getInstance().getLevel();
	}
}
