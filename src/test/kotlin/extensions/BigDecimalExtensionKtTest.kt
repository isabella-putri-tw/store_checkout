package extensions

import assertk.assertThat
import assertk.assertions.isEqualByComparingTo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Nested
import java.math.BigDecimal

internal class BigDecimalExtensionKtTest {

    @Nested
    inner class RoundUp() {
        @Test
        fun `should not round when divisible by 5 cents`() {
            assertThat(BigDecimal("1").roundUp(BigDecimal("0.05"))).isEqualByComparingTo(BigDecimal("1"))
            assertThat(BigDecimal("1.05").roundUp(BigDecimal("0.05"))).isEqualByComparingTo(BigDecimal("1.05"))
            assertThat(BigDecimal("2.1").roundUp(BigDecimal("0.05"))).isEqualByComparingTo(BigDecimal("2.1"))
        }

        @Test
        fun `should round up when not divisible by 5 cents`() {
            assertThat(BigDecimal("2.51").roundUp(BigDecimal("0.05"))).isEqualByComparingTo(BigDecimal("2.55"))
            assertThat(BigDecimal("5.481912").roundUp(BigDecimal("0.05"))).isEqualByComparingTo(BigDecimal("5.50"))
        }

        @Test
        fun `should not round up when divisible by 3 dollar`() {
            assertThat(BigDecimal("3").roundUp(BigDecimal("3"))).isEqualByComparingTo(BigDecimal("3"))
            assertThat(BigDecimal("9").roundUp(BigDecimal("3"))).isEqualByComparingTo(BigDecimal("9"))
        }

        @Test
        fun `should round up when not divisible by 3 dollar`() {
            assertThat(BigDecimal("1").roundUp(BigDecimal("3"))).isEqualByComparingTo(BigDecimal("3"))
            assertThat(BigDecimal("7.234").roundUp(BigDecimal("3"))).isEqualByComparingTo(BigDecimal("9"))
        }
    }

}