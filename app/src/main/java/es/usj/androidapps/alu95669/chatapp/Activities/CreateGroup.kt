package es.usj.androidapps.alu95669.chatapp.Activities


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import es.usj.androidapps.alu95669.chatapp.DataModels.User
import es.usj.androidapps.alu95669.chatapp.DataModels.UserListAdapter
import es.usj.androidapps.alu95669.chatapp.R

import kotlin.collections.ArrayList

class CreateGroup : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userDBRef: DatabaseReference

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserListAdapter
    private val llm2 = LinearLayoutManager(this)
    interface Callback {
        fun onUpdate(list: ArrayList<User>)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_chat_room)
        var ts = FirebaseAuth.getInstance().currentUser?.metadata?.lastSignInTimestamp!!
        title = "Create a Group"

        mAuth = FirebaseAuth.getInstance()
        userDBRef = FirebaseDatabase.getInstance().reference

        //I refresh the last connected time of the user in firebase
        userDBRef.child("user").child(mAuth.currentUser?.uid!!)
            .child("lastTimeConnected").setValue(ts)

        userList = ArrayList()

        //We want to check the different users in our system
        userDBRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                //We check every node
                for (postSnapshot in snapshot.children) {
                    //We store the user that we have in the node
                    val newUser = postSnapshot.getValue<User>()
                    //If the user is different to the us (current user) we add to the User list
                    if (mAuth.currentUser?.uid != newUser?.userId) {
                        userList.add(newUser!!)
                    }
                }
                val userListAdapter = UserListAdapter(this@CreateGroup, userList)
                userRecyclerView.adapter = userListAdapter
            }

            override fun onCancelled(error: DatabaseError) {
            }



        })
        val btnCreate = findViewById<Button>(R.id.btnCreate)
        val etGroupName = findViewById<EditText>(R.id.etGroupName)
        btnCreate.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            addGroupToDB(etGroupName.text.toString(),etGroupName.text.toString())
            finish()
            startActivity(intent)
        }




        userRecyclerView = findViewById(R.id.rvUserList)
        userRecyclerView.layoutManager = llm2



    }

    private fun addGroupToDB(groupName: String, groupId: String){
        //Linking to DB
        userDBRef = FirebaseDatabase.getInstance().reference
        //Creating nodes for each group. USE THIS LOGIC TO CREATE OTHER THINGS IN THE DATABASE, TEAM
        userDBRef.child("groups").child(groupId).setValue(groupName)
    }


}


