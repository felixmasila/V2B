//package com.felixmasila.v2b.fragment;
//
//
//import android.app.Fragment;
//import android.app.FragmentTransaction;
//import android.os.Bundle;
//import android.support.design.widget.Snackbar;
//import android.support.v7.widget.AppCompatButton;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import com.felixmasila.v2b.R;
//import com.felixmasila.v2b.activity.Constants;
//import com.felixmasila.v2b.activity.RequestInterface;
//import com.felixmasila.v2b.models.ServerRequest;
//import com.felixmasila.v2b.models.ServerResponse;
//import com.felixmasila.v2b.models.User;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
////import static com.felixmasila.v2b.R.id.btn_register_donor;
////import static com.felixmasila.v2b.R.id.et_email;
////import static com.felixmasila.v2b.R.id.et_name;
////import static com.felixmasila.v2b.R.id.et_password;
////import static com.felixmasila.v2b.R.id.first_name;
//
//
///**
// * A simple {@link Fragment} subclass.
// * public class RegisterFragment extends Fragment  implements View.OnClickListener
// */
//public class IwanttodonateFragment extends Fragment implements View.OnClickListener {
//    private RadioGroup radioSexGroup;
//    private Spinner spinner1;
//    private AppCompatButton btn_register_donor;
//    private EditText first_name,second_name,reg_last_name,id_number,reg_email,sextype;
//    private TextView tv_login;
//    private ProgressBar progress;
//
//
//    public IwanttodonateFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        final  View rootView = inflater.inflate(R.layout.fragment_iwanttodonate, container, false);
//        radioSexGroup=(RadioGroup) rootView.findViewById(R.id.sextype);
//
//        return rootView;
//
//    }
//    private void initViews(View view){
//
//        btn_register_donor = (AppCompatButton)view.findViewById(R.id.btn_register_donor);
//        tv_login = (TextView)view.findViewById(R.id.tv_login);
//        first_name = (EditText)view.findViewById(R.id.first_name);
//        second_name = (EditText)view.findViewById(R.id.second_name);
//        reg_last_name = (EditText)view.findViewById(R.id.reg_last_name);
//
//        progress = (ProgressBar)view.findViewById(R.id.progress);
//
//        btn_register_donor.setOnClickListener(this);
//        tv_login.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()){
//            case R.id.tv_login:
//                goToLogin();
//                break;
//
//            case R.id.btn_register_donor:
//
//                String name = first_name.getText().toString();
//                String email = second_name.getText().toString();
//                String password = reg_last_name.getText().toString();
//
//                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
//
//                    progress.setVisibility(View.VISIBLE);
//                    registerProcess(name,email,password);
//
//                } else {
//
//                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
//                }
//                break;
//
//        }
//
//    }
//
//    private void registerProcess(String name, String email,String password){
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//
//        com.felixmasila.v2b.activity.RequestInterface requestInterface = retrofit.create(RequestInterface.class);
//
//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//        user.setPassword(password);
//        ServerRequest request = new ServerRequest();
//        request.setOperation(Constants.REGISTER_OPERATION);
//        request.setUser(user);
//        Call<ServerResponse> response = requestInterface.operation(request);
//
//        response.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
//
//                ServerResponse resp = response.body();
//                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
//                progress.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//                progress.setVisibility(View.INVISIBLE);
//                Log.d(Constants.TAG,"failed");
//                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
//
//
//            }
//        });
//    }
//
//    private void goToLogin(){
//
//        Fragment login = new LoginFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_frame,login);
//        ft.commit();
//    }
//
//}
//
////package com.felixmasila.v2b.fragment;
////
////
////import android.os.Bundle;
////import android.support.v4.app.Fragment;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////
////import com.felixmasila.v2b.R;
////
/////**
//// * A simple {@link Fragment} subclass.
//// */
////public class IwanttodonateFragment extends Fragment {
////
////
////    public IwanttodonateFragment() {
////        // Required empty public constructor
////    }
////
////
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                             Bundle savedInstanceState) {
////        // Inflate the layout for this fragment
////        return inflater.inflate(R.layout.fragment_mydonationhistory, container, false);
////    }
////
////}
//

package com.felixmasila.v2b.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.felixmasila.v2b.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IwanttodonateFragment extends Fragment {


    public IwanttodonateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

}
