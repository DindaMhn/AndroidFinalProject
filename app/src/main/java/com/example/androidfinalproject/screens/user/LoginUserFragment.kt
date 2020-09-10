package com.example.androidfinalproject.screens.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.activity.MainActivity
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.user.account.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_login_provider.*
import kotlinx.android.synthetic.main.fragment_login_user.*
import kotlinx.android.synthetic.main.fragment_profile_user_fagment.*
import kotlinx.android.synthetic.main.fragment_user_register.*
import javax.inject.Inject

const val RC_SIGN_IN = 123
class LoginUserFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var userViewModel: UserViewModel
    var sharedPreferences: SharedPreferences? = null
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity as Activity, gso);

    }

    override fun onStart() {
        super.onStart()
        val acct = GoogleSignIn.getLastSignedInAccount(context)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            val userData = User(
                email = account?.email!!,
                username = account?.email!!,
                password = "12345",
                phone_number = "",
                fullname = account.familyName + " " + account.givenName
            )
            println("this"+account.displayName+account.familyName+account.givenName+account.account+account.photoUrl)
            userViewModel.registerUser(userData)
            check(account?.email!!, account?.email!!,"12345")

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Fail", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (sharedPreferences?.getBoolean("ISLOGGEDIN_USER", false) == true) {
            view?.findNavController()
                ?.navigate(R.id.action_loginUserFragment_to_menuUserActivity)
        } else {
            userViewModel.userResponse.observe(
                viewLifecycleOwner, Observer {
//                    if (it.status == 400.toString() && it.message == "Login User Failed") {
//                        Toast.makeText(
//                            this.context,
//                            "Invalid Login",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    if (it.status == 200.toString()) {
                        Toast.makeText(this.context, "Login Success", Toast.LENGTH_SHORT)
                            .show()
                        userViewModel.userData.observe(
                            viewLifecycleOwner, Observer {
                                if (it != null) {
                                    with(sharedPreferences?.edit()) {
                                        this?.putString("ID_USER",it.id)

                                        this?.putString(
                                            getString(R.string.wallet_id_key),
                                            it.id_wallet
                                        )
                                        this?.putString(
                                            getString(R.string.fullname_user_key),
                                            it.fullname
                                        )
                                        this?.putString(
                                            getString(R.string.borndate_user_key),
                                            it.borndate
                                        )
                                        this?.putString(
                                            getString(R.string.phone_user_key),
                                            it.phone_number
                                        )
                                        this?.putString(
                                            getString(R.string.address_user_key),
                                            it.address
                                        )
                                        this?.putBoolean(
                                            "ISLOGGEDIN_USER",
                                            true
                                        )
                                        this?.commit()
                                    }
                                }
                                view?.findNavController()
                                    ?.navigate(R.id.action_loginUserFragment_to_menuUserActivity)
                            })
                    }
                    else{
                        Toast.makeText(
                            this.context,
                            "Invalid Login",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
        backtoMainUserText.setOnClickListener(this)
        loginUserButton.setOnClickListener(this)
        sign_in_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            backtoMainUserText -> {
                startActivity(Intent(this.context, MainActivity::class.java))
            }
            loginUserButton -> {
                val userLogin =
                    User(
                        email = userNameInputUserLogin.text.toString(),
                        username = userNameInputUserLogin.text.toString(),
                        password = passwordInputUserLogin.text.toString()
                    )
                if (userNameInputUserLogin.text.toString() == "" ||
                    passwordInputUserLogin.text.toString() == ""
                ) {
                    Toast.makeText(this.context, "Must be Filled", Toast.LENGTH_SHORT).show()
                } else {
                    userViewModel.loginUser(userLogin)
                }
            }
            sign_in_button -> {
                signIn()
            }

        }
    }

    private fun signIn(){
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun check(email:String,username:String,password:String){
        userViewModel.userResponse.observe(viewLifecycleOwner,
            Observer {
                if(it.status==400.toString()){
                    val userLogin =
                        User(
                            email = email,
                            username = username,
                            password = password
                        )
                    userViewModel.loginUser(userLogin)
                }else if (it.status == 200.toString()) {
                    val userLogin =
                        User(
                            email = email,
                            username = username,
                            password = password
                        )
                    userViewModel.loginUser(userLogin)
                }
            })
    }
}