package com.example.lmusic;

import android.app.AlertDialog;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.lmusic.R;

import java.util.ArrayList;

public class MultipleCDialogFragment extends DialogFragment {

    public interface onMultiChoiceListener{
        void positiveChecked(String[] list, ArrayList<String> selectedItelList);
        void negativeChecked();
    }

    onMultiChoiceListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (onMultiChoiceListener) context;
        } catch (Exception e) {
            throw  new ClassCastException(getActivity().toString()+"MultipleChoice must implemented");
        }

    }

    ArrayList<String> selectedItemList = new ArrayList<>();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] list = getActivity().getResources().getStringArray(R.array.choice_items);
         builder.setTitle("Select Your Choice")
        .setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
if(isChecked){
    selectedItemList.add(list[which]);
}else{
    selectedItemList.remove(list[which]);
}
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        mListener.positiveChecked(list,selectedItemList);
    }
}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        mListener.negativeChecked();
    }
});

 builder.create();
 return builder.show();
    }
}
