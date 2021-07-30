package services

import model.Item
import model.Receipt
import model.ReceiptItem
import java.math.BigDecimal

class ReceiptCalculator {
    companion object {
        fun calculate(items:List<Item>): Receipt {
            var total = BigDecimal.ZERO
            var salesTax = BigDecimal.ZERO
            val receiptItems = mutableListOf<ReceiptItem>()
            items.forEach { item ->
                val receiptItem = ReceiptItem.builder(item)
                salesTax = salesTax.add(receiptItem.tax.totalTax())
                total = total.add(receiptItem.total)
                receiptItems.add(receiptItem)
            }

            return Receipt(receiptItems, salesTax, total)
        }
    }
}