package de.htwg.seapal.aview.gui.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;
import com.google.inject.Inject;
import com.google.inject.Injector;

import org.apache.commons.lang.StringUtils;

import de.htwg.seapal.R;
import de.htwg.seapal.controller.IAccountController;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.model.IAccount;
import de.htwg.seapal.model.impl.Account;
import de.htwg.seapal.model.impl.SignupAccount;
import de.htwg.seapal.utils.seapal.MomentUtil;
import roboguice.RoboGuice;

/**
 * Created by jakub on 12/16/13.
 */
public class AccountFragment extends Fragment implements PlusClient.ConnectionCallbacks, PlusClient.OnConnectionFailedListener, PlusClient.OnAccessRevokedListener{

    private static final String TAG = "AccountFragment";

    private static final int LOGGEDIN_CHILD = 1;
    private static final int LOGIN_CHILD = 0;

    @Inject
    private IAccountController accountController;

    @Inject
    private SessionManager sessionManager;

    private PlusClient mPlusClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewSwitcher viewSwitcher = (ViewSwitcher) inflater.inflate(R.layout.account_view, container, false);
        if (sessionManager.isLoggedIn()) {
            viewSwitcher.setDisplayedChild(LOGGEDIN_CHILD);
        } else {
            viewSwitcher.setDisplayedChild(LOGIN_CHILD);
        }
        return viewSwitcher;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // cuz this uses Fragment and not Robofragment, cuz Roboguice has no support for roboActionbar
        // so its a mixin with support fragments and android fragments.
        Injector i = RoboGuice.getInjector(getActivity());
        i.injectMembers(this);

        mPlusClient = new PlusClient.Builder(getActivity(),this,this).setActions(MomentUtil.ACTIONS).build();


        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ViewSwitcher viewSwitcher = (ViewSwitcher) view;

        if (sessionManager.isLoggedIn()) {
            TextView usernameText = (TextView) view.findViewById(R.id.account_loggedin_username);
            usernameText.setText(sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
            View logout = view.findViewById(R.id.account_logout);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sessionManager.logoutUser();
                    viewSwitcher.setDisplayedChild(LOGIN_CHILD);
                }
            });
        } else {
            View signup = view.findViewById(R.id.account_create_fresh);
            if (signup != null) {
                signup.setOnClickListener(new AccountCreateFreshOnClickListener());
            }

            SignInButton account_gplus_login = (SignInButton) view.findViewById(R.id.account_gplus_auth);
            account_gplus_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            View account_login = view.findViewById(R.id.account_login);
            if (account_login != null) {
                account_login.setOnClickListener(new AccountLoginOnClickListener(view));

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPlusClient.connect();
    }

    @Override
    public void onStop() {
        mPlusClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onAccessRevoked(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    private class AccountLoginOnClickListener implements View.OnClickListener {
        private final View view;

        public AccountLoginOnClickListener(View view) {
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            EditText email = (EditText) view.findViewById(R.id.account_username);
            EditText password = (EditText) view.findViewById(R.id.account_password);

            if (accountController.accountExists(email.getText().toString())) {
                Account aLogin = new Account();
                aLogin.setEmail(email.getText().toString());
                aLogin.setPassword(password.getText().toString());
                IAccount authenticated = accountController.authenticate(aLogin);
                if (authenticated != null) {
                    sessionManager.createLoginSession(authenticated.getUUID().toString(), authenticated.getEmail() , authenticated.getEmail());
                    Toast.makeText(getActivity(), "successfully logged in", Toast.LENGTH_LONG).show();
                    ViewSwitcher viewSwitcher = (ViewSwitcher) view;
                    viewSwitcher.setDisplayedChild(LOGGEDIN_CHILD);

                } else {
                    Toast.makeText(getActivity(), "Username or Password wrong try again", Toast.LENGTH_LONG).show();

                }


            }

        }
    }

    private class AccountCreateFreshOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.account_sign_up_dialog, null);

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view).
                    setPositiveButton(R.string.signup, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            EditText email = (EditText) view.findViewById(R.id.username);
                            EditText firstName = (EditText) view.findViewById(R.id.first_name);
                            EditText lastName = (EditText) view.findViewById(R.id.last_name);
                            EditText password = (EditText) view.findViewById(R.id.password);
                            EditText password_repeat = (EditText) view.findViewById(R.id.password_repeat);

                            if (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                                if (StringUtils.equals(password.getText().toString(), password_repeat.getText().toString())) {
                                    if (!accountController.accountExists(email.getText().toString())) {
                                        SignupAccount s = new SignupAccount();
                                        s.setEmail(email.getText().toString());
                                        s.setFirstName(firstName.getText().toString());
                                        s.setLastName(lastName.getText().toString());
                                        s.setPassword(password.getText().toString());
                                        accountController.saveAccount(s, true);
                                        Toast.makeText(getActivity(), "Successfully Signed Up...  Now u can login with your Credentials", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Account already exists", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(getActivity(), "password is not the same", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(getActivity(), "Not a valid email", Toast.LENGTH_LONG).show();

                            }
                        }
                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.create().show();

        }
    }

}
