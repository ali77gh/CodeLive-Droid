package com.ali77gh.codelive.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.ali77gh.codelive.R
import com.ali77gh.codelive.core.socket.CodeLiveSync
import io.github.kbiakov.codeview.classifier.CodeProcessor

class SetAddressActivity : AppCompatActivity(), CodeLiveSync.ConnectCallback {

    private var progress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_addess)

        val address = findViewById<EditText>(R.id.text_set_address_activity_address)
        val join = findViewById<Button>(R.id.btn_set_address_activity_enter)
        progress = findViewById(R.id.progress_set_address_activity)

        CodeProcessor.init(this)

        join.setOnClickListener {
            if (validate(address.text.toString())) {
                progress?.animate()?.alpha(1.0F)?.start()
                CodeLiveSync.setConnectListener(this)
                CodeLiveSync.connect(address.text.toString())
            }
        }
    }

    override fun success() {
        runOnUiThread {
            CodeLiveSync.setConnectListener(null)
            startMainActivity()
            Toast.makeText(this, "joined successfully", LENGTH_LONG).show()
        }
    }

    override fun failed() {
        runOnUiThread {
            progress?.animate()?.alpha(0.0F)?.start()
            Toast.makeText(this, "connection failed", LENGTH_LONG).show()
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun validate(address: String): Boolean {
        if (address.equals("")) {
            Toast.makeText(this, "plz enter address", LENGTH_LONG).show()
            return false
        }

        if (!address.contains(":")) {
            Toast.makeText(this, "plz enter port", LENGTH_LONG).show()
            return false
        }

        //todo add more validation
        return true
    }
}
