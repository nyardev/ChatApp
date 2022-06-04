package es.usj.androidapps.alu95669.chatapp.Activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.usj.androidapps.alu95669.chatapp.R
import kotlin.system.measureTimeMillis


class LoginScreen : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var userDBRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        mAuth = FirebaseAuth.getInstance()

        supportActionBar?.hide()
        userDBRef = FirebaseDatabase.getInstance().reference
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        //GOTO Register Screen
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val i = Intent(this, RegisterScreen::class.java)
            startActivity(i)
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            login(email, password)
        }

        val btnShow = findViewById<Button>(R.id.btnShow)
        btnShow.setOnClickListener {
            if (btnShow.text.toString().equals("Show")) {
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                btnShow.text = "Hide"
            } else {
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                btnShow.text = "Show"
            }
        }
    }

    private fun login(email: String, password: String){
        FirebaseAuth.getInstance().signOut()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val i = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(i)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


}