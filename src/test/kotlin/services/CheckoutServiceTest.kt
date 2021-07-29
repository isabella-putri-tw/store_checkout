package services

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class CheckoutServiceTest {
    @Nested
    inner class Checkout() {
        private val standardOut = System.out
        private val outputStreamCaptor = ByteArrayOutputStream()

        @Mock
        lateinit var mockReader: BufferedReader

        lateinit var checkoutService: CheckoutService


        @BeforeEach
        fun setUp() {
            MockitoAnnotations.openMocks(this)
            System.setOut(PrintStream(outputStreamCaptor))
            checkoutService = CheckoutService()
            checkoutService.reader = mockReader
        }

        @AfterEach
        fun tearDown() {
            System.setOut(standardOut)
        }

        @Test
        fun `should print empty receipt if user doesn't input item `() {
            Mockito.`when`(mockReader.readLine()).thenReturn("-")
            checkoutService.checkout()

            assertThat(outputStreamCaptor.toString().trim()).isEqualTo(
                """
                    Welcome to checkout service!
                    To input item, please follow this format: `qty|name|price_for_one`, e.g. `2|eggs|1`
                    
                    What item do you buy (input `-` to stop entering item)?
                    ------------------ end of items ------------------
                    
                    -------------------- RECEIPT ---------------------
                    
                    ┌─────┬──────┬───────┬────────────────┬───────┐
                    │     │      │       │ Tax            │       │
                    │ Qty │ Name │ Price ├───────┬────────┤ Total │
                    │     │      │       │ Local │ Import │       │
                    ├─────┴──────┴───────┴───────┴────────┼───────┤
                    │                           Sales tax │   $ 0 │
                    ├─────────────────────────────────────┼───────┤
                    │                               Total │   $ 0 │
                    └─────────────────────────────────────┴───────┘
                    """.trimIndent().trimStart().trimEnd()
            )
        }


        @Test
        fun `should print receipt with an item if user input an item`() {
            Mockito.`when`(mockReader.readLine()).thenReturn("1|book|3.5", "-")
            checkoutService.checkout()

            assertThat(outputStreamCaptor.toString().trim()).isEqualTo(
                """
                    Welcome to checkout service!
                    To input item, please follow this format: `qty|name|price_for_one`, e.g. `2|eggs|1`
                    
                    What item do you buy (input `-` to stop entering item)?
                    What item do you buy (input `-` to stop entering item)?
                    ------------------ end of items ------------------
                    
                    -------------------- RECEIPT ---------------------
                    
                    ┌─────┬──────┬───────┬────────────────┬────────┐
                    │     │      │       │ Tax            │        │
                    │ Qty │ Name │ Price ├───────┬────────┤ Total  │
                    │     │      │       │ Local │ Import │        │
                    ├─────┼──────┼───────┼───────┼────────┼────────┤
                    │ 1   │ book │ 3.5   │ 0     │ 0      │   3.50 │
                    ├─────┴──────┴───────┴───────┴────────┼────────┤
                    │                           Sales tax │    $ 0 │
                    ├─────────────────────────────────────┼────────┤
                    │                               Total │ $ 3.50 │
                    └─────────────────────────────────────┴────────┘
                    """.trimIndent().trimStart().trimEnd()
            )
        }

        @Test
        fun `should ask to input item again if input is invalid`() {
            Mockito.`when`(mockReader.readLine()).thenReturn("Invalid input", "1|book|3.5", "-")
            checkoutService.checkout()

            assertThat(outputStreamCaptor.toString().trim()).isEqualTo(
                """
                    Welcome to checkout service!
                    To input item, please follow this format: `qty|name|price_for_one`, e.g. `2|eggs|1`
                    
                    What item do you buy (input `-` to stop entering item)?
                    Quantity should be a rounded number
                    What item do you buy (input `-` to stop entering item)?
                    What item do you buy (input `-` to stop entering item)?
                    ------------------ end of items ------------------
                    
                    -------------------- RECEIPT ---------------------
                    
                    ┌─────┬──────┬───────┬────────────────┬────────┐
                    │     │      │       │ Tax            │        │
                    │ Qty │ Name │ Price ├───────┬────────┤ Total  │
                    │     │      │       │ Local │ Import │        │
                    ├─────┼──────┼───────┼───────┼────────┼────────┤
                    │ 1   │ book │ 3.5   │ 0     │ 0      │   3.50 │
                    ├─────┴──────┴───────┴───────┴────────┼────────┤
                    │                           Sales tax │    $ 0 │
                    ├─────────────────────────────────────┼────────┤
                    │                               Total │ $ 3.50 │
                    └─────────────────────────────────────┴────────┘
                    """.trimIndent().trimStart().trimEnd()
            )
        }

        @Test
        fun `should print receipt with multiple items`() {
            Mockito.`when`(mockReader.readLine()).thenReturn(
                "1|imported bottle of perfume|27.99",
                "1|bottle of perfume|18.99",
                "1|packet of headache pills|9.75",
                "1|imported box of chocolates|11.25",
                "-"
            )
            checkoutService.checkout()

            assertThat(outputStreamCaptor.toString().trim()).isEqualTo(
                """
                    Welcome to checkout service!
                    To input item, please follow this format: `qty|name|price_for_one`, e.g. `2|eggs|1`
                    
                    What item do you buy (input `-` to stop entering item)?
                    What item do you buy (input `-` to stop entering item)?
                    What item do you buy (input `-` to stop entering item)?
                    What item do you buy (input `-` to stop entering item)?
                    What item do you buy (input `-` to stop entering item)?
                    ------------------ end of items ------------------
                    
                    -------------------- RECEIPT ---------------------
                    
                    ┌─────┬────────────────────────────┬───────┬────────────────┬─────────┐
                    │     │                            │       │ Tax            │         │
                    │ Qty │ Name                       │ Price ├───────┬────────┤ Total   │
                    │     │                            │       │ Local │ Import │         │
                    ├─────┼────────────────────────────┼───────┼───────┼────────┼─────────┤
                    │ 1   │ imported bottle of perfume │ 27.99 │ 2.80  │ 1.40   │   32.19 │
                    │ 1   │ bottle of perfume          │ 18.99 │ 1.90  │ 0      │   20.89 │
                    │ 1   │ packet of headache pills   │ 9.75  │ 0     │ 0      │    9.75 │
                    │ 1   │ imported box of chocolates │ 11.25 │ 0     │ 0.60   │   11.85 │
                    ├─────┴────────────────────────────┴───────┴───────┴────────┼─────────┤
                    │                                                 Sales tax │  $ 6.70 │
                    ├───────────────────────────────────────────────────────────┼─────────┤
                    │                                                     Total │ $ 74.68 │
                    └───────────────────────────────────────────────────────────┴─────────┘
                    """.trimIndent().trimStart().trimEnd()
            )
        }
    }
}