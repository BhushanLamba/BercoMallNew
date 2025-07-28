package it.softbrain.barcomall.presentation.ui.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import it.softbrain.barcomall.R
import it.softbrain.barcomall.data.util.Constants.Companion.LOGIN_API_KEY
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.data.util.SharedPref
import it.softbrain.barcomall.data.util.SharedPref.IS_USER_LOGIN
import it.softbrain.barcomall.databinding.ActivityLoginBinding
import it.softbrain.barcomall.presentation.ui.MainActivity
import it.softbrain.barcomall.presentation.ui.dasboard.DashboardActivity
import it.softbrain.barcomall.presentation.utils.CustomDialogs
import it.softbrain.barcomall.presentation.viewModel.login.LoginViewModel
import it.softbrain.barcomall.presentation.viewModel.login.LoginViewModelFactory
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userName: String
    private lateinit var password: String

    private lateinit var progressDialog: android.app.AlertDialog
    private lateinit var activity: Activity
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activity = this
        context = this

        progressDialog = CustomDialogs.getCustomProgressDialog(activity)

        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class]

        binding.apply {
            tvLogin.setOnClickListener {
                userName = etUsername.text.toString().trim()
                password = etPassword.text.toString().trim()

                if (TextUtils.isEmpty(userName)) {
                    etUsername.error = "Required"
                } else if (TextUtils.isEmpty(password)) {
                    etPassword.error = "Required"
                } else {
                    loginViewModel.loginUser(userName, password, LOGIN_API_KEY)
                }
            }

            tvWithoutLogin.setOnClickListener {
                startActivity(Intent(activity, DashboardActivity::class.java))
                finish()
            }
        }

        setUpObserver()

    }

    private fun setUpObserver() {
        loginViewModel.loginUserData.observe(this)
        {
            when (it) {
                is Resource.Error -> {
                    progressDialog.dismiss()
                    CustomDialogs.getMessageDialog(activity, it.message.toString(), false)
                }

                is Resource.Loading -> {
                    progressDialog.show()
                }

                is Resource.Success -> {
                    progressDialog.dismiss()

                    val response = it.data
                    val responseObject = JSONObject(response.toString())
                    val statusCode = responseObject.getString("Status_Code").toString()
                    val message = responseObject.getString("Message").toString()

                    if (statusCode.equals("1", true)) {
                        SharedPref.setBoolean(context, IS_USER_LOGIN, true)
                        startActivity(Intent(activity, DashboardActivity::class.java))
                        finish()
                    } else {
                        CustomDialogs.getMessageDialog(activity, message, false)
                    }
                }
            }
        }
    }
}