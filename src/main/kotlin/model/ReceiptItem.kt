package model

import extensions.setScaleIfItsDecimal
import java.math.BigDecimal

data class ReceiptItem(val quantity: Int, val name: String, val price: BigDecimal, val tax: Tax, val total: BigDecimal) {
    class Tax(val local: BigDecimal, val import: BigDecimal) {
        fun totalTax(): BigDecimal = local.add(import)
    }

    // This enum seems to be used only for output formatting, you might want to shift them into the appropriate file/ class
    enum class Field (val displayName: String){
        QTY("Qty"), NAME("Name"), PRICE("Price"), TAX("Tax"), LOCAL("Local"), IMPORT("Import"), TOTAL("Total")
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