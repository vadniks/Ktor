package com.some.service

const val drawerParam = "encoded"
private val shapes = arrayOf(0b00u, 0b01u, 0b10u)
private val colors = mapOf(
    0b00u to "red",
    0b01u to "green",
    0b10u to "blue"
)

fun draw(encoded: Int): Triple<UInt, UInt, String?>? {
    if (encoded <= 0 || encoded > 0b11111111) return null
    @Suppress("NAME_SHADOWING") val encoded = encoded.toUInt()

    val shape = (encoded and 0b11000000u) shr 6
    val color = (encoded and 0b00110000u) shr 4
    val width = ((encoded and 0b00001100u) shr 2) * 20u
    val height = ((encoded and 0b00000011u) shr 0) * 20u

    if (shape > 0b10u || color > 0b10u || width == 0u || height == 0u) return null
    return Triple(width, height, performDraw(shape, colors[color]!!, width, height))
}

private fun performDraw(
    shape: UInt,
    color: String,
    width: UInt,
    height: UInt,
    halfWidth: UInt = width / 2u
): String? = when (shape) {
    shapes[0] -> """
        <circle
            cx="$halfWidth"
            cy="$halfWidth"
            r="$halfWidth"
            fill="$color"/>
    """.trimIndent()
    shapes[1] -> """
        <rect 
            width="$width" 
            height="$height" 
            fill="$color"/>
    """.trimIndent()
    shapes[2] -> """
        <polygon 
            points="$width,$height 0,0 0,$height" 
            fill="$color"/>
    """.trimIndent()
    else -> null
}
