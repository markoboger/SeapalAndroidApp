package de.htwg.seapal.aview.gui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import de.htwg.seapal.R;

public class PictureDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    public interface PictureDialogFragmentListener {
        void onFinishEditDialog(String inputText);
    }

    private EditText mEditText;

    public PictureDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picture_fragment, container);
        mEditText = (EditText) view.findViewById(R.id.txt_other);
        getDialog().setTitle(R.string.picture_headder);

        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);

        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            PictureDialogFragmentListener activity = (PictureDialogFragmentListener) getActivity();
            activity.onFinishEditDialog(mEditText.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }

}
