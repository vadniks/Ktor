package com.some.service

const val sortParam = "array"

fun sort(array: String): String? = check(array) { shellSort(
    array.split(',').map { it.toInt() }.toTypedArray()
) }

private fun check(array: String, after: () -> String): String? {
    var alpha = ","
    for (i in 0..9) alpha += i

    for (i in array) {
        var isBad = true
        for (j in alpha)
            if (i == j) {
                isBad = false
                break
            }
        if (isBad) return null
    }
    return after()
}

private fun shellSort(arr: Array<Int>): String {
    var interval = arr.size
    while (interval > 0) {
        var i = interval
        while (i < arr.size) {
            val temp = arr[i]
            var j = i
            while (j >= interval && arr[j - interval] > temp) {
                arr[j] = arr[j - interval]
                j -= interval
            }
            arr[j] = temp
            i += 1
        }
        interval /= 2
    }
    return arr.contentToString()
}
