package com.example.a3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.a3.MainActivity;
import com.example.a3.R;


public class FirstFragment extends ListFragment {
    private static final String TAG = "FirstFragment";
    private ListSelectionListener mListener = null;
    private int retainPos = -1;

    // Callback interface that allows this Fragment to notify the MainActivity when
    // user clicks on a List Item
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        Log.i(TAG, getClass().getSimpleName() + ": " + pos);


        // Indicates the selected item has been checked
        getListView().setItemChecked(pos, true);

        // Inform the MainActivity that the item in position pos has been selected
        mListener.onListSelection(pos);

    }

    @Override
    public void onAttach(Context activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);

        try {

            // Set the ListSelectionListener for communicating with the MainActivity
            // Try casting the containing activity to a ListSelectionListener
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            // Cast failed: This is not going to work because containing activity may not
            // have implemented onListSelection() method
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // UB:  Notice that the superclass's method does an OK job of inflating the
    //      container layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle saveState) {
        super.onSaveInstanceState(saveState);
        saveState.putInt("retainPos", getSelectedItemPosition());
//        saveState.putInt("retainPos", retainPos);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.fragment_first, MainActivity.vacatSpotArray));

//        if(savedState != null) {
//            retainPos = savedState.getInt("retainPos");
//            getListView().setItemChecked(retainPos, true);
//        }

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

}