package de.tudarmstadt.informatik.secuso.phishedu;

import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendController;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishResult;
import android.app.ActionBar;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ResultActivity extends SwipeActivity {
	public static int RESULT_GUESSED = PhishResult.getMax()+1;
	
	//int level; is used as index for the consequences type
	
	protected static int[] resultLayoutIDs;
	private int result = PhishResult.Phish_Detected.getValue();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.result=getIntent().getIntExtra(Constants.EXTRA_RESULT,0);
		setTitle();
	}
	
	public ResultActivity(){
		//We need one layout for each PhishResult + You guessed 
		resultLayoutIDs=new int[PhishResult.values().length+1];
		resultLayoutIDs[PhishResult.Phish_Detected.getValue()]=R.layout.result_phish_detected;
		resultLayoutIDs[PhishResult.NoPhish_Detected.getValue()]=R.layout.result_nophish_detected;
		resultLayoutIDs[PhishResult.Phish_NotDetected.getValue()]=R.layout.result_phish_notdetected;
		resultLayoutIDs[PhishResult.NoPhish_NotDetected.getValue()]=R.layout.result_nophish_notdetected;
		resultLayoutIDs[RESULT_GUESSED]=R.layout.you_guessed;
	}
	
	protected void onStartClick(){
		setResult(RESULT_OK);
		finish();
	}

	@Override
	protected String startButtonText() {
		return "Nächste URL";
	}

	@Override
	protected int getPageCount() {
		return 1;
	}

	/**
	 * Disable back button so he can not guess again.
	 */
	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	protected View getPage(int page, LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		View view =inflater.inflate(resultLayoutIDs[this.result], container, false);
		TextView urlText = (TextView) view.findViewById(R.id.url);
		setUrlText(urlText);
		return view;
	}

	private void setTitle(){
		ActionBar ab = getActionBar();
		if(this.result==PhishResult.Phish_Detected.getValue() || this.result==PhishResult.NoPhish_Detected.getValue()){
			ab.setTitle(getString(R.string.correct));
		}else{
			ab.setTitle(getString(R.string.wrong));
		}
		if(this.result==PhishResult.Phish_Detected.getValue() || this.result==PhishResult.Phish_NotDetected.getValue()){
			ab.setSubtitle(getString(R.string.phish));
		}else{
			ab.setSubtitle(getString(R.string.no_phish));
		}
		
		ab.setIcon(getResources().getDrawable(R.drawable.desktop));
	}
}