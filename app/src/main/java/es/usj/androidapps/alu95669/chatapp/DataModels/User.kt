package es.usj.androidapps.alu95669.chatapp.DataModels

class User {
    var name: String? = null
    var email: String? = null
    var userId: String? = null
    var lastTimeConnected : Long? = null

    //Firebase needs an empty constructor
    constructor(){}

    constructor(name: String?, email: String?, userId: String?, lastTimeConnected: Long?){
        this.name = name
        this.email = email
        this.userId = userId
        this.lastTimeConnected = lastTimeConnected
    }
}