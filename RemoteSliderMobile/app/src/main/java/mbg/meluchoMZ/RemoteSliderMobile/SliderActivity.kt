package mbg.meluchoMZ.RemoteSliderMobile

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.BufferedOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.IllegalArgumentException
import java.net.Socket
import java.net.UnknownHostException

/**
 * The second activity of the app. It connects and interacts with the server.
 * @author Miguel Blanco Godón [github(meluchoMZ)]
 */
class SliderActivity : AppCompatActivity() {
    private lateinit var connectButton : Button
    private lateinit var inputIP : EditText
    private lateinit var inputPort : EditText
    private lateinit var rePagButton : Button
    private lateinit var avPagButton : Button
    private var ip : String? = null
    private var port : Int? = null

    val BADIP : Int = 80
    val BADPORT : Int = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)
        connectButton = findViewById<Button>(R.id.connectButton)
        inputIP =  findViewById<EditText>(R.id.inputIP)
        inputPort = findViewById<EditText>(R.id.inputPort)
        rePagButton = findViewById<Button>(R.id.rePag)
        avPagButton = findViewById<Button>(R.id.avPag)

        /* OnClick connect button action */
        connectButton.setOnClickListener {
            try {
                println("ip = " + inputIP.text.toString())
                ip = inputIP.text.toString()
                port = Integer.parseInt(inputPort.text.toString())
            } catch (e : Exception) {
                ip = null
                port = null
                Toast.makeText(this, "Error: Cannot save this info", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (ip!!.isEmpty()) {
                Toast.makeText(this, "Error: Cannot save this info", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (ip != null && port != null) {
                Toast.makeText(this, "IP and PORT saved", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        /* OnClick rePag button action */
        rePagButton.setOnClickListener {
            if (!tryStatus())
                Toast.makeText(this, "No connection established", Toast.LENGTH_SHORT).show()
            else {
                Background().execute("REPAG")
            }

        }

        /* OnClick avPag button action */
        avPagButton.setOnClickListener {
            if (!tryStatus())
                Toast.makeText(this, "Error: No IP found", Toast.LENGTH_LONG).show()
            else {
                 Background().execute("AVPAG")
            }
        }

    }

    fun tryStatus() : Boolean {
        return (ip != null && port != null)
    }


    internal inner class Background : AsyncTask<String, String, Int>() {

        override fun doInBackground(vararg params: String?): Int {
            try {
                val s : Socket = Socket(ip, port!!)
                val os : OutputStream = BufferedOutputStream(s.getOutputStream())
                os.write(params[0].toString().toByteArray())
                os.flush()
                s.close()
            } catch (u : UnknownHostException) {
                u.printStackTrace()
                return BADIP
            } catch (io : IOException) {
                io.printStackTrace()
            } catch (se : SecurityException) {
                se.printStackTrace()
            } catch (i : IllegalArgumentException) {
                i.printStackTrace()
                return BADPORT
            } catch (ex : Exception) {
                ex.printStackTrace()
            }
            return 0
        }
    }
}


