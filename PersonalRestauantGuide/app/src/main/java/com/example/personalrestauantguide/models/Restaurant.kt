package com.example.personalrestauantguide.models

import java.io.Serializable

data class Restaurant(val name:String, val address:String, val phone:String, val lat:Double, val lon:Double, val tags:String, val image:String, val id: Int, val rating:Double) : Serializable