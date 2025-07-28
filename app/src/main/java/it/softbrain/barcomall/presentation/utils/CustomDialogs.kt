package it.softbrain.barcomall.presentation.utils

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import it.softbrain.barcomall.R
import it.softbrain.barcomall.databinding.MessageDialogBinding
import it.softbrain.barcomall.databinding.ProgressDialogBinding

object CustomDialogs {
    fun getCustomProgressDialog(activity: Activity): AlertDialog {

        val binding = ProgressDialogBinding.inflate(LayoutInflater.from(activity))

        val pDialogBuilder = AlertDialog.Builder(activity)

        Glide.with(binding.imgLoading).asGif().load(R.drawable.loding).into(binding.imgLoading)

        pDialogBuilder.setView(binding.root)

        val pDialog = pDialogBuilder.create()

        pDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        pDialog.setCancelable(false)
        return pDialog

    }

    fun getMessageDialog(activity: Activity,message:String,showCancelButton:Boolean): AlertDialog {

        val binding = MessageDialogBinding.inflate(LayoutInflater.from(activity))

        val messageDialogBuilder = AlertDialog.Builder(activity)

        messageDialogBuilder.setView(binding.root)

        val messageDialog = messageDialogBuilder.create()

        messageDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        messageDialog.setCancelable(false)


        binding.apply {
            tvMessage.text=message
            if (!showCancelButton)
            {
                btnCancel.visibility= View.GONE
            }

            btnOk.setOnClickListener { messageDialog.dismiss() }
            btnCancel.setOnClickListener { messageDialog.dismiss() }
        }
        messageDialog.show()
        return messageDialog

    }
}