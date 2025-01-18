package com.example.cleanhomes111.ui.theme.models

class User {

    var name:String = ""
    private var email:String = ""
    private var password:String = ""
    private var id:String = ""

    constructor(name: String, email: String, password: String, id: String) {
        this.name = name
        this.email = email
        this.password = password
        this.id = id
    }

    constructor()
}