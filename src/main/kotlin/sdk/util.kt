package sdk

import java.util.*

val generateString = ('a'..'z').randomString(6)

fun ClosedRange<Char>.randomString(lenght: Int) =
    (1..lenght)
        .map { (Random().nextInt(endInclusive.toInt() - start.toInt()) + start.toInt()).toChar() }
        .joinToString("")