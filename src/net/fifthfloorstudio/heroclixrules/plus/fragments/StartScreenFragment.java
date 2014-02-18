package net.fifthfloorstudio.heroclixrules.plus.fragments;

import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.fifthfloorstudio.heroclixrules.plus.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.fifthfloorstudio.heroclixrules.plus.utils.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class StartScreenFragment extends Fragment {

    private View rootView;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_start_screen, container,
				false);

        loadJsonAndPopulateView();
		
		return rootView;
	}

    private void loadJsonAndPopulateView() {
        JSONObject changelog = JSONParser.getJsonChangelog(getActivity());
        populateviewWithJSON(changelog);
    }

    private void populateviewWithJSON(JSONObject changelog) {
        LinearLayout parent = (LinearLayout) rootView.findViewById(R.id.layout_changelog);
        Iterator<String> keys = changelog.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                addChangeToView(key, changelog.getJSONArray(key), parent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addChangeToView(String title, JSONArray changes, LinearLayout parent) throws JSONException {
        TextView titleView = new TextView(getActivity());
        titleView.setText(title);
        parent.addView(titleView);

        String stringChanges = createStringFromChanges(changes);
        TextView changeView = new TextView(getActivity());
        changeView.setText(stringChanges);
        parent.addView(changeView);
    }

    private String createStringFromChanges(JSONArray array) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length(); i++) {
            stringBuilder.append(" • ");
            stringBuilder.append(array.get(i));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void onPowerClicked(View v) {
        switch (v.getId()) {
            case R.id.speed:
                switchToPower("Speed power");
                break;
            case R.id.attack:
                switchToPower("Attack power");
                break;
            case R.id.defense:
                switchToPower("Defense power");
                break;
            case R.id.damage:
                switchToPower("Damage power");
                break;
        }
    }

    private void switchToPower(String power) {
        Fragment fragment = new RuleListFragment();
        Bundle args = new Bundle();
        args.putString(RuleListFragment.ARG_RULE_TITLE, power);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();
    }

}
