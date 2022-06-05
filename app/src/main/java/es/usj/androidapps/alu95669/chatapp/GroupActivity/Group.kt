package es.usj.androidapps.alu95669.chatapp.GroupActivity

import es.usj.androidapps.alu95669.chatapp.DataModels.User
import kotlin.collections.ArrayList

class Group {
    var name: String? = null
    var groupId: String? = null
    private var members: ArrayList<User>?=null

    //Firebase needs an empty constructor
    constructor(){}

    constructor(name: String?, groupId: String?, members: ArrayList<User>?){
        this.name = name
        this.groupId = groupId
        this.members = members
    }
}