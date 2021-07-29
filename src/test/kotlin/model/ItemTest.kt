package model

import assertk.assertThat
import assertk.assertions.*
import exceptions.ItemException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class ItemTest {
    @Nested
    inner class Build() {
        @Test
        fun `should throw exception if raw input is empty`() {
            assertThat {
                Item.build("")
            }.isFailure().isInstanceOf(ItemException::class)
                .hasMessage("Item shouldn't be empty")
        }

        @Test
        fun `should throw exception if quantity is not a rounded number`() {
            assertThat {
                Item.build("e")
            }.isFailure().isInstanceOf(ItemException::class)
                .hasMessage("Quantity should be a rounded number")

            assertThat {
                Item.build("1.2")
            }.isFailure().isInstanceOf(ItemException::class)
                .hasMessage("Quantity should be a rounded number")
        }

        @Test
        fun `should throw exception if name is empty`() {
            assertThat {
                Item.build("12")
            }.isFailure().isInstanceOf(ItemException::class)
                .hasMessage("Name shouldn't be empty")

            assertThat {
                Item.build("2|")
            }.isFailure().isInstanceOf(ItemException::class)
                .hasMessage("Name shouldn't be empty")
        }

        @Test
        fun `should throw exception if price is empty`() {
            assertThat {
                Item.build("12|book")
            }.isFailure().isInstanceOf(ItemException::class)
                .hasMessage("Price shouldn't be empty")

            assertThat {
                Item.build("2|pills|")
            }.isFailure().isInstanceOf(ItemException::class)
                .hasMessage("Price shouldn't be empty")
        }

        @Test
        fun `should throw exception if price not a number`() {
            assertThat {
                Item.build("12|book|a")
            }.isFailure().isInstanceOf(ItemException::class)
                .hasMessage("Price should be a number")

        }

        @Test
        fun `should build item when format is correct`() {
            val actualItem1 = Item.build("3|book of life|4")

            assertThat(actualItem1.quantity).isEqualTo(3)
            assertThat(actualItem1.name).isEqualTo("book of life")
            assertThat(actualItem1.price).isEqualByComparingTo(BigDecimal("4"))

            val actualItem2 = Item.build("12|paper|0.12")

            assertThat(actualItem2.quantity).isEqualTo(12)
            assertThat(actualItem2.name).isEqualTo("paper")
            assertThat(actualItem2.price).isEqualByComparingTo(BigDecimal("0.12"))
        }
    }
}