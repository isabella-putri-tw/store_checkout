package services

import com.jakewharton.picnic.renderText
import exceptions.ItemException
import extensions.buildReceiptTable
import model.Item
import model.Receipt
import org.apache.commons.lang3.StringUtils
import java.io.BufferedReader
import java.io.InputStreamReader

class CheckoutService {
    var reader = BufferedReader(InputStreamReader(System.`in`))

    companion object {
        const val NO_MORE_ITEM = "-"
        const val END_OF_ITEMS = " end of items "
        const val WELCOME_MESSAGE = "Welcome to checkout service!"
        const val INFO_MESSAGE = "To input item, please follow this format: `qty|name|price_for_one`, e.g. `2|eggs|1`\n"
        const val ASK_FOR_ITEM = "What item do you buy (input `-` to stop entering item)?"
    }
    fun checkout() {
        welcome()
        val items = enterItems()
        printReceipt(ReceiptCalculator.calculate(items))
    }

    private fun printReceipt(receipt: Receipt) {
        println("${StringUtils.center(" RECEIPT ", 50, "-")}\n")
        println(receipt.buildReceiptTable().renderText())
    }

    private fun enterItems(): List<Item> {
        val items = mutableListOf<Item>()
        var rawItem = ""
        do {
            try {
                println(ASK_FOR_ITEM)
                rawItem = reader.readLine()
                if (rawItem != NO_MORE_ITEM)
                    items.add(Item.build(rawItem))
            } catch (ex: ItemException) {
                println(ex.message)
            }
        } while (rawItem != NO_MORE_ITEM)

        printEndOfItems()
        return items.toList()
    }

    private fun printEndOfItems() {
        println("${StringUtils.center(END_OF_ITEMS, 50, "-")}\n")
    }

    private fun welcome() {
        println(WELCOME_MESSAGE)
        println(INFO_MESSAGE)
    }
}