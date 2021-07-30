package utils

import java.math.BigDecimal

val PERCENT_DIVISOR = BigDecimal("100")

fun BigDecimal.calculatePercentage(percentage: BigDecimal): BigDecimal {
    return this.multiply(percentage).divide(PERCENT_DIVISOR)
}

fun BigDecimal.roundUp(roundedValue: BigDecimal): BigDecimal {
    val remainder = this.remainder(roundedValue)
    return if (remainder.compareTo(BigDecimal.ZERO) == 0)
        this
    else {
        this.add(roundedValue).subtract(remainder).setScale(roundedValue.scale())
    }
}

fun BigDecimal.setScaleIfItsDecimal(scale: Int): BigDecimal = if (this.scale() > 0) this.setScale(scale) else this