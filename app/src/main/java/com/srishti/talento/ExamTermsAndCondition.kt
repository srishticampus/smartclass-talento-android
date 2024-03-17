package com.srishti.talento

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.srishti.talento.databinding.ActivityExamTermsAndConditionBinding

class ExamTermsAndCondition : AppCompatActivity() {

    lateinit var binding: ActivityExamTermsAndConditionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamTermsAndConditionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val queNo: String = intent.getStringExtra("que_no").toString()
        val duration: String = intent.getStringExtra("duration").toString()
        val questionSetId: String = intent.getStringExtra("question_set_id").toString()
        val techId: String = intent.getStringExtra("techId").toString()

        binding.acceptBtn.setOnClickListener {
            if (binding.checkbox.isChecked) {
                intent = Intent(applicationContext, ExamActivity::class.java)
                intent.putExtra("que_no", queNo)
                intent.putExtra("duration", duration)
                intent.putExtra("question_set_id", questionSetId)
                intent.putExtra("techId", techId)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext,"Please Accept The Terms And Conditions",Toast.LENGTH_LONG).show()
            }
        }


    }
}