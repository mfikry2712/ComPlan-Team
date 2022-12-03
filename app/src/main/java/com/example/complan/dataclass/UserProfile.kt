package com.example.complan.dataclass

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserProfile(
    val namaUser: String? = null,
    val jurusanUser: String? = null,
    val kodeSekolah: String? = null,
){
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}