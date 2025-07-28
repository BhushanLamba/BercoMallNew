package it.softbrain.barcomall.presentation.utils

import android.app.ProgressDialog.show
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.softbrain.barcomall.R
import it.softbrain.barcomall.databinding.FragmentImageZoomBinding
import it.softbrain.barcomall.presentation.ui.MainActivity
import it.softbrain.barcomall.presentation.ui.dasboard.DashboardActivity

class ImageZoomFragment : Fragment() {

    private var _binding: FragmentImageZoomBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var productImage: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentImageZoomBinding.inflate(inflater, container, false)

        (activity as DashboardActivity).binding.bottomLy.visibility=View.GONE
        (activity as DashboardActivity).binding.topLy.visibility=View.GONE

        productImage = arguments?.getString("productImage").toString()

        Glide.with(binding.imgZoom.context)
            .load(productImage)
            .placeholder(R.drawable.logo)
            .override(800, 600)                  // resize to prevent memory issues
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imgZoom)


        return binding.root

    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        (activity as DashboardActivity).binding.bottomLy.visibility=View.VISIBLE
        (activity as DashboardActivity).binding.topLy.visibility=View.VISIBLE

        _binding = null
    }
}