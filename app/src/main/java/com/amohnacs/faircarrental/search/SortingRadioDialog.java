package com.amohnacs.faircarrental.search;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.amohnacs.faircarrental.R;

/**
 * Created by adrianmohnacs on 4/22/18.
 */

public class SortingRadioDialog extends DialogFragment {
    public static final String TAG = SortingRadioDialog.class.getSimpleName();

    public static final java.lang.String SORTING_DIALOG_SELECTION = "sorting_dialog_selection";
    private static String[] sortingOptions = new String[] {
            "Company, Ascending",
            "Company, Descending",
            "Distance, Ascending",
            "Distance, Descending",
            "Price, Ascending",
            "Price, Descending"
    };

    private SortingDialogCallback dialogCallback;

    /** This is a callback method which will be executed
     *  on creating this fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int passedInPosition = bundle.getInt(SORTING_DIALOG_SELECTION);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getActivity().getString(R.string.sort));
        builder.setSingleChoiceItems(sortingOptions, passedInPosition, null);

        builder.setPositiveButton(getActivity().getString(R.string.OK), (dialog, which) -> {
            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();
            dialogCallback.onPositiveDialogClick(position);
        });

        builder.setNegativeButton(getActivity().getString(R.string.CANCEL), (dialog, which) -> dialog.dismiss());

        AlertDialog sortingDialog = builder.create();
        return sortingDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SortingDialogCallback) {
            dialogCallback = (SortingDialogCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SortingDialogCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dialogCallback = null;
    }

    public interface SortingDialogCallback {
        void onPositiveDialogClick(int position);
    }
}
