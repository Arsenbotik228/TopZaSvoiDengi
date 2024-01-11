package com.myself223.ulanagaybotiknakotline

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


data class Notes(
        val title: String? = null,
        val date: String? = null,
        val desc: String? = null,
        val image: String? = null
): Serializable

