package model

import extensions.calculatePercentage
import extensions.roundUp
import validators.validateRawItem
import java.math.BigDecimal

data class Item(val quantity: Int, val name: String, val price: BigDecimal) {
    companion object {
        val EXEMPT_TAX_PRODUCTS = listOf(
            "book",
            "chocolate bar",
            "imported box of chocolates",
            "packet of headache pills"
        )
        val DEFAULT_TAX = BigDecimal("10")
        val IMPORTED_TAX = BigDecimal("5")
        val ROUNDED_TAX_VALUE = BigDecimal("0.05")

        fun build(rawInput: String): Item {
            rawInput.validateRawItem()
            val splittedInput = rawInput.split("|")
            return Item(splittedInput[0].toInt(), splittedInput[1], BigDecimal(splittedInput[2]))
        }
    }
}

fun Item.isImported(): Boolean = this.name.indexOf("imported") >= 0

fun Item.countTotal(): BigDecimal = this.price.multiply(BigDecimal(this.quantity))

fun Item.countLocalTax(): BigDecimal {
    val total = this.countTotal()
    var tax = BigDecimal.ZERO
    if (!Item.EXEMPT_TAX_PRODUCTS.contains(this.name))
        tax = total.calculatePercentage(Item.DEFAULT_TAX)

    return tax.roundUp(Item.ROUNDED_TAX_VALUE)
}

fun Item.countImportTax(): BigDecimal {
    val total = this.countTotal()
    var tax = BigDecimal.ZERO
    if (isImported())
        tax = tax.add(total.calculatePercentage(Item.IMPORTED_TAX))

    return tax.roundUp(Item.ROUNDED_TAX_VALUE)
}