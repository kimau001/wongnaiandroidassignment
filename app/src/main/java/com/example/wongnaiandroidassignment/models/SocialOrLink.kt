package com.example.wongnaiandroidassignment.models

import android.os.Parcel
import android.os.Parcelable
import com.example.wongnaiandroidassignment.bases.BaseJsonObject


data class SocialOrLink(
    var name: String?,
    var url: String = "",
    var type: String = ""
) : Parcelable {

    constructor(baseJsonObject: BaseJsonObject) : this(""){
        name = baseJsonObject.getString("name","")
        url = baseJsonObject.getString("url","")
        type = baseJsonObject.getString("type","")
    }

    constructor(parcel: Parcel) : this(
        parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SocialOrLink> {
        override fun createFromParcel(parcel: Parcel): SocialOrLink {
            return SocialOrLink(parcel)
        }

        override fun newArray(size: Int): Array<SocialOrLink?> {
            return arrayOfNulls(size)
        }
    }


}