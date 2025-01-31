package es.usj.androidapps.alu95669.chatapp.DataModels


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.usj.androidapps.alu95669.chatapp.R
import java.util.*
import kotlin.collections.ArrayList

class UserListAdapter(val context: Context, list: ArrayList<User>): RecyclerView.Adapter<UserListAdapter.UserHolder>(){

    private var userList: ArrayList<User> = arrayListOf()

    private val selectedUserList: ArrayList<User> = arrayListOf()


    //When we initiates the adapter, we fill our internal Array with the values of the list
    init {
        userList.addAll(list)
    }


    class UserHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val isSelected: CheckBox = itemView.findViewById(R.id.cbIsSelected)
        fun render(user: User) {
            val name: TextView = itemView.findViewById(R.id.tvUserNameRVList)
            name.text = user.name
            val lastConnection : TextView = itemView.findViewById(R.id.tvLastConnectionList)
            val date = Date(user.lastTimeConnected!!)
            lastConnection.text = date.toLocaleString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.user_card_list, parent, false)
        return UserHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.render(userList[position])

        val currentUser = userList[position]

        holder.itemView.setOnClickListener{
            if(holder.isSelected.isChecked) {
                holder.isSelected.isChecked = false
            }else if(!holder.isSelected.isChecked){
                holder.isSelected.isChecked = true}
        }
        if(holder.isSelected.isChecked){
            selectedUserList.add(currentUser)
        }
        if(!holder.isSelected.isChecked) {
            if (selectedUserList.contains(currentUser)) {
                selectedUserList.remove(currentUser)
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun getSelectedUsers(): ArrayList<User>{
        return selectedUserList
    }
}

