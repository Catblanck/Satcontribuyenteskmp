package com.example.satcontribuyenteskmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform