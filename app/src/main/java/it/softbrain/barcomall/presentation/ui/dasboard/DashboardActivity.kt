package it.softbrain.barcomall.presentation.ui.dasboard

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import it.softbrain.barcomall.R
import it.softbrain.barcomall.data.util.SharedPref
import it.softbrain.barcomall.data.util.SharedPref.IS_USER_LOGIN
import it.softbrain.barcomall.databinding.ActivityDashboardBinding
import it.softbrain.barcomall.databinding.NavigationDrawerBinding
import it.softbrain.barcomall.presentation.ui.authentication.LoginActivity
import it.softbrain.barcomall.presentation.viewModel.dashboard.DashboardViewModel
import it.softbrain.barcomall.presentation.viewModel.dashboard.DashboardViewModelFactory
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    lateinit var dashboardViewModel: DashboardViewModel

    @Inject
    lateinit var dashboardViewModelFactory: DashboardViewModelFactory

    private lateinit var context: Context
    private lateinit var activity: Activity

    lateinit var binding: ActivityDashboardBinding

    private var isUserLogin: Boolean = false

     lateinit var navDialogBinding:NavigationDrawerBinding
    private lateinit var navDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this
        activity = this
        navDialog = Dialog(context)
        navDialogBinding = NavigationDrawerBinding.inflate(layoutInflater)

        isUserLogin = SharedPref.getBoolean(context, IS_USER_LOGIN)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        handleBottomBar()


        dashboardViewModel =
            ViewModelProvider(this, factory = dashboardViewModelFactory)[DashboardViewModel::class]
        replaceFragment(DiscoverFragment(), Bundle())
    }

    private fun handleBottomBar() {
        binding.apply {

            navDialog.setContentView(navDialogBinding.root)
            //dialog.window.setWindowAnimations()
            navDialog.window?.setBackgroundDrawableResource(R.drawable.linear_gradient)
            navDialog.window?.setGravity(Gravity.START or Gravity.TOP)
            navDialog.window?.setLayout(
                (resources.displayMetrics.widthPixels * 0.75).toInt(),
                LayoutParams.MATCH_PARENT
            )


            imgMenu.setOnClickListener {
                navDialog.show()
            }

            imgLogo.setOnClickListener {
                tvDiscover.visibility = View.VISIBLE
                tvFavourites.visibility = View.GONE
                tvProfile.visibility = View.GONE
                tvCart.visibility = View.GONE

                imgDiscover.setColorFilter(ContextCompat.getColor(context, R.color.primaryColor))
                imgFavourites.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                imgProfile.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                imgCart.setColorFilter(ContextCompat.getColor(context, R.color.gray))

                replaceFragment(DiscoverFragment(), Bundle())
            }

            discoverLy.setOnClickListener {
                tvDiscover.visibility = View.VISIBLE
                tvFavourites.visibility = View.GONE
                tvProfile.visibility = View.GONE
                tvCart.visibility = View.GONE

                imgDiscover.setColorFilter(ContextCompat.getColor(context, R.color.primaryColor))
                imgFavourites.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                imgProfile.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                imgCart.setColorFilter(ContextCompat.getColor(context, R.color.gray))

                replaceFragment(DiscoverFragment(), Bundle())
            }

            profileLy.setOnClickListener {

                if (isUserLogin) {
                    tvDiscover.visibility = View.GONE
                    tvFavourites.visibility = View.GONE
                    tvProfile.visibility = View.VISIBLE
                    tvCart.visibility = View.GONE

                    imgProfile.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.primaryColor
                        )
                    )
                    imgFavourites.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                    imgDiscover.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                    imgCart.setColorFilter(ContextCompat.getColor(context, R.color.gray))

                    Toast.makeText(context, "Profile", Toast.LENGTH_LONG).show()
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                    finish()
                }
            }

            favouritesLy.setOnClickListener {

                if (isUserLogin) {
                    tvDiscover.visibility = View.GONE
                    tvFavourites.visibility = View.VISIBLE
                    tvProfile.visibility = View.GONE
                    tvCart.visibility = View.GONE

                    imgFavourites.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.primaryColor
                        )
                    )
                    imgProfile.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                    imgDiscover.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                    imgCart.setColorFilter(ContextCompat.getColor(context, R.color.gray))

                    Toast.makeText(context, "Favourite", Toast.LENGTH_LONG).show()
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                    finish()
                }
            }

            cartLy.setOnClickListener {

                if (isUserLogin) {
                    tvDiscover.visibility = View.GONE
                    tvFavourites.visibility = View.GONE
                    tvProfile.visibility = View.GONE
                    tvCart.visibility = View.VISIBLE

                    imgCart.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.primaryColor
                        )
                    )
                    imgProfile.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                    imgDiscover.setColorFilter(ContextCompat.getColor(context, R.color.gray))
                    imgFavourites.setColorFilter(ContextCompat.getColor(context, R.color.gray))

                    Toast.makeText(context, "Cart", Toast.LENGTH_LONG).show()
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                    finish()
                }
            }


        }
    }

    fun replaceFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fram, fragment)
        fragmentTransaction.addToBackStack(fragment.id.toString())
        fragmentTransaction.commit()

    }

}