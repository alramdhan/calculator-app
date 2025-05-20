package org.logixphere.kalkulator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform