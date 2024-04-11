package mx.itson.edu.practica9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {
    private val userRef = FirebaseDatabase.getInstance().getReference("Users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnSave: Button = findViewById<Button>(R.id.save_button)as Button
        btnSave.setOnClickListener { saveMarkFromForm() }

        userRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

                val value = dataSnapshot.getValue()

                if(value is String){
                } else if(value is User){
                    val usuario = value;

                    if(usuario !=null) writeMark(usuario)

                }
            }
        } )
    }

    private fun saveMarkFromForm(){
        var name:EditText=findViewById<EditText>(R.id.et_name) as EditText
        var lastName:EditText=findViewById<EditText>(R.id.et_lastName) as EditText
        var age:EditText=findViewById<EditText>(R.id.et_age) as EditText

        val usuario = User(
            name.text.toString(),
            lastName.text.toString(),
            age.text.toString()
        )
        userRef.push().setValue(usuario)
    }

    private fun writeMark(mark: User){
        var listV:TextView=findViewById<TextView>(R.id.list_textView) as TextView
        val text = listV.text.toString() + mark.toString() + "\n"
        listV.text = text
    }

}