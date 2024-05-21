package com.example.to6_pmdm.ejercicio1

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.to6_pmdm.R
import com.example.to6_pmdm.databinding.ActivityEjercicio1Binding
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class Ejercicio1 : AppCompatActivity() {

    lateinit var binding: ActivityEjercicio1Binding
    private lateinit var areaDibujo: AreaDibujo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        areaDibujo = AreaDibujo(this, null)
        binding.root.addView(areaDibujo)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_area_dibujo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.saveMenu -> guardarDibujo()
            R.id.deleteMenu -> areaDibujo.borrarDibujo()
            R.id.backDrawMenu -> areaDibujo.deshacerUltimoTrazo()
            R.id.colorMenu -> cambiarColorTrazo()
            R.id.grosorTrazoMenu -> cambiarGrosorTrazo()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun guardarDibujo(){
        val bitmap = getBitmapWithBackground(areaDibujo, Color.WHITE)
        saveBitmapToFile(bitmap)
    }

    private fun getBitmapWithBackground(view: View, backgroundColor: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(backgroundColor)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmapToFile(bitmap: Bitmap){
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        var fileName = "dibujo.png"
        var file = File(path, fileName)

        var counter = 1
        while (file.exists()){
            fileName = "dibujo_${counter++}.png"
            file = File(path, fileName)
        }

        try{
            FileOutputStream(file).use {fos ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                Toast.makeText(this, "Dibujo guardado en ${file.absolutePath}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar el dibujo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cambiarColorTrazo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona el color del trazo")

        val dialogLayout = layoutInflater.inflate(R.layout.dialog_color_selection, null)
        builder.setView(dialogLayout)

        val radioGroup = dialogLayout.findViewById<RadioGroup>(R.id.radioGroup)

        builder.setPositiveButton("Aceptar") {dialog, _ ->
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = dialogLayout.findViewById<RadioButton>(selectedRadioButtonId)

            val colorName = selectedRadioButton.text.toString().toLowerCase()
            val colorResourceId = resources.getIdentifier(colorName, "color", packageName)

            if(colorResourceId != 0){
                areaDibujo.colorTrazo = ContextCompat.getColor(this, colorResourceId)
            } else Toast.makeText(this, "Color no válido", Toast.LENGTH_SHORT).show()

            Toast.makeText(this, "Color seleccionado: $colorName", Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") {dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun cambiarGrosorTrazo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona el grosor del trazo")

        val dialogLayout = layoutInflater.inflate(R.layout.dialog_grosor_trazo, null)
        builder.setView(dialogLayout)

        val etGrosorTrazo = dialogLayout.findViewById<EditText>(R.id.etGrosorTrazo)
        etGrosorTrazo.setText(areaDibujo.grosorTrazo.toString())

        builder.setPositiveButton("Aceptar") {dialog, _ ->
            val grosorTrazo = etGrosorTrazo.text.toString().toIntOrNull()

            if(grosorTrazo != null && grosorTrazo <= 20 && grosorTrazo >= 1){
                areaDibujo.grosorTrazo = grosorTrazo
                dialog.dismiss()
                Toast.makeText(this, "Grosor establecido: $grosorTrazo", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Grosor no establecido, fuera del rango", Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("Cancelar") {dialog, _ ->
            dialog.dismiss()
        }

        builder.show()

    }

}
