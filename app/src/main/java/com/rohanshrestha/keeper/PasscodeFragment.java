package com.rohanshrestha.keeper;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rohanshrestha.keeper.utils.Prefs;


/**
 * A simple {@link Fragment} subclass.
 */
public class PasscodeFragment extends Fragment implements View.OnClickListener {

    private Button btn_one, btn_two, btn_three, btn_four, btn_five, btn_six,
            btn_seven, btn_eight, btn_nine, btn_zero, btn_delete;
    private EditText tv_passcode;


    private Prefs prefs;

    public PasscodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_passcode, container, false);
        btn_one = (Button) layout.findViewById(R.id.btn_one);
        btn_two = (Button) layout.findViewById(R.id.btn_two);
        btn_three = (Button) layout.findViewById(R.id.btn_three);
        btn_four = (Button) layout.findViewById(R.id.btn_four);
        btn_five = (Button) layout.findViewById(R.id.btn_five);
        btn_six = (Button) layout.findViewById(R.id.btn_six);
        btn_seven = (Button) layout.findViewById(R.id.btn_seven);
        btn_eight = (Button) layout.findViewById(R.id.btn_eight);
        btn_nine = (Button) layout.findViewById(R.id.btn_nine);
        btn_zero = (Button) layout.findViewById(R.id.btn_zero);
        btn_delete = (Button) layout.findViewById(R.id.btn_delete);
        tv_passcode = (EditText) layout.findViewById(R.id.tv_passcode);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prefs = new Prefs(getActivity());

        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        btn_eight.setOnClickListener(this);
        btn_nine.setOnClickListener(this);
        btn_zero.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    private void login() {
        String passcode = tv_passcode.getText().toString();
        if (!passcode.isEmpty() && passcode.equalsIgnoreCase(prefs.getPasscode())) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            Animation shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            shakeAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    tv_passcode.getText().clear();
                    setDeleteButton();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            tv_passcode.startAnimation(shakeAnimation);

        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_one:
                tv_passcode.setText(String.format("%s%s",
                        tv_passcode.getText().toString(),
                        btn_one.getText().toString()));
                break;
            case R.id.btn_two:
                tv_passcode.setText(String.format("%s%s",
                        tv_passcode.getText().toString(),
                        btn_two.getText().toString()));
                break;
            case R.id.btn_three:
                tv_passcode.setText(String.format("%s%s",
                        tv_passcode.getText().toString(),
                        btn_three.getText().toString()));
                break;
            case R.id.btn_four:
                tv_passcode.setText(String.format("%s%s",
                        tv_passcode.getText().toString(),
                        btn_four.getText().toString()));
                break;
            case R.id.btn_five:
                tv_passcode.setText(String.format("%s%s",
                        tv_passcode.getText().toString(),
                        btn_five.getText().toString()));
                break;
            case R.id.btn_six:
                tv_passcode.setText(String.format("%s%s",
                        tv_passcode.getText().toString(),
                        btn_six.getText().toString()));
                break;
            case R.id.btn_seven:
                tv_passcode.setText(String.format("%s%s",
                        tv_passcode.getText().toString(),
                        btn_seven.getText().toString()));
                break;
            case R.id.btn_eight:
                tv_passcode.setText(String.format("%s%s",
                        tv_passcode.getText().toString(),
                        btn_eight.getText().toString()));
                break;
            case R.id.btn_nine:
                tv_passcode.setText(String.format("%s%s",
                        tv_passcode.getText().toString(),
                        btn_nine.getText().toString()));
                break;
            case R.id.btn_zero:
                tv_passcode.setText(String.format("%s%s",
                        tv_passcode.getText().toString(),
                        btn_zero.getText().toString()));
                break;
            case R.id.btn_delete:
                int length = tv_passcode.getText().length();
                if (length > 0) {
                    tv_passcode.getText().delete(length - 1, length);
                } else {
                    getActivity().onBackPressed();
                }
                break;
        }
        setDeleteButton();
        if (tv_passcode.getText().length() == 6)
            login();
    }

    private void setDeleteButton() {
        if (tv_passcode.getText().length() > 0) {
            btn_delete.setText("Delete");
        } else
            btn_delete.setText("Cancel");
    }
}
