package com.pk.vanilla.presentation.common;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pk.vanilla.R;
import com.pk.vanilla.presentation.details.ImageDetailFragment;
import com.pk.vanilla.presentation.search.ImageSearchFragment;

import java.util.List;

public class BaseActivity extends AppCompatActivity implements StateChanger {

    private boolean doubleBackToExitPressedOnce = false;
    private final static int UI_UPDATE_DELAY = 100;
    private final static int BACK_PRESSING_DELAY = 2000;

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList.size() > 1) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fragmentList.get(fragmentList.size() - 1));
            changeState(fragmentList.get(0));
            transaction.commit();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, BACK_PRESSING_DELAY);
        }
    }

    public void showSoftKeyboardForInput(TextView textView) {
        if (textView == null) {
            return;
        }
        textView.requestFocus();
        textView.postDelayed(() -> {
            final InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (keyboard != null) {
                keyboard.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT);
            }
        }, UI_UPDATE_DELAY);
    }

    public void hideSoftKeyboardForInput(EditText textView) {
        if (textView == null) {
            return;
        }
        textView.clearFocus();
        IBinder windowToken = textView.getWindowToken();
        final InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (keyboard != null) {
            keyboard.hideSoftInputFromWindow(windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    @Override
    public void changeState(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
