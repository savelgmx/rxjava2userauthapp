package fb.fandroid.adv.rxjava2userauthapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Serializable;

import fb.fandroid.adv.rxjava2userauthapp.albums.AlbumsActivity;
import fb.fandroid.adv.rxjava2userauthapp.model.User;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthFragment extends Fragment {
    private AutoCompleteTextView mEmail;
    private EditText mPassword;
    private Button mEnter;
    private Button mRegister;

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEmailValid() && isPasswordValid()) {
                String credentials = mEmail.getText().toString()+ ":" +mPassword.getText().toString();// concatenate username/email and password with colon for authentication
                final String authHeader= "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);//form authenfication header
                ApiUtils.getApiService()
                        .getUser(authHeader)
                        .subscribe(user->{
                            //здесь данные которые успешно извлечены из user после вызова API
                            Gson gson = new Gson();

                        //    User.UserBean user = response.body().getData();

                            Intent startProfileIntent = new Intent(getActivity(), ProfileActivity.class);
                            startProfileIntent.putExtra(ProfileActivity.USER_KEY, (Serializable) user);
                            startActivity(startProfileIntent);
                            getActivity().finish();

                        },Throwable::printStackTrace);

/*
                public interface APIClient {

                    @GET("pincode")
                    Single<City> getCityFromPincode(@Query("pincode") String  pincode);
                }
                How you implement is

                apiClient.getCityFromPincode("123456")
                        .subscribe(city -> {
                            // handle data fetched successfully and API call completed
                        },Throwable::printStackTrace);
*/

/*                        .enqueue(

                        new retrofit2.Callback<User>(){
                            //используем Handler, чтобы показывать ошибки в Main потоке, т.к. наши коллбеки возвращаются в рабочем потоке
                            Handler mainHandler = new Handler(getActivity().getMainLooper());
                            @Override
                            public void onFailure(retrofit2.Call<User> call, Throwable t) {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showMessage(R.string.request_error);
                                    }
                                });
                            }

                            @Override
                            public void onResponse(retrofit2.Call<User> call, final retrofit2.Response<User> response) {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!response.isSuccessful()) {
                                      //      int errorMessage=ErrorUtils.parseError(response);
                                            Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT).show();
                                        } else {
                                            Gson gson = new Gson();

                                            User.UserBean user = response.body().getData();

                                            Intent startProfileIntent = new Intent(getActivity(), ProfileActivity.class);
                                            startProfileIntent.putExtra(ProfileActivity.USER_KEY, (Serializable) user);
                                            startActivity(startProfileIntent);
                                            getActivity().finish();
                                        }
                                    }
                                });
                            }//

                        }); //ApiUtils.getApiService().getUser(user).enqueue*/

            } else {

                showMessage(R.string.input_error);
            }
        }
    };

    private View.OnClickListener mOnRegisterClickListener = view -> getFragmentManager()
            .beginTransaction()
            .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
            .addToBackStack(RegistrationFragment.class.getName())
            .commit();

    private View.OnFocusChangeListener mOnEmailFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                mEmail.showDropDown();
            }
        }
    };

    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mEmail.getText())
                && Patterns.EMAIL_ADDRESS.matcher(mEmail.getText()).matches();
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_auth, container, false);

        mEmail = v.findViewById(R.id.etEmail);
        mPassword = v.findViewById(R.id.etPassword);
        mEnter = v.findViewById(R.id.buttonEnter);
        mRegister = v.findViewById(R.id.buttonRegister);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);
        mEmail.setOnFocusChangeListener(mOnEmailFocusChangeListener);

        return v;
    }
}
