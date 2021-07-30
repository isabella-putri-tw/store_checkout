package model

import services.countImportTax
import services.countLocalTax
import services.countTotal
import utils.setScaleIfItsDecimal
import java.math.BigDecimal

data class ReceiptItem(val quantity: Int, val name: String, val price: BigDecimal, val tax: Tax, val total: BigDecimal) {
    class Tax(val local: BigDecimal, val import: BigDecimal) {
        fun totalTax(): BigDecimal = local.add(import)
    }



    companion object {
        fun builder(item: Item): ReceiptItem {
            val localTax = item.countLocalTax().setScaleIfItsDecimal(2)
            val importTax = item.countImportTax().setScaleIfItsDecimal(2)
            val total = item.countTotal().setScaleIfItsDecimal(2)
            return ReceiptItem(item.quantity, item.name, item.price, Tax(localTax, importTax), total.add(localTax).add(importTax))
        }
    }
}