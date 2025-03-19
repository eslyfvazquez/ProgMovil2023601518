package com.example.aceler

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var sableImageView: ImageView
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplicar el tema sin depender de styles.xml
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar)

        super.onCreate(savedInstanceState)

        // Crear el layout dinámicamente
        val layout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(Color.BLACK) // Fondo negro para simular el ambiente de Star Wars
        }

        // Crear el ImageView para el sable láser
        sableImageView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(900, 1700)
            setImageResource(R.drawable.sable_azul) // Imagen inicial del sable
        }

        // Agregar el sable al layout
        layout.addView(sableImageView)
        setContentView(layout)

        // Configurar el acelerómetro
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer == null) {
            Toast.makeText(this, "Este dispositivo no tiene acelerómetro", Toast.LENGTH_LONG).show()
        }

        // Obtener el servicio de vibración
        vibrator = getSystemService(Vibrator::class.java)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            val acceleration = sqrt((x * x + y * y + z * z).toDouble())

            if (acceleration > 15) { // Sensibilidad del sable
                cambiarColorSable()
                reproducirSonido()
                vibrarDispositivo()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun cambiarColorSable() {
        val colores = listOf(R.drawable.sable_azul, R.drawable.sable_rojo, R.drawable.sable_verde)
        val nuevoColor = colores.random()
        sableImageView.setImageResource(nuevoColor)
    }

    private fun reproducirSonido() {
        try {
            mediaPlayer?.release() // Liberar cualquier sonido anterior
            mediaPlayer = MediaPlayer.create(this, R.raw.sable)
            mediaPlayer?.start()
            mediaPlayer?.setOnCompletionListener { it.release() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun vibrarDispositivo() {
        try {
            vibrator?.let {
                if (it.hasVibrator()) {
                    val efecto = VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)
                    it.vibrate(efecto)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}