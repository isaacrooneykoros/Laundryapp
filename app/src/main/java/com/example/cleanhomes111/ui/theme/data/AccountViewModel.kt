@file:Suppress("DEPRECATION")

package com.example.cleanhomes111.ui.theme.data

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import com.example.cleanhomes111.ui.theme.models.Account
import com.example.cleanhomes111.ui.theme.navigation.ROUT_LOGIN
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class AccountViewModel(private var navController: NavController, var context: Context) {
    private var authViewModel:AuthViewModel = AuthViewModel(navController, context)
    private var progress: ProgressDialog
    init {
        if (!authViewModel.isLoggedIn()){
            navController.navigate(ROUT_LOGIN)
        }
        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
    }

    fun addAccount(name:String, email:String, title:String, filePath: Uri){
        val accountId = System.currentTimeMillis().toString()
        val storageRef = FirebaseStorage.getInstance().getReference()
            .child("Accounts/$accountId")
        progress.show()
        storageRef.putFile(filePath).addOnCompleteListener{ it ->
            progress.dismiss()
            if (it.isSuccessful){
                // Save data to db
                storageRef.downloadUrl.addOnSuccessListener {
                    val imageUrl = it.toString()
                    val account = Account(name,title,email,imageUrl,accountId)
                    val databaseRef = FirebaseDatabase.getInstance().getReference()
                        .child("Accounts/$accountId")
                    databaseRef.setValue(account).addOnCompleteListener {
                        if (it.isSuccessful){
                            navController.navigate(ROUT_LOGIN)
                            Toast.makeText(this.context, "Successfully created an account", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                Toast.makeText(this.context, "Upload error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun ViewAccount(account: MutableState<Account>, accounts: SnapshotStateList<Account>):SnapshotStateList<Account>{
        val ref = FirebaseDatabase.getInstance().getReference()
            .child("Accounts")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                accounts.clear()
                for (snap in snapshot.children){
                    var retrievedAccount = snap.getValue(Account::class.java)
                    account.value = retrievedAccount!!
                    accounts.add(retrievedAccount)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "DB locked", Toast.LENGTH_SHORT).show()
            }
        })
        return accounts
    }

    fun updateAccount(accountId:String){
        val ref = FirebaseDatabase.getInstance().getReference()
            .child("Account/$accountId")
        ref.removeValue()
        navController.navigate(ROUT_LOGIN)
    }


    fun deleteAccount(accountId:String){
        val ref = FirebaseDatabase.getInstance().getReference()
            .child("Accounts/$accountId")
        ref.removeValue()
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }
}

