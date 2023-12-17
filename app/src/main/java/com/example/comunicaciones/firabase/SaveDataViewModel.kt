package com.example.comunicaciones.firabase

import android.content.Context
import android.net.wifi.WifiInfo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comunicaciones.data.UsuarioDataClass
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class SaveDataViewModel : ViewModel() {


    val db = Firebase.firestore


    fun saveDataWifi(usuario: UsuarioDataClass, context: Context) {
        val collectionReference = db.collection("RedWifi")

        val usuarioMap = usuario.toMap()

        collectionReference.document(usuario.id)
            .set(usuarioMap).addOnSuccessListener {
                // El documento se ha agregado con el ID que has establecido manualmente
                Log.i(
                    "mensaje",
                    "Se agregó el documento con el ID establecido manualmente: ${usuario.id}"
                )
            }
            .addOnFailureListener {
                Log.i("mensaje", "Se produjo el siguiente error ${it}")
            }
    }


    fun saveDataRed(usuario: UsuarioDataClass){
        val collectionReference = db.collection("RedMovile")

        val usuarioMap = usuario.toMap()

        collectionReference.document(usuario.id)
            .set(usuarioMap).addOnSuccessListener {
                // El documento se ha agregado con el ID que has establecido manualmente
                Log.i(
                    "mensaje",
                    "Se agregó el documento con el ID establecido manualmente: ${usuario.id}"
                )
            }
            .addOnFailureListener {
                Log.i("mensaje", "Se produjo el siguiente error ${it}")
            }

    }

    fun UpdateDataRed(usuario: UsuarioDataClass){
        val collectionReference = db.collection("RedMovile")

        val documentoReferencia = collectionReference.document()
        //Obtenemos la id

        val nuevoID = documentoReferencia.id
        Log.i("id1", "${usuario.id}")
        Log.i("id2", "$nuevoID")
        val usuarioRef = db.collection("RedMovile").document(usuario.id)

        // Obtén el documento actual de Firestore para preservar el ID
        usuarioRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // El documento existe, obtenemos el ID y actualizamos todos los campos
                    val nuevoUsuario = documentSnapshot.toObject(UsuarioDataClass::class.java)
                    nuevoUsuario?.let {
                        usuarioRef.set(usuario.copy(id = it.id).toMap())
                            .addOnSuccessListener {
                                Log.i("mensaje", "Se actualizó el documento con ID: ${usuario.id}")
                            }
                            .addOnFailureListener { exception ->
                                Log.e(
                                    "mensaje",
                                    "Se produjo el siguien te error al actualizar: $exception"
                                )
                            }
                    }
                } else {
                    Log.e("mensaje", "El documento con ID ${usuario.id} no existe.")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "mensaje",
                    "Se produjo el siguiente error al obtener el documento: $exception"
                )
            }

    }


    fun UpdateData(usuario: UsuarioDataClass) {
        val collectionReference = db.collection("RedWifi")

        val documentoReferencia = collectionReference.document()
        //Obtenemos la id

        val nuevoID = documentoReferencia.id
        Log.i("id1", "${usuario.id}")
        Log.i("id2", "$nuevoID")
        val usuarioRef = db.collection("RedWifi").document(usuario.id)

        // Obtén el documento actual de Firestore para preservar el ID
        usuarioRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // El documento existe, obtenemos el ID y actualizamos todos los campos
                    val nuevoUsuario = documentSnapshot.toObject(UsuarioDataClass::class.java)
                    nuevoUsuario?.let {
                        usuarioRef.set(usuario.copy(id = it.id).toMap())
                            .addOnSuccessListener {
                                Log.i("mensaje", "Se actualizó el documento con ID: ${usuario.id}")
                            }
                            .addOnFailureListener { exception ->
                                Log.e(
                                    "mensaje",
                                    "Se produjo el siguien te error al actualizar: $exception"
                                )
                            }
                    }
                } else {
                    Log.e("mensaje", "El documento con ID ${usuario.id} no existe.")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "mensaje",
                    "Se produjo el siguiente error al obtener el documento: $exception"
                )
            }
    }

}
