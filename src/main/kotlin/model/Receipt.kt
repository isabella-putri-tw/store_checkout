package model

import java.math.BigDecimal

data class Receipt(val items: List<ReceiptItem>, val salesTax: BigDecimal, val total: BigDecimal) {

}
