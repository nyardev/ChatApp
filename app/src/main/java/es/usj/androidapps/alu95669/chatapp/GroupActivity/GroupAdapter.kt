package es.usj.androidapps.alu95669.chatapp.GroupActivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.usj.androidapps.alu95669.chatapp.R
import kotlin.collections.ArrayList

class GroupAdapter(val context: Context, list: ArrayList<Group>):
    RecyclerView.Adapter<GroupAdapter.GroupHolder>() {

    private var groupList: ArrayList<Group> = arrayListOf()

    init {
        groupList.addAll(list)
    }

    class GroupHolder (itemView: View) :
        RecyclerView.ViewHolder(itemView) {
            fun render(group: Group) {
                val name: TextView = itemView.findViewById(R.id.tvGroupNameRV)
                name.text = group.name
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.group_card, parent, false)
        return GroupHolder(itemView)
    }

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.render(groupList[position])

        val currentUser = groupList[position]

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ViewGroup::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.groupId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }
}