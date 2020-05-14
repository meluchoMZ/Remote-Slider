package mbg.meluchoMZ.RemoteSliderMobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

/**
 * The first screen(activity) of the RemoteSliderMobile app.
 * @author Miguel Blanco Godón [github(meluchoMZ)]
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sliderButton = findViewById<Button>(R.id.slidersButton)
        sliderButton.setOnClickListener {
            val intent = Intent(this, SliderActivity::class.java)
            startActivity(intent)
        }
    }
}
