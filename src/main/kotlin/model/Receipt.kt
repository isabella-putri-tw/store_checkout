package model

import java.math.BigDecimal

data class Receipt(val items: List<ReceiptItem>, val salesTax: BigDecimal, val total: BigDecimal) {
    enum class Field(val displayName: String) {
        SALES_TAX("Sales tax"), TOTAL("Total")
    }
}
