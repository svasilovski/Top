package com.example.top;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class DialogSelectorFecha extends DialogFragment {
    public static final String FECHA = "fecha";
    public static final String SELECTED_DATE = "selectedDate";

    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance(Locale.ROOT);
        Bundle args = this.getArguments();
        if(args != null){
            long fecha = args.getLong(FECHA);
            calendar.setTimeInMillis(fecha);
        }

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), listener, anio, mes, dia);
    }
}
