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
import es.usj.androidapps.alu95669.chatapp.DataModels.User
import es.usj.androidapps.alu95669.chatapp.R
import es.usj.androidapps.alu95669.chatapp.databinding.ActivityRegisterScreenBinding


class RegisterScreen : AppCompatActivity() {

    //Variables needed to connect to the DB
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)
        setTitle("REGISTER Screen")

        mAuth = FirebaseAuth.getInstance();

        //We get the components of our layout
        val etEmail = findViewById<EditText>(R.id.etEmailRegister)
        val etPassword = findViewById<EditText>(R.id.etPasswordRegister)
        val etName = findViewById<EditText>(R.id.etNameRegister)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener{
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            register(name, email, password)
        }

        val btnShow = findViewById<Button>(R.id.btnShowRegister)
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

    private fun register(name: String, email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    //Adding user to DB
                    addUserToDB(name, email, mAuth.currentUser?.uid!!)
                    val i = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(i)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed. Try longer password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // ...
            }
    }

    private fun addUserToDB(name: String, email: String, userId: String){
        //Linking to DB
        dbRef = FirebaseDatabase.getInstance().reference
        //Creating nodes for each user. USE THIS LOGIC TO CREATE OTHER THINGS IN THE DATABASE, TEAM
        dbRef.child("user").child(userId).setValue(User(name, email, userId))
    }
}