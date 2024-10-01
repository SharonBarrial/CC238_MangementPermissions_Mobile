package com.example.usser_permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //reconocer el controller
        val btCamera = findViewById<Button>(R.id.btCamera)

        //cuando se haga click en el boton
        btCamera.setOnClickListener {
            //verificar permisos de la camara
            checkCameraPermission()
        }
    }

    /**
     * Conocimientos previos
     * 1. Permisos de uso del recurso => **Manifest.permission.CAMERA**
     * 2. Verificar si el usuario tiene permisos => **ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)**
     * 3. Solicitar permisos => **ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)**
     * 4.
     *
     */

    //metodo para verificar permisos de la camara
    private fun checkCameraPermission() {
        //¿Ya tienes el permiso?
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED)
        {
            //No tiene permisos
            Toast.makeText(this, "No tiene permisos", Toast.LENGTH_SHORT).show()
            requestCameraPermission()
        }
        else
        {
            //Tiene permisos
            ////Si se configura manualmente el permiso, sale este mensaje
            Toast.makeText(this, "Tiene permisos", Toast.LENGTH_SHORT).show()
        }

    }

    //metodo para solicitar permisos de la camara
    private fun requestCameraPermission() {
        //¿Ya se ha solicitado permiso anteriormente?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
        {
            //Si ya se ha solicitado permiso anteriormente
            Toast.makeText(this, "Ya se ha solicitado permiso. Habilitelo manualmente!", Toast.LENGTH_SHORT).show()
        }
        else
        {
            //Solicitar permiso
            //Aqui recien se solicita el permiso
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        }
    }

    //metodo para manejar la respuesta de la solicitud de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //verificar si el requestCode es igual al codigo de permiso de la camara
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                //verificar si el usuario ha aceptado el permiso
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permiso aceptado
                    Toast.makeText(this, "Se otorgo el permiso de la camara", Toast.LENGTH_SHORT).show()
                    //aqui vendria la funcionalidad de la camara


                } else {
                    //permiso denegado
                    Toast.makeText(this, "No se otorgo el permiso de la camara", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}