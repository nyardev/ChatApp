package es.usj.androidapps.alu95669.chatapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import es.usj.androidapps.alu95669.chatapp.DataModels.MessageAdapter
import es.usj.androidapps.alu95669.chatapp.R
import es.usj.androidapps.alu95669.chatapp.DataModels.Message
import es.usj.androidapps.alu95669.chatapp.DataModels.User
import es.usj.androidapps.alu95669.chatapp.DataModels.UserAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Chat: AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: FloatingActionButton

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>

    private lateinit var dbRef: DatabaseReference

    private val llm = LinearLayoutManager(this)

    var senderRoom: String? = null
    var receiverRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)


        dbRef = FirebaseDatabase.getInstance().reference

        val userName = intent.getStringExtra("name")
        val receiverID = intent.getStringExtra("uid")
        val senderID = FirebaseAuth.getInstance().currentUser?.uid


        senderRoom = receiverID + senderID
        receiverRoom = senderID + receiverID

        supportActionBar?.title = userName

        messageRecyclerView = findViewById(R.id.rvMessagesCR)
        sendButton = findViewById(R.id.btnSend)
        messageBox = findViewById(R.id.etMessageCR)

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)


        messageRecyclerView.layoutManager = llm
        messageRecyclerView.adapter = messageAdapter

        dbRef.child("messages").child(senderRoom!!)
            .child("message").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children) {
                        //We store the message that we have in the node
                        val newMessage = postSnapshot.getValue<Message>()
                        messageList.add(newMessage!!)
                    }
                    val messageAdapter = MessageAdapter(this@Chat, messageList)
                    messageRecyclerView.adapter = messageAdapter
                    (messageRecyclerView.layoutManager as LinearLayoutManager).scrollToPosition(
                        messageList.size - 1
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        sendButton.setOnClickListener {
            if (messageBox.text.isNotBlank()) {
                val message = messageBox.text.toString()

                //We define the format we want to our Date
                val sdf = SimpleDateFormat("HH:mm")
                val date = Date()

                val messageObject = Message(message, senderID, sdf.format(date))
                dbRef.child("messages").child(senderRoom!!).child("message").push()
                    .setValue(messageObject).addOnSuccessListener {
                        dbRef.child("messages").child(receiverRoom!!).child("message").push()
                            .setValue(messageObject)
                    }
                messageBox.setText("")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val llSearch = findViewById<LinearLayout>(R.id.llSearch)
        val searchET = findViewById<EditText>(R.id.etSearchMessage)
        val btnSearchET = findViewById<FloatingActionButton>(R.id.btnSearchET)

        appearSearchBox(llSearch, searchET, btnSearchET)

        btnSearchET.setOnClickListener {
            searchMessage(searchET, llSearch, btnSearchET)
        }
        return true
    }

    private fun appearSearchBox(
        llSearch: LinearLayout,
        searchET: EditText,
        btnSearchET: FloatingActionButton
    ) {
        llSearch.visibility = View.VISIBLE
        llSearch.isClickable = true

        searchET.visibility = View.VISIBLE
        searchET.isClickable = true

        btnSearchET.visibility = View.VISIBLE
        btnSearchET.isClickable = true
    }

    private fun searchMessage(
        searchET: EditText,
        llSearch: LinearLayout,
        btnSearchET: FloatingActionButton
    ) {
        var messageText = searchET.text.toString()
        var position = 0
        var found = false

        for (message in messageList) {
            if (!message.message?.contains(messageText)!!) {
                position += 1
            } else {
                found = true
                break
            }
        }
        //If it did not find any match
        if (!found) {
            Toast.makeText(this, "Message not found!", Toast.LENGTH_SHORT).show()
        } else {
            (messageRecyclerView.layoutManager as LinearLayoutManager).scrollToPosition(position - 1)
        }
        disappearSearchBox(searchET, llSearch, btnSearchET)
    }

    private fun disappearSearchBox(
        searchET: EditText,
        llSearch: LinearLayout,
        btnSearchET: FloatingActionButton
    ) {
        searchET.visibility = View.INVISIBLE
        searchET.isClickable = false
        llSearch.visibility = View.INVISIBLE
        llSearch.isClickable = false
        btnSearchET.visibility = View.INVISIBLE
        btnSearchET.isClickable = false
    }

}


