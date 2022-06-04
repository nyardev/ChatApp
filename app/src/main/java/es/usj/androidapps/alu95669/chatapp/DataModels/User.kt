package es.usj.androidapps.alu95669.chatapp.DataModels

class User {
    var name: String? = null
    var email: String? = null
    var userId: String? = null

    //Firebase needs an empty constructor
    constructor(){}

    constructor(name: String?, email: String?, userId: String?){
        this.name = name
        this.email = email
        this.userId = userId
    }
}