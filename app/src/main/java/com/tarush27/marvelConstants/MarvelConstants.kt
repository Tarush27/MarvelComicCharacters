package com.tarush27.marvelConstants

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

const val MARVEL_BASE_LINK = "http://gateway.marvel.com/"
val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
const val API_KEY = "51c4ceb0cc8faf4ca1ed7548ed4ba4e2"
const val PRIVATE_KEY = "02a6ba81bafd6f5337a5a074e8ce32fa1cd25eb0"
fun hash(): String {
    val inputHash = "$timeStamp$PRIVATE_KEY$API_KEY"
    val messageDigestHash = MessageDigest.getInstance("MD5")
    return BigInteger(1, messageDigestHash.digest(inputHash.toByteArray())).toString(16)
        .padStart(32, '0')
}