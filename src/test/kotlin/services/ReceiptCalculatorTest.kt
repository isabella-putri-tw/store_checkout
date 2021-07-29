package services

import assertk.assertThat
import assertk.assertions.hasSameSizeAs
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isEqualByComparingTo
import assertk.assertions.isEqualTo
import model.Item
import model.ReceiptItem
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class ReceiptCalculatorTest {
    @Nested
    inner class Calculate() {
        @Test
        fun `books, food and medical products should have no tax`() {
            val items = listOf<Item>(
                Item(1, "book", BigDecimal("12.49")),
                Item(1, "chocolate bar", BigDecimal("0.85")),
                Item(2, "packet of headache pills", BigDecimal("9.75"))
            )

            val actualReceipt = ReceiptCalculator.calculate(items)

            assertThat(actualReceipt.items).hasSameSizeAs(items)

            assertThat(actualReceipt.items[0].tax).isEqualByComparingTo(BigDecimal.ZERO)
            assertThat(actualReceipt.items[0].total).isEqualByComparingTo(BigDecimal("12.49"))

            assertThat(actualReceipt.items[1].tax).isEqualByComparingTo(BigDecimal.ZERO)
            assertThat(actualReceipt.items[1].total).isEqualByComparingTo(BigDecimal("0.85"))

            assertThat(actualReceipt.items[2].tax).isEqualByComparingTo(BigDecimal.ZERO)
            assertThat(actualReceipt.items[2].total).isEqualByComparingTo(BigDecimal("19.50"))

            assertThat(actualReceipt.salesTax).isEqualByComparingTo(BigDecimal.ZERO)
            assertThat(actualReceipt.total).isEqualByComparingTo(BigDecimal("32.84"))
        }

        @Test
        fun `other products should get 10 percent tax`() {
            val items = listOf(
                Item(3, "music CD", BigDecimal("10")),
            )

            val actualReceipt = ReceiptCalculator.calculate(items)

            assertThat(actualReceipt.items).hasSameSizeAs(items)

            assertThat(actualReceipt.items[0].tax).isEqualByComparingTo(BigDecimal("3"))
            assertThat(actualReceipt.items[0].total).isEqualByComparingTo(BigDecimal("33"))

            assertThat(actualReceipt.salesTax).isEqualByComparingTo(BigDecimal("3"))
            assertThat(actualReceipt.total).isEqualByComparingTo(BigDecimal("33"))
        }

        @Test
        fun `imported products should get additional 5 percent tax`() {
            val items = listOf(
                Item(5, "imported box of chocolates", BigDecimal("10")),
                Item(1, "imported bottle of perfume", BigDecimal("100")),
            )

            val actualReceipt = ReceiptCalculator.calculate(items)

            assertThat(actualReceipt.items).hasSameSizeAs(items)

            assertThat(actualReceipt.items[0].tax).isEqualByComparingTo(BigDecimal("2.5"))
            assertThat(actualReceipt.items[0].total).isEqualByComparingTo(BigDecimal("52.5"))

            assertThat(actualReceipt.items[1].tax).isEqualByComparingTo(BigDecimal("15"))
            assertThat(actualReceipt.items[1].total).isEqualByComparingTo(BigDecimal("115"))

            assertThat(actualReceipt.salesTax).isEqualByComparingTo(BigDecimal("17.5"))
            assertThat(actualReceipt.total).isEqualByComparingTo(BigDecimal("167.5"))
        }

        @Test
        fun `sales tax should be rounded up 5 cents`() {
            val items = listOf(
                Item(1, "imported bottle of perfume", BigDecimal("27.99")),
                Item(1, "bottle of perfume", BigDecimal("18.99")),
                Item(1, "packet of headache pills", BigDecimal("9.75")),
                Item(1, "imported box of chocolates", BigDecimal("11.25")),
            )

            val actualReceipt = ReceiptCalculator.calculate(items)

            assertThat(actualReceipt.items).hasSameSizeAs(items)

            assertThat(actualReceipt.items[0].tax).isEqualByComparingTo(BigDecimal("4.20"))
            assertThat(actualReceipt.items[0].total).isEqualByComparingTo(BigDecimal("32.19"))

            assertThat(actualReceipt.items[1].tax).isEqualByComparingTo(BigDecimal("1.90"))
            assertThat(actualReceipt.items[1].total).isEqualByComparingTo(BigDecimal("20.89"))

            assertThat(actualReceipt.items[2].tax).isEqualByComparingTo(BigDecimal.ZERO)
            assertThat(actualReceipt.items[2].total).isEqualByComparingTo(BigDecimal("9.75"))

            assertThat(actualReceipt.items[3].tax).isEqualByComparingTo(BigDecimal("0.6"))
            assertThat(actualReceipt.items[3].total).isEqualByComparingTo(BigDecimal("11.85"))

            assertThat(actualReceipt.salesTax).isEqualByComparingTo(BigDecimal("6.7"))
            assertThat(actualReceipt.total).isEqualByComparingTo(BigDecimal("74.68"))
        }
    }

}