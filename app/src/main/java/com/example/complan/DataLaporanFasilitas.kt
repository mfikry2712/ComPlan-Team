package com.example.complan

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DataLaporanFasilitas(
    val idStudent: String? = null,
    val namaFasilitas: String? = null,
    val lokasiFasilitas: String? = null,
    val description: String? = null,
    val photo: String? = null,
    val timestamp: Long? = null
){
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}