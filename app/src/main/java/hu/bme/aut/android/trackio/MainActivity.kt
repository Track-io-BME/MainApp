package hu.bme.aut.android.trackio

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.databinding.ActivityMainBinding
import hu.bme.aut.android.trackio.model.WorkoutNotificationHelper
import hu.bme.aut.android.trackio.network.InternetConnectivityChecker

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val PERMISSION_REQUEST_CODE = 10101
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE
            )
        }
        InternetConnectivityChecker.init(applicationContext)
        WorkoutNotificationHelper.init(application)
        SharedPrefConfig.init(applicationContext)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!allPermissionsGranted()) {
                REQUIRED_PERMISSIONS.any {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, it)) {
                        AlertDialog.Builder(this)
                            .setTitle(getString(R.string.permissions_dialog))
                            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                                ActivityCompat.requestPermissions(
                                    this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE
                                )
                            }
                            .setNegativeButton(getString(R.string.back), null)
                            .show()
                    }
                    else{
                        ActivityCompat.requestPermissions(
                            this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE
                        )
                    }
                    return
                }
                Toast.makeText(
                    this,
                    getString(R.string.permission_not_granted),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}