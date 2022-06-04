package es.usj.androidapps.alu95669.chatapp.DataModels

class Message{
        var message : String? = null
        var senderID : String? = null
        var sentTime : String? = null
    //Default Constructor
    constructor(){}
    //Parameter Constructor
    constructor(message: String?, senderID : String?, sentTime: String?){
        this.message = message
        this.senderID = senderID
        this.sentTime = sentTime
    }
    }
