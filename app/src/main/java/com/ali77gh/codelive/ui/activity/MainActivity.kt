package com.ali77gh.codelive.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.ali77gh.codelive.R

import com.ali77gh.codelive.core.socket.CodeLiveSync
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.highlight.ColorTheme


class MainActivity : AppCompatActivity(), CodeLiveSync.CodeLiveSyncCallback {


    var codeView: CodeView? = null
    var state: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        codeView = findViewById(R.id.code_view_main)
        state = findViewById(R.id.image_main_connection_state)

        CodeLiveSync.setSyncListener(this)


        val test =
                "<html>\n" +
                        "   <head>\n" +
                        "   <title>\n" +
                        "       Tilte\n" +
                        "   </title>\n" +
                        "   </head>\n" +
                        "   <body>\n" +
                        "       hello world\n" +
                        "   </body>\n" +
                        "</html>\n"


        codeView?.setCode(test, "xml")

        var colorTheme: ColorTheme = ColorTheme.MONOKAI

        codeView?.getOptions()?.setTheme(colorTheme)

    }

    override fun onLoad(holeText: String, langId: String) {
        runOnUiThread {}
    }

    override fun onCursorChange(o: Any) {
        runOnUiThread {}
    }

    override fun onScrollChange(o: Any) {
        runOnUiThread {}
    }

    override fun onRun(o: Any) {
        runOnUiThread {}
    }

    override fun onConnectionStateChange(s: Int) {
        runOnUiThread {
            if (s == CodeLiveSync.CONNECT)
                state?.setImageDrawable(resources.getDrawable(R.drawable.main_cloud_connect))
            else if (s == CodeLiveSync.DISCONNECT)
                state?.setImageDrawable(resources.getDrawable(R.drawable.main_cloud_disconnect))
        }
    }
}
