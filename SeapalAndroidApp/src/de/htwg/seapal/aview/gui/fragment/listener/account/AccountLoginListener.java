package de.htwg.seapal.aview.gui.fragment.listener.account;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.inject.Inject;

import de.htwg.seapal.Manager.SessionManager;
import de.htwg.seapal.R;
import de.htwg.seapal.controller.impl.AccountController;
import de.htwg.seapal.model.IAccount;
import de.htwg.seapal.model.impl.Account;

/**
 * Created by jakub on 2/19/14.
 */
public class AccountLoginListener implements View.OnClickListener {

    @Inject
    private SessionManager sessionManager;

    @Inject
    private AccountController accountController;

    @Inject
    private Context context;

    @Override
    public void onClick(View view) {
        EditText email = (EditText) view.findViewById(R.id.account_username);
        EditText password = (EditText) view.findViewById(R.id.account_password);

        if (accountController.accountExists(email.getText().toString())) {
            Account aLogin = new Account();
            aLogin.setEmail(email.getText().toString());
            aLogin.setPassword(password.getText().toString());
            IAccount authenticated = accountController.authenticate(aLogin);
            if (authenticated != null) {
                sessionManager.createLoginSession(authenticated.getEmail(), authenticated.getEmail());
                Toast.makeText(context, "successfully logged in", Toast.LENGTH_LONG).show();

            }


        }
    }
}
