package net.tecgurus.app.tecgurusapp.utils;

import android.content.Context;
import android.widget.EditText;

import net.tecgurus.app.tecgurusapp.R;

public class ValidationsUtils {

    public static boolean isEmpty(EditText editText, Context context){
        if (editText !=null && !editText.getText().toString().isEmpty()){
            return false;
        }
        if (editText != null)
            editText.setError(context.getString(R.string.error_field_requiered));
        return true;
    }

}
