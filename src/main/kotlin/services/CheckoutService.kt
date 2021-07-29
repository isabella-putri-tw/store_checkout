package services

import com.jakewharton.picnic.BorderStyle
import com.jakewharton.picnic.TextAlignment
import com.jakewharton.picnic.renderText
import com.jakewharton.picnic.table
import exceptions.ItemException
import model.Item
import model.Receipt
import org.apache.commons.lang3.StringUtils
import java.io.BufferedReader
import java.io.InputStreamReader

class CheckoutService {
    var reader = BufferedReader(InputStreamReader(System.`in`))
    fun checkout() {
        welcome()
        val items = enterItems()
        printReceipt(ReceiptCalculator.calculate(items))
    }

    private fun printReceipt(receipt: Receipt) {
        println("${StringUtils.center(" RECEIPT ", 50, "-")}\n")
        println(
            table {
                style {
                    borderStyle = BorderStyle.Solid
                }
                cellStyle {
                    paddingLeft = 1
                    paddingRight = 1
                    borderLeft = true
                    borderRight = true
                }
                header {
                    cellStyle {
                        border = true
                        alignment = TextAlignment.MiddleLeft
                    }
                    row("Qty", "Name", "Price", "Tax", "Total")
                }
                body {
                    receipt.items.forEach {
                        row{
                            cell(it.quantity)
                            cell(it.name)
                            cell(it.price.toPlainString())
                            cell(it.tax.toPlainString())
                            cell(it.total) {
                                alignment = TextAlignment.MiddleRight
                            }
                        }
                    }
                }
                footer {
                    cellStyle {
                        border = true
                        alignment = TextAlignment.MiddleRight
                    }
                    row {
                        cell("Sales tax") {
                            columnSpan = 4
                        }
                        cell("$ ${receipt.salesTax.toPlainString()}") {
                            alignment = TextAlignment.MiddleRight
                        }
                    }
                    row {
                        cell("Total") {
                            columnSpan = 4
                        }
                        cell("$ ${receipt.total.toPlainString()}") {
                            alignment = TextAlignment.MiddleRight
                        }
                    }
                }
            }.renderText()
        )
    }

    private fun enterItems(): List<Item> {
        var items = mutableListOf<Item>()
        var rawItem = ""
        do {
            try {
                println("What item do you buy (input `-` to stop entering item)?")
                rawItem = reader.readLine()
                if (rawItem != "-")
                    items.add(Item.build(rawItem))
            } catch (ex: ItemException) {
                println(ex.message)
            }
        } while (rawItem != "-")

        println("${StringUtils.center(" end of items ", 50, "-")}\n")
        return items.toList()
    }

    private fun welcome() {
        println("Welcome to checkout service!")
        println("To input item, please follow this format: `qty|name|price_for_one`, e.g. `2|eggs|1`\n")
    }
}