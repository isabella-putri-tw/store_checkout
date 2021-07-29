package model

import extensions.setScaleIfItsDecimal
import java.math.BigDecimal

data class ReceiptItem(val quantity: Int, val name: String, val price: BigDecimal, val tax: BigDecimal, val total: BigDecimal) {
    companion object {
        fun builder(item: Item): ReceiptItem {
            val tax = item.countTax().setScaleIfItsDecimal(2)
            val total = item.countTotal().setScaleIfItsDecimal(2)
            return ReceiptItem(item.quantity, item.name, item.price, tax, total.add(tax))
        }
    }

}