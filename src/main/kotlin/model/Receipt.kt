package model

import java.math.BigDecimal

data class Receipt(val items: List<ReceiptItem>, val salesTax: BigDecimal, val total: BigDecimal) {
    // This enum seems to be used only for output formatting, you might want to shift them into the appropriate file/ class
    enum class Field(val displayName: String) {
        SALES_TAX("Sales tax"), TOTAL("Total")
    }
}
