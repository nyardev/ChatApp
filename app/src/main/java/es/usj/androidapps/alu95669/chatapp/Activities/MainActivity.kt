package es.usj.androidapps.alu95669.chatapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import es.usj.androidapps.alu95669.chatapp.DataModels.User
import es.usj.androidapps.alu95669.chatapp.DataModels.UserAdapter
import es.usj.androidapps.alu95669.chatapp.R
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var userDBRef: DatabaseReference

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var adapter : UserAdapter

    private val llm = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var ts = FirebaseAuth.getInstance().currentUser?.metadata?.lastSignInTimestamp!!
        title = "Chats"

        mAuth = FirebaseAuth.getInstance()
        userDBRef = FirebaseDatabase.getInstance().reference

        //I refresh the last connected time of the user in firebase
        userDBRef.child("user").child(mAuth.currentUser?.uid!!)
            .child("lastTimeConnected").setValue(ts)

        userList = ArrayList()

        //We want to check the different users in our system
        userDBRef.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                //We check every node
                for(postSnapshot in snapshot.children){
                    //We store the user that we have in the node
                    val newUser = postSnapshot.getValue<User>()
                    //If the user is different to the us (current user) we add to the User list
                    if(mAuth.currentUser?.uid != newUser?.userId){
                        userList.add(newUser!!)
                    }
                }
                val userAdapter = UserAdapter(this@MainActivity, userList)
                userRecyclerView.adapter = userAdapter
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        userRecyclerView = findViewById(R.id.rvUserList)
        userRecyclerView.layoutManager = llm

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logOutOption){
            //Logout from the actual session and return to login Screen
            mAuth.signOut()
            val loginIntent = Intent(this, LoginScreen::class.java)
            finish()
            startActivity(loginIntent)
            return true
        }
            return true
    }

}