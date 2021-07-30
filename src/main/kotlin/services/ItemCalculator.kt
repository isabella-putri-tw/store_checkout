package services

import model.Item
import utils.calculatePercentage
import utils.roundUp
import java.math.BigDecimal

private fun Item.isImported(): Boolean = this.name.indexOf("imported") >= 0

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