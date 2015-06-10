package com.astimac.fb.popup.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.astimac.fb.popup.example.R;
import com.astimac.fb.popup.example.eventbus.ServiceRuntimeChangedEvent;
import com.astimac.fb.popup.example.service.PopUpService;
import com.astimac.fb.popup.example.utils.Prefs;
import de.greenrobot.event.EventBus;

/**
 * Created by alex on 6/10/15.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    /**
     * Start / Stop service button
     */
    private Button mButton;

    /**
     * Small message textview
     */
    private TextView mTextView;

    /**
     * Flag which indicates if service is running
     */
    private boolean mIsServiceRunning;

    public MainFragment() {
        //empty
    }

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButton = (Button) view.findViewById(R.id.main_fragment_start_button);
        mTextView = (TextView) view.findViewById(R.id.main_fragment_desc_text);

        mButton.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        //Checks if service is running and updates the views
        checkIfServiceIsRunning();
    }

    @Override
    public void onPause() {
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onPause();
    }

    private void checkIfServiceIsRunning() {
        mIsServiceRunning = Prefs.isServiceRunning(getActivity());
        updateViews();
    }

    private void updateViews() {
        if(mIsServiceRunning) {
            mTextView.setText(R.string.message_service_running);
            mButton.setText(R.string.button_hide);
        } else {
            mTextView.setText(R.string.message_service_stopped);
            mButton.setText(R.string.button_show);
        }
    }

    /**
     * Event bus listener
     *
     * @param e - event which contains informations about the service
     */
    public void onEventMainThread(@NonNull ServiceRuntimeChangedEvent e) {
        mIsServiceRunning = e.isServiceRunning();

        updateViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_fragment_start_button:
                if (mIsServiceRunning) {
                    getActivity().stopService(new Intent(getActivity(), PopUpService.class));
                } else {
                    getActivity().startService(new Intent(getActivity(), PopUpService.class));
                }
                break;
            default:
                break;
        }
    }
}
