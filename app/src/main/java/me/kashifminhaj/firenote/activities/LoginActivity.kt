package me.kashifminhaj.firenote.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import me.kashifminhaj.firenote.R


class LoginActivity : AppCompatActivity() {
    var isLogin = true
    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signupLink.setOnClickListener {
            if (isLogin)
                presentSignup()
            else
                presentLogin()
        }
        loginButton.setOnClickListener { onSubmit() }

        presentLogin()
        mAuth = FirebaseAuth.getInstance()
        mAuth?.addAuthStateListener {
            if(mAuth!!.currentUser != null) {
                showStatus("Already signed in.")
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }



    fun presentSignup() {
        isLogin = false
        loginFullName.visibility = View.VISIBLE
        signupLink.text = "Already have an Account? Click to sign in."
        loginTitle.text = "Signup for FireNote"
        loginButton.text = "Signup"
    }

    fun presentLogin() {
        isLogin = true
        loginFullName.visibility = View.GONE
        signupLink.text = "Don't have an Account? Click here to create one."
        loginTitle.text = "Login"
        loginButton.text = "Login"
    }

    fun fieldsAreValid(): Boolean {
        return true
    }

    fun login() {
        // login to firebase and maybe move on to next activity
        mAuth!!.signInWithEmailAndPassword(loginEmail.text.toString(), loginPass.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showStatus("Login successfull")
                    } else {
                        showStatus("Error logging in")
                    }
                }
    }
    fun signup() {
        // create new account and log in to it
        mAuth!!.createUserWithEmailAndPassword(loginEmail.text.toString(), loginPass.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        showStatus("Signup successfull")
                    } else {
                        showStatus("Could not sign up: " + it.exception?.message)
                    }
                }
    }

    fun onSubmit() {
        if(!fieldsAreValid()) return
        if(isLogin)
            login()
        else
            signup()
    }

    fun showStatus(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
