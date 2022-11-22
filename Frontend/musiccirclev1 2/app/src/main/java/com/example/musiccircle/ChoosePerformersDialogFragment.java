package com.example.musiccircle;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Fragments.Event.EventChoosePerformersAdapter;
import com.example.musiccircle.Fragments.Event.EventChoosePerformersAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ChoosePerformersDialogFragment extends DialogFragment {
    private EventChoosePerformersAdapter adapter;
    private Context context;
    private ArrayList<User> users;
    private ArrayList<String> performers;
    private OnSubmitClickListener listener;
    Button btn;
    EditText editText;

    public static ChoosePerformersDialogFragment newInstance(Context context, ArrayList<User> users, OnSubmitClickListener listener) {
        ChoosePerformersDialogFragment dialog = new ChoosePerformersDialogFragment(listener);
        dialog.context = context;
        dialog.users = users;
        dialog.performers = new ArrayList<>();
        return dialog;
    }

    public ChoosePerformersDialogFragment(OnSubmitClickListener listener) {
        this.listener = listener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_event_choose);
        ;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        adapter = new EventChoosePerformersAdapter(context, users);
        recyclerView.setAdapter(adapter);
        btn = view.findViewById(R.id.btnGetSelected);
        editText = view.findViewById(R.id.btnSearchSelectedPerformers);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        btn.setOnClickListener(view1 -> {
            if (adapter.getSelected().size() > 0) {
                for (int i = 0; i < adapter.getSelected().size(); i++) {
                    performers.add(adapter.getSelected().get(i).getUsername());
                    //System.out.println(adapter.getSelected().get(i).getUsername());
                }
            }
            listener.onSubmitClicked(performers);
            dismiss();
        });

        return view;
    }

    private void filter(String text) {
        ArrayList<User> filteredList = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(user);
            }
        }
        adapter.filterList(filteredList);
    }

    public ArrayList<String> getPerformers() {
        return performers;
    }

    public void setPerformers(ArrayList<String> performers) {
        this.performers = performers;
    }
}
