package com.trecstudios.pillbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


public class ScannerDialog extends DialogFragment {
	
//	String ndc=ndc = ((CameraTestActivity) getActivity()).getNDCCode();
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final String result = ((CameraTestActivity) getActivity()).getNDCCode();
		final String ndc = new String(result);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Is this: " + Pill.getNameFromNDC(ndc)).setPositiveButton(R.string.correct, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				 
				Intent intent = new Intent(getActivity(), MainActivity.class);
				intent.putExtra("NDC_CODE", ndc);
				startActivity(intent);
				
				
			}

			


		
			
		}).setNegativeButton(R.string.scan_again, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//SCAN AGAIN
				
			}
			
		});
		return builder.create();
	}
}
