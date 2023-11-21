package com.courseclass.quickchat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.courseclass.quickchat.ui.theme.QuickChatTheme
import com.courseclass.quickchat.utils.AndroidUtil
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.hbb20.CountryCodePicker
import kotlinx.coroutines.delay
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit

class LoginOtpActivity : AppCompatActivity() {

    private var phoneNumber: String? = null
    private lateinit var otpInput: EditText
    private lateinit var nextBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var resendOtpTextView: TextView
    val mAuth = FirebaseAuth.getInstance()

    var timeOutSec: Long = 60L
    var storedVerificationId: String=" "
    lateinit var resendToken:PhoneAuthProvider.ForceResendingToken


    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginotp);

        otpInput = findViewById(R.id.login_otp)
        nextBtn = findViewById(R.id.login_next_btn)
        progressBar = findViewById(R.id.login_progress_bar)
        resendOtpTextView = findViewById(R.id.resend_otp_textview)

        var enteredOtp:String

        phoneNumber = intent.getStringExtra("phone")
        if (phoneNumber != null) {
            sendOtp(phoneNumber!!, false)

            nextBtn.setOnClickListener { it ->
                run {
                    enteredOtp = otpInput.text.toString()
                    val credential =
                        PhoneAuthProvider.getCredential(storedVerificationId, enteredOtp)
                    signInWithPhoneAuthCredential(credential)
                    setInProgressBar(true)
                }
            }

            //Resend textview button
            /*resendOtpTextView.setOnClickListener { it ->
                    sendOtp(phoneNumber!!, true)
            }*/
        }
        //Toast.makeText(applicationContext, phoneNumber, Toast.LENGTH_LONG).show()
        //val data: MutableMap<String, String> = HashMap()
        //FirebaseFirestore.getInstance().collection("test").add(data)
    }

    fun sendOtp(phoneNumber: String, isResend: Boolean) {
        //Resend function caller
        //startResendTimer()
        setInProgressBar(true)
        /*val builder = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(phoneNumber).setTimeout(timeOutSec,TimeUnit.SECONDS)
            .setActivity(this@LoginOtpActivity).setCallbacks(PhoneAuthProvider.OnVerificationStateChangedCallbacks(){})*/

        val builder = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(timeOutSec, TimeUnit.SECONDS)
            .setActivity(this@LoginOtpActivity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    //Log.d(TAG, "onVerificationCompleted:$credential")
                    signInWithPhoneAuthCredential(credential)
                    setInProgressBar(false)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    //Log.w(TAG, "onVerificationFailed", e)
                    val myClassInstance = AndroidUtil()
                    myClassInstance.showToast(applicationContext,"OTP verification failed")
                    setInProgressBar(false)

                    /*if (e is FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                    } else if (e is FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded
                    } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                        // reCAPTCHA verification attempted with null Activity
                    }*/
                }
                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken,
                ) {
                    //Log.d(TAG, "onCodeSent:$verificationId")
                    storedVerificationId = verificationId
                    resendToken = token
                    val myClassInstance = AndroidUtil()
                    myClassInstance.showToast(applicationContext,"OTP sent successfully")
                    setInProgressBar(false)
                }
            })
        if(isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendToken).build())
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build())
        }
    }

    fun setInProgressBar(inProgress: Boolean) {
        if (inProgress) {
            progressBar.visibility = View.VISIBLE
            nextBtn.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            nextBtn.visibility = View.VISIBLE
        }
    }

    private fun signInWithPhoneAuthCredential(phoneCredential: PhoneAuthCredential) {
        setInProgressBar(true)
        mAuth.signInWithCredential(phoneCredential)
            .addOnCompleteListener(this) { task ->
                setInProgressBar(false)
                if (task.isSuccessful) {
                    //Log.d(TAG, "signInWithCredential:success")
                    val intent = Intent(this@LoginOtpActivity, LoginUsernameActivity::class.java)
                    intent.putExtra("phone",phoneNumber)
                    startActivity(intent)
                    val user = task.result?.user
                } else {
                    val myClassInstance = AndroidUtil()
                    myClassInstance.showToast(applicationContext,"OTP verification failed")
                    //Log.w(TAG, "signInWithCredential:failure", task.exception)
                    /*if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }*/
                }
            }
    }
    //Resend function
    /*fun startResendTimer() {
        resendOtpTextView.isEnabled = false

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                timeOutSec--
                runOnUiThread {
                    resendOtpTextView.text = "Resend OTP in $timeOutSec seconds"
                }
                if (timeOutSec <= 0) {
                    timeOutSec = 60L
                    timer.cancel()
                    runOnUiThread {
                        resendOtpTextView.isEnabled = true
                    }
                }
            }
        }, 0, 1000)
    }*/
}

