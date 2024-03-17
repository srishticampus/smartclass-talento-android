package com.srishti.talento

import android.Manifest
import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.srishti.talento.Retro.Api
import com.srishti.talento.Retro.ApiClient
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot
import com.srishti.talento.databinding.ActivityGatePassBinding
import id.zelory.compressor.Compressor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GatePassActivity : AppCompatActivity() {

    lateinit var binding: ActivityGatePassBinding
    private val myCalendar: Calendar = Calendar.getInstance()

    lateinit var batch_details: Array<String> // Declare batch_details as an array of strings
    lateinit var batchId: Array<String> // Declare batchId as an array of strings
    lateinit var selectedBatchId: String

    lateinit var event_details: Array<String> // Declare batch_details as an array of strings
    lateinit var eventId: Array<String> // Declare batchId as an array of strings
    lateinit var selectedEventId: String

    private val STORAGE_PERMISSION_CODE = 101
    private val RESULT_LOAD_PRO_IMAGE = 106

    private var proImageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGatePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getBooleanExtra("event",true)){
            binding.endDateTil.visibility = View.GONE
            binding.startDateTil.visibility = View.GONE
            binding.eventTil.visibility = View.VISIBLE
        }else{
            binding.endDateTil.visibility = View.VISIBLE
            binding.startDateTil.visibility = View.VISIBLE
            binding.eventTil.visibility = View.GONE
        }

        try {
            selectTechApi()
            selectEventApi()
        } catch (e: Exception) {
            Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show()
        }

        startDateUpdateLabel()
        endDateUpdateLabel()


        val startDate =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, month: Int, day: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                startDateUpdateLabel()
            }
        binding.startDateEt.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@GatePassActivity,
                startDate,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            // Set the minimum date to today to restrict selecting past dates
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()
        }

        val endDate =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, month: Int, day: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                endDateUpdateLabel()
            }
        binding.endDateEt.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@GatePassActivity,
                endDate,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            // Set the minimum date to today to restrict selecting past dates
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()
        }

        binding.imgNameEt.setOnClickListener {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.READ_MEDIA_IMAGES
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    checkPermission(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        STORAGE_PERMISSION_CODE
                    )
                } else {
                    // calling intent on below line.
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    // starting activity on below line.
                    startActivityForResult(intent, 1)
                }
            } else {
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    checkPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        STORAGE_PERMISSION_CODE
                    )
                } else {
                    // calling intent on below line.
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    // starting activity on below line.
                    startActivityForResult(intent, 1)
                }

            }


        }

        // technology selection et
        binding.passTechnologyEt.isCursorVisible = false

        binding.passTechnologyEt.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                try {
                    binding.passTechnologyEt.showDropDown()
                    val course = adapterView.getItemAtPosition(i) as String
                    binding.passTechnologyEt.setText(adapterView.getItemAtPosition(i).toString())
                    selectedBatchId = batchId[i]
                    // Toast.makeText(this@TalentoTwoRegistrationActivity, selectedBatchId, Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    // Handle the exception if needed
                }
            }

        binding.passTechnologyEt.setOnClickListener { view ->
            try {
                if (binding.passTechnologyEt.text.toString().isEmpty()) {
                    binding.passTechnologyEt.showDropDown()
                } else {
                    binding.passTechnologyEt.text = null
                    // Add a delay of 200 milliseconds
                    val delayMillis = 200

                    // Perform the desired action after the delay
                    val handler = Handler()
                    handler.postDelayed({
                        // Code to be executed after the delay
                        // Insert your logic here
                        binding.passTechnologyEt.showDropDown()
                    }, delayMillis.toLong())
                }
            } catch (e: Exception) {
                // Handle the exception if needed
            }
        }

        // event selection et
        binding.passEventEt.isCursorVisible = false

        binding.passEventEt.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                try {
                    binding.passEventEt.showDropDown()
                    val course = adapterView.getItemAtPosition(i) as String
                    binding.passEventEt.setText(adapterView.getItemAtPosition(i).toString())
                    selectedEventId = eventId[i]
                    // Toast.makeText(this@TalentoTwoRegistrationActivity, selectedBatchId, Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    // Handle the exception if needed
                }
            }


        binding.passEventEt.setOnClickListener { view ->
            try {
                if (binding.passEventEt.text.toString().isEmpty()) {
                    binding.passEventEt.showDropDown()
                } else {
                    binding.passEventEt.text = null
                    // Add a delay of 200 milliseconds
                    val delayMillis = 200

                    // Perform the desired action after the delay
                    val handler = Handler()
                    handler.postDelayed({
                        // Code to be executed after the delay
                        // Insert your logic here
                        binding.passEventEt.showDropDown()
                    }, delayMillis.toLong())
                }
            } catch (e: Exception) {
                // Handle the exception if needed
            }
        }

        binding.passSubmitBtn.setOnClickListener {


            if (intent.getBooleanExtra("event",true)){
                if (binding.passFirstNameEt.text.toString().isEmpty()) {
                    binding.passFirstNameEt.setError("Required")
                } else if (binding.passTechnologyEt.text.toString().isEmpty()) {
                    binding.passTechnologyEt.setError("Required")
                } else if (binding.passEventEt.text.toString().isEmpty()) {
                    binding.passEventEt.setError("Required")
                } else if (binding.imgNameEt.text.toString().isEmpty()) {
                    binding.imgNameEt.setError("Required")
                } else {
                    binding.passSubmitBtn.visibility = View.INVISIBLE
                    GlobalScope.launch {
                        eventPassSumbitApi()
                    }

                }
            }else{
                if (binding.passFirstNameEt.text.toString().isEmpty()) {
                    binding.passFirstNameEt.setError("Required")
                } else if (binding.passTechnologyEt.text.toString().isEmpty()) {
                    binding.passTechnologyEt.setError("Required")
                } else if (binding.startDateEt.text.toString().isEmpty()) {
                    binding.startDateEt.setError("Required")
                } else if (binding.endDateEt.text.toString().isEmpty()) {
                    binding.endDateEt.setError("Required")
                } else if (binding.imgNameEt.text.toString().isEmpty()) {
                    binding.imgNameEt.setError("Required")
                } else {
                    binding.passSubmitBtn.visibility = View.INVISIBLE
                    GlobalScope.launch {
                        regularPassSumbitApi()
                    }

                }
            }




        }


    }

    private fun startDateUpdateLabel() {
        val myFormat = "dd/MM/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.startDateEt.setText(dateFormat.format(myCalendar.time))
    }

    private fun endDateUpdateLabel() {
        val myFormat = "dd/MM/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.endDateEt.setText(dateFormat.format(myCalendar.time))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === RESULT_OK) {
            // compare the resultCode with the
            // constant
            if (requestCode === 1) {
                // Get the url of the image from data
                val selectedImageUri: Uri = data?.data!!
                if (null != selectedImageUri) {
                    // update the image view in the layout
                    //imageIV.setImageURI(selectedImageUri)

                    val imagePath = getImagePathFromUri(selectedImageUri)
                    proImageFile = File(imagePath)
                    binding.imgNameEt.setText(imagePath)

                    // Toast.makeText(applicationContext,imagePath,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getImagePathFromUri(uri: Uri?): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri!!, projection, null, null, null)
        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            return filePath
        }
        return uri?.path ?: ""
    }

    fun selectTechApi() {
        val api = ApiClient.UserData().create(Api::class.java)
        api.TALENTO_TWO_VIEW_TECHNOLOGIES().enqueue(object : Callback<TalentoTwoRoot> {
            override fun onResponse(
                call: Call<TalentoTwoRoot>,
                response: Response<TalentoTwoRoot>
            ) {
                if (response.isSuccessful) {
                    val root = response.body()
                    if (root?.status == true) {
                        batch_details = Array(root.batch_details.size) { "" }
                        for (i in 0 until root.batch_details.size) {
                            batch_details[i] = root.batch_details[i].batch_name
                            val arrayAdapter = ArrayAdapter(
                                applicationContext,
                                R.layout.simple_list_item_1,
                                batch_details
                            )
                            binding.passTechnologyEt.setAdapter(arrayAdapter)
                        }

                        // Getting batch id
                        batchId = Array(root.batch_details.size) { "" }
                        for (j in 0 until root.batch_details.size) {
                            batchId[j] = root.batch_details[j].batch_id
                            val batchIdArrayAdapter = ArrayAdapter(
                                applicationContext,
                                android.R.layout.simple_list_item_1,
                                batchId
                            )
                        }
                    } else {
                        Toast.makeText(this@GatePassActivity, root?.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this@GatePassActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TalentoTwoRoot>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun selectEventApi() {
        val api = ApiClient.UserData().create(Api::class.java)
        api.TALENTO_TWO_VIEW_EVENTS().enqueue(object : Callback<TalentoTwoRoot> {
            override fun onResponse(
                call: Call<TalentoTwoRoot>,
                response: Response<TalentoTwoRoot>
            ) {
                if (response.isSuccessful) {
                    val root = response.body()
                    if (root?.status == true) {
                        event_details = Array(root.event_details.size) { "" }
                        for (i in 0 until root.event_details.size) {
                            event_details[i] = root.event_details[i].event_name
                            val arrayAdapter = ArrayAdapter(
                                applicationContext,
                                R.layout.simple_list_item_1,
                                event_details
                            )
                            binding.passEventEt.setAdapter(arrayAdapter)
                        }

                        // Getting batch id
                        eventId = Array(root.event_details.size) { "" }
                        for (j in 0 until root.event_details.size) {
                            eventId[j] = root.event_details[j].event_id
                            val batchIdArrayAdapter = ArrayAdapter(
                                applicationContext,
                                android.R.layout.simple_list_item_1,
                                eventId
                            )
                        }
                    } else {
//                        Toast.makeText(this@GatePassActivity, root?.message, Toast.LENGTH_SHORT)
//                            .show()
                    }
                } else {
                    Toast.makeText(this@GatePassActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TalentoTwoRoot>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    suspend fun regularPassSumbitApi() {

       // binding.passSubmitBtn.visibility = View.GONE

        val sharedPreferences = applicationContext?.getSharedPreferences("category", MODE_PRIVATE)
        val userIdd = sharedPreferences?.getString("user_id", null)


        val name =
            RequestBody.create(
                MediaType.parse("text/plain"),
                binding.passFirstNameEt.text.toString()
            )
        val technology = RequestBody.create(MediaType.parse("text/plain"), selectedBatchId)
        val userId = RequestBody.create(MediaType.parse("text/plain"), userIdd)
        val status = RequestBody.create(MediaType.parse("text/plain"), "regular")
        val startDate =
            RequestBody.create(MediaType.parse("text/plain"), binding.startDateEt.text.toString())
        val endDate =
            RequestBody.create(MediaType.parse("text/plain"), binding.endDateEt.text.toString())
        var proImageFilePart: MultipartBody.Part? = null

        try {
            val compressedImageFile = Compressor.compress(applicationContext, proImageFile!!)
            proImageFilePart = MultipartBody.Part.createFormData(
                "avatar",
                proImageFile?.name,
                RequestBody.create(MediaType.parse("image/*"), compressedImageFile)
            )
        } catch (e: NullPointerException) {
            // Handle the exception if needed
        }

        val api = ApiClient.UserData().create(Api::class.java)
        api.TALENTO_TWO_GATE_PASS_REGULAR(userId, name, technology, startDate, endDate,status, proImageFilePart)
            .enqueue(object : Callback<TalentoTwoRoot> {
                override fun onResponse(
                    call: Call<TalentoTwoRoot>,
                    response: Response<TalentoTwoRoot>
                ) {
                    if (response.isSuccessful) {
                        val root = response.body()
                        if (root?.status == true) {
                            Toast(this@GatePassActivity).showCustomToast(
                                root.message,
                                this@GatePassActivity
                            )
                            startActivity(
                                Intent(
                                    this@GatePassActivity,
                                    SmartClassHomeActivity::class.java
                                )
                            )
                        } else {
                            // Toast(this@GatePassActivity).showCustomToast(root.message,this@GatePassActivity)
                            Toast.makeText(this@GatePassActivity, root?.message, Toast.LENGTH_SHORT)
                                .show()
                            startActivity(
                                Intent(
                                    this@GatePassActivity,
                                    SmartClassHomeActivity::class.java
                                )
                            )
                        }
                    } else {
                        Toast.makeText(this@GatePassActivity, "Error", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(
                                this@GatePassActivity,
                                SmartClassHomeActivity::class.java
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<TalentoTwoRoot>, t: Throwable) {
                    Toast.makeText(this@GatePassActivity, "Error", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(
                            this@GatePassActivity,
                            SmartClassHomeActivity::class.java
                        )
                    )
                }
            })
    }
    suspend fun eventPassSumbitApi() {

        // binding.passSubmitBtn.visibility = View.GONE

        val sharedPreferences = applicationContext?.getSharedPreferences("category", MODE_PRIVATE)
        val userIdd = sharedPreferences?.getString("user_id", null)


        val name =
            RequestBody.create(
                MediaType.parse("text/plain"),
                binding.passFirstNameEt.text.toString()
            )
        val technology = RequestBody.create(MediaType.parse("text/plain"), selectedBatchId)
        val userId = RequestBody.create(MediaType.parse("text/plain"), userIdd)
        val status = RequestBody.create(MediaType.parse("text/plain"), "event")
        val eventId = RequestBody.create(MediaType.parse("text/plain"), selectedEventId)

        var proImageFilePart: MultipartBody.Part? = null

        try {
            val compressedImageFile = Compressor.compress(applicationContext, proImageFile!!)
            proImageFilePart = MultipartBody.Part.createFormData(
                "avatar",
                proImageFile?.name,
                RequestBody.create(MediaType.parse("image/*"), compressedImageFile)
            )
        } catch (e: NullPointerException) {
            // Handle the exception if needed
        }

        val api = ApiClient.UserData().create(Api::class.java)
        api.TALENTO_TWO_GATE_PASS_EVENT(userId, name, technology, status, eventId, proImageFilePart)
            .enqueue(object : Callback<TalentoTwoRoot> {
                override fun onResponse(
                    call: Call<TalentoTwoRoot>,
                    response: Response<TalentoTwoRoot>
                ) {
                    if (response.isSuccessful) {
                        val root = response.body()
                        if (root?.status == true) {
                            Toast(this@GatePassActivity).showCustomToast(
                                root.message,
                                this@GatePassActivity
                            )
                            startActivity(
                                Intent(
                                    this@GatePassActivity,
                                    SmartClassHomeActivity::class.java
                                )
                            )
                        } else {
                            // Toast(this@GatePassActivity).showCustomToast(root.message,this@GatePassActivity)
                            Toast.makeText(this@GatePassActivity, root?.message, Toast.LENGTH_SHORT)
                                .show()
                            startActivity(
                                Intent(
                                    this@GatePassActivity,
                                    SmartClassHomeActivity::class.java
                                )
                            )
                        }
                    } else {
                        Toast.makeText(this@GatePassActivity, "Error", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(
                                this@GatePassActivity,
                                SmartClassHomeActivity::class.java
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<TalentoTwoRoot>, t: Throwable) {
                    Toast.makeText(this@GatePassActivity, "Error", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(
                            this@GatePassActivity,
                            SmartClassHomeActivity::class.java
                        )
                    )
                }
            })
    }

    // Function to check and request permission.
    fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this@GatePassActivity,
                arrayOf<String>(permission),
                requestCode
            )
        } else {
            Toast.makeText(applicationContext, "Permission Already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Storage Permission Granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(applicationContext, "Storage Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


}