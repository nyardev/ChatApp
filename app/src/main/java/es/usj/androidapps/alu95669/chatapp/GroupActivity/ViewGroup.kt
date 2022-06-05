package es.usj.androidapps.alu95669.chatapp.GroupActivity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import es.usj.androidapps.alu95669.chatapp.Activities.LoginScreen
import es.usj.androidapps.alu95669.chatapp.DataModels.User
import es.usj.androidapps.alu95669.chatapp.R

class ViewGroup: AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var groupDBRef: DatabaseReference


    private lateinit var groupList : ArrayList<Group>
    private lateinit var groupRecyclerView: RecyclerView

    private val llm3 = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grouplist_view)
        title = "Groups"

        mAuth = FirebaseAuth.getInstance()
        groupDBRef = FirebaseDatabase.getInstance().reference

        groupList = ArrayList()
        val arrayUsers : ArrayList<User> = arrayListOf()
        val newGroup = Group("Name", "ID", arrayUsers)
        groupList.add(newGroup)


        //We want to check the different users in our system
        groupDBRef.child("groups").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                groupList.clear()
                //We check every node
                for(postSnapshot in snapshot.children){
                    //We store the group that we have in the node
                    val newGroup = postSnapshot.getValue<Group>()
                    //we add the group to the group list
                    groupList.add(newGroup!!)
                }
                val groupAdapter = GroupAdapter(this@ViewGroup, groupList)
                groupRecyclerView.adapter = groupAdapter
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        groupRecyclerView = findViewById(R.id.rvGroupList)
        groupRecyclerView.layoutManager = llm3

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