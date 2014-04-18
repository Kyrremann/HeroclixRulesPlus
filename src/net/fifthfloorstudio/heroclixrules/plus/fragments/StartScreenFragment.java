package net.fifthfloorstudio.heroclixrules.plus.fragments;

import heroclix.Rules.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StartScreenFragment extends Fragment {

	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_start_screen, container,
				false);
		return rootView;
	}
}
