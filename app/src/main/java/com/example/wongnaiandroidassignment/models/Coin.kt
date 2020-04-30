package com.example.wongnaiandroidassignment.models

import android.os.Parcel
import android.os.Parcelable
import com.example.wongnaiandroidassignment.bases.BaseJsonObject


data class Coin(
    var id: Int,
    var uuid: String? = "",
    var slug: String? = "",
    var symbol: String? = "",
    var name: String? = "",
    var description: String? = "",
    var color: String? = "",
    var iconType: String? = "",
    var iconUrl: String? = "",
    var websiteUrl: String? = "",
    var history: ArrayList<String> = ArrayList(),
    var socials: ArrayList<SocialOrLink> = ArrayList(),
    var links:  ArrayList<SocialOrLink> = ArrayList(),
    var confirmedSupply: Boolean = false,
    var approvedSupply: Boolean = false,
    var penalty: Boolean = false,
    var numberOfMarkets: Int = 0,
    var numberOfExchanges: Int = 0,
    var volume: Int = 0,
    var marketCap: Int = 0,
    var circulatingSupply: Int = 0,
    var totalSupply: Int = 0,
    var firstSeen: Int = 0,
    var rank: Int = 0,
    var change: Double = 0.00,
    var type: String? = "",
    var price: String? = "",
    var allTimeHigh_price: String? = "",
    var allTimeHigh_timestamp: Int = 0
) : Parcelable {

    constructor(baseJsonObject: BaseJsonObject) : this(0){
        id = baseJsonObject.getInt("id",0)
        uuid = baseJsonObject.getString("uuid","")
        slug = baseJsonObject.getString("slug","")
        symbol = baseJsonObject.getString("symbol","")
        name = baseJsonObject.getString("name","")
        description = baseJsonObject.getString("description","")
        color = baseJsonObject.getString("color","")
        iconType = baseJsonObject.getString("iconType","")
        iconUrl = baseJsonObject.getString("iconUrl","")
        websiteUrl = baseJsonObject.getString("websiteUrl","")
        confirmedSupply = baseJsonObject.getBoolean("confirmedSupply",false)
        approvedSupply = baseJsonObject.getBoolean("approvedSupply",false)
        penalty = baseJsonObject.getBoolean("penalty",false)
        numberOfMarkets = baseJsonObject.getInt("numberOfMarkets",0)
        numberOfExchanges = baseJsonObject.getInt("numberOfExchanges",0)
        volume = baseJsonObject.getInt("volume",0)
        marketCap = baseJsonObject.getInt("marketCap",0)
        circulatingSupply = baseJsonObject.getInt("circulatingSupply",0)
        totalSupply = baseJsonObject.getInt("totalSupply",0)
        firstSeen = baseJsonObject.getInt("firstSeen",0)
        rank = baseJsonObject.getInt("rank",0)
        change = baseJsonObject.getDouble("change",0.00)
        type = baseJsonObject.getString("type","")
        price = baseJsonObject.getString("price","")
        allTimeHigh_price = baseJsonObject.getString("allTimeHigh_price","")
        allTimeHigh_timestamp = baseJsonObject.getInt("allTimeHigh_timestamp",0)

        val historyList = baseJsonObject.getJSONArray("history")

        for (i in 0 until historyList.length()){
            history.add(historyList.getString(i))
        }

        val socialList = baseJsonObject.getJSONArray("socials")

        for (i in 0 until socialList.length()){
            socials.add(SocialOrLink(BaseJsonObject(socialList.getJSONObject(i).toString())))
        }

        val linkList = baseJsonObject.getJSONArray("links")

        for (i in 0 until linkList.length()){
            links.add(SocialOrLink(BaseJsonObject(linkList.getJSONObject(i).toString())))
        }

    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        arrayListOf<String>().apply {
            parcel.readList(this as List<*>, kotlin.String::class.java.classLoader)
        },
        arrayListOf<SocialOrLink>().apply {
            parcel.readList(this as List<*>, SocialOrLink::class.java.classLoader)
        },
        arrayListOf<SocialOrLink>().apply {
            parcel.readList(this as List<*>, SocialOrLink::class.java.classLoader)
        },
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(uuid)
        parcel.writeString(slug)
        parcel.writeString(symbol)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(color)
        parcel.writeString(iconType)
        parcel.writeString(iconUrl)
        parcel.writeString(websiteUrl)
        parcel.writeList(history as List<*>?)
        parcel.writeList(socials as List<*>?)
        parcel.writeList(links as List<*>?)
        parcel.writeByte(if (confirmedSupply) 1 else 0)
        parcel.writeByte(if (approvedSupply) 1 else 0)
        parcel.writeByte(if (penalty) 1 else 0)
        parcel.writeInt(numberOfMarkets)
        parcel.writeInt(numberOfExchanges)
        parcel.writeInt(volume)
        parcel.writeInt(marketCap)
        parcel.writeInt(circulatingSupply)
        parcel.writeInt(totalSupply)
        parcel.writeInt(firstSeen)
        parcel.writeInt(rank)
        parcel.writeDouble(change)
        parcel.writeString(type)
        parcel.writeString(price)
        parcel.writeString(allTimeHigh_price)
        parcel.writeInt(allTimeHigh_timestamp)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coin> {
        override fun createFromParcel(parcel: Parcel): Coin {
            return Coin(parcel)
        }

        override fun newArray(size: Int): Array<Coin?> {
            return arrayOfNulls(size)
        }
    }


}