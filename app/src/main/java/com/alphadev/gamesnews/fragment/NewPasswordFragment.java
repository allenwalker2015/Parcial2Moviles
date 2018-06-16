package com.alphadev.gamesnews.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.api.GamesNewsAPIService;
import com.alphadev.gamesnews.api.data.remote.GamesNewsAPIUtils;
import com.alphadev.gamesnews.api.pojo.UserPOJO;
import com.alphadev.gamesnews.viewmodel.GamesNewsViewModel;

import java.io.IOException;

public class NewPasswordFragment extends Fragment {

    EditText old_password, new_password, confirm_password;

    Button save;
    private GamesNewsViewModel gamesNewsViewModel;
    private SharedPreferences sp;
    private GamesNewsAPIService service;
    // private OnFragmentInteractionListener mListener;

    public NewPasswordFragment() {
        // Required empty public constructor
    }

    public static NewPasswordFragment newInstance() {
        NewPasswordFragment fragment = new NewPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_password, container, false);
        old_password = v.findViewById(R.id.old_password_txt);
        new_password = v.findViewById(R.id.new_password_txt);
        confirm_password = v.findViewById(R.id.confirm_password_txt);
        save = v.findViewById(R.id.save_buttton);
        gamesNewsViewModel = ViewModelProviders.of(this).get(GamesNewsViewModel.class);
        sp = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        service = GamesNewsAPIUtils.getAPIService(getContext());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_password_txt = new_password.getText().toString();
                String confirm_password_txt = confirm_password.getText().toString();
                String old_password_txt = old_password.getText().toString();
                if (TextUtils.isEmpty(old_password_txt)) {
                    old_password.setError(getString(R.string.empty_field));
                }
                if (TextUtils.isEmpty(new_password_txt)) {
                    new_password.setError(getString(R.string.empty_field));
                }
                if (TextUtils.isEmpty(confirm_password_txt)) {
                    confirm_password.setError(getString(R.string.empty_field));
                }
                if (!new_password_txt.equals(confirm_password_txt)) {
                    confirm_password.setError(getString(R.string.not_match));
                }
                if (!TextUtils.isEmpty(new_password_txt) && !TextUtils.isEmpty(confirm_password_txt) && new_password_txt.equals(confirm_password_txt)) {
                    doInBackground task = new doInBackground();
                    String token = sp.getString("token", "");
                    String user = sp.getString("userId", "");
                    task.execute("Bearer " + token, user, new_password_txt, old_password_txt);
                }
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class doInBackground extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                UserPOJO user = service.getUserDetail(strings[0]).execute().body();
                if (user != null) {
                    if (user.getPassword().equals(strings[3])) {
                        if (gamesNewsViewModel.updatePassword(strings[0], strings[1], strings[2])) {
                            return R.string.sucess;
                        }
                    } else return R.string.error_incorrect_password;
                }
                return R.string.server_error_msg;
            } catch (IOException e) {
                e.printStackTrace();
                return R.string.server_error_msg;
            }
        }

        @Override
        protected void onPostExecute(Integer b) {
                new_password.setText("");
                old_password.setText("");
                confirm_password.setText("");
            Toast.makeText(getContext(), b, Toast.LENGTH_SHORT).show();

        }
    }
}
