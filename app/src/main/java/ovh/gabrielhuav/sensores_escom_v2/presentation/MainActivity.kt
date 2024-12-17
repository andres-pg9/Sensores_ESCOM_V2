package ovh.gabrielhuav.sensores_escom_v2.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import ovh.gabrielhuav.sensores_escom_v2.R
import ovh.gabrielhuav.sensores_escom_v2.presentation.components.GameplayActivity

class MainActivity : AppCompatActivity() {

    private val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        )
    } else {
        arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // Verificar permisos
        if (!hasPermissions()) {
            requestPermissions()
        }

        // Botón para el modo de un jugador
        val refactorButton = findViewById<Button>(R.id.btnRefactor)
        val etPlayerName = findViewById<EditText>(R.id.etPlayerName)

        refactorButton.setOnClickListener {
            val intent = Intent(this, GameplayActivity::class.java)
            startActivity(intent)
        }

        refactorButton.setOnClickListener {
            val playerName = etPlayerName.text.toString()
            if (playerName.isNotEmpty()) {
                val intent = Intent(this, GameplayActivity::class.java).apply {
                    putExtra("PLAYER_NAME", playerName)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, ingrese su nombre.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun hasPermissions(): Boolean {
        return requiredPermissions.all {
            ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, requiredPermissions, REQUEST_PERMISSIONS_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            val allGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (!allGranted) {
                Toast.makeText(this, "Debe otorgar los permisos para continuar.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 101
    }
}