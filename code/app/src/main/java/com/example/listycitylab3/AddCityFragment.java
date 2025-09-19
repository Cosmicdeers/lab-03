package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class AddCityFragment extends DialogFragment{
    private City anothertempcity;

    static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", (Serializable) city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    interface AddCityDialogueListener {
        void addCity(City city);
        void editCity(City city);
    }
    private AddCityDialogueListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof AddCityDialogueListener){
            listener = (AddCityDialogueListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (bundle != null) {
            anothertempcity = (City) bundle.getSerializable("city");

            // used to add text instead of hint
            assert anothertempcity != null;
            String holdname = anothertempcity.getName();
            String holdprovince = anothertempcity.getProvince();
            editCityName.setText(holdname);
            editProvinceName.setText(holdprovince);

            return builder
                    .setView(view)
                    .setTitle("Edit a City")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add",(dialog, which) -> {
                        String cityName = editCityName.getText().toString();
                        String provinceName = editProvinceName.getText().toString();
                        anothertempcity.setName(cityName);
                        anothertempcity.setProvince(provinceName);
                        // made an editCity to properly update "notifyDataSetChanged"
                        listener.editCity(anothertempcity);
                    })
                    .create();

        } else {
            return builder
                    .setView(view)
                    .setTitle("Add a City")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add",(dialog, which) -> {
                        String cityName = editCityName.getText().toString();
                        String provinceName = editProvinceName.getText().toString();
                        listener.addCity(new City(cityName, provinceName));
                    })
                    .create();
        }
    }
}
