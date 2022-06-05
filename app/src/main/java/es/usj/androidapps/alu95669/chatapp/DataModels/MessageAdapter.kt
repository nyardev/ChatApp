package es.usj.androidapps.alu95669.chatapp.DataModels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import es.usj.androidapps.alu95669.chatapp.R


class MessageAdapter(val context: Context, list: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messageList: ArrayList<Message> = arrayListOf()
    private val itemSent = 2
    private val itemReceived = 1


    //When we initiates the adapter, we fill our internal Array with the values of the list
    init {
        messageList.addAll(list)
    }


    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val sentMessage: TextView = itemView.findViewById(R.id.tvSentMessage)
        val sentTime: TextView = itemView.findViewById(R.id.tvSentTime)

    }

    class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val receivedMessage: TextView = itemView.findViewById(R.id.tvReceivedMessage)
        val sentTime: TextView = itemView.findViewById(R.id.tvSentTimeR)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==1){
            val view = LayoutInflater.from(context).inflate(R.layout.received_message, parent, false)
            ReceivedViewHolder(view)
        }else{
            val view = LayoutInflater.from(context).inflate(R.layout.sent_message, parent, false)
            SentViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass==SentViewHolder::class.java){
            //Sent view holder
            val viewHolder = holder as SentViewHolder
            viewHolder.sentMessage.text = currentMessage.message
            viewHolder.sentTime.text = currentMessage.sentTime

        }else{
            val viewHolder = holder as ReceivedViewHolder
            viewHolder.receivedMessage.text = currentMessage.message
            viewHolder.sentTime.text = currentMessage.sentTime
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int{
        val currentMessage = messageList[position]

        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderID)){
            itemSent
        }else itemReceived
    }

    fun getItemByPosition(position: Int): Message{
        return messageList[position]
    }
}