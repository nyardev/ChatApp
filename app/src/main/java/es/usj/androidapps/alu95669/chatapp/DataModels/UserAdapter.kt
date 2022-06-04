package es.usj.androidapps.alu95669.chatapp.DataModels

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import es.usj.androidapps.alu95669.chatapp.Activities.Chat
import es.usj.androidapps.alu95669.chatapp.R
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter(val context: Context, list: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserHolder>() {

    var userList: ArrayList<User> = arrayListOf()


    //When we initiates the adapter, we fill our internal Array with the values of the list
    init {
        userList.addAll(list)
    }


    class UserHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun render(user: User) {
            val name: TextView = itemView.findViewById(R.id.tvUserNameRV)
            name.text = user.name
            val lastConnection : TextView = itemView.findViewById(R.id.tvLastConnection)
            var date = Date(user.lastTimeConnected!!)
            lastConnection.text = date.toLocaleString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.user_card, parent, false)
        return UserHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.render(userList[position])

        val currentUser = userList[position]

        holder.itemView.setOnClickListener{
            val intent = Intent(context, Chat::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.userId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}

