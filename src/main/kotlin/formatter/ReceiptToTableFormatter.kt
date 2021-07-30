package extensions

import com.jakewharton.picnic.*
import model.Receipt
import java.math.BigDecimal

enum class ReceiptTableField(val displayName: String) {
    QTY("Qty"), NAME("Name"), PRICE("Price"), TAX("Tax"), LOCAL("Local"), IMPORT("Import"),
    SALES_TAX("Sales tax"), TOTAL("Total")
}

fun Receipt.buildTable() = table {
    style {
        borderStyle = BorderStyle.Solid
    }
    cellStyle {
        paddingLeft = 1
        paddingRight = 1
        borderLeft = true
        borderRight = true
    }
    buildHeader()
    buildBody(this, this@buildTable)
    buildFooter(this, this@buildTable)
}

private fun buildFooter(tableDsl: TableDsl, receipt: Receipt) {
    tableDsl.footer {
        cellStyle {
            border = true
            alignment = TextAlignment.MiddleRight
        }
        buildFooterRow(this, ReceiptTableField.SALES_TAX.displayName, receipt.salesTax)
        buildFooterRow(this, ReceiptTableField.TOTAL.displayName, receipt.total)
    }
}

private fun buildBody(tableDsl: TableDsl, receipt: Receipt) {
    tableDsl.body {
        receipt.items.forEach {
            row {
                cell(it.quantity)
                cell(it.name)
                cell(it.price.toPlainString())
                cell(it.tax.local.toPlainString())
                cell(it.tax.import.toPlainString())
                cell(it.total) {
                    alignment = TextAlignment.MiddleRight
                }
            }
        }
    }
}

private fun TableDsl.buildHeader() {
    header {
        cellStyle {
            border = true
            alignment = TextAlignment.MiddleLeft
        }
        row {
            cell(ReceiptTableField.QTY.displayName) { rowSpan = 2 }
            cell(ReceiptTableField.NAME.displayName) { rowSpan = 2 }
            cell(ReceiptTableField.PRICE.displayName) { rowSpan = 2 }
            cell(ReceiptTableField.TAX.displayName) { columnSpan = 2 }
            cell(ReceiptTableField.TOTAL.displayName) { rowSpan = 2 }
        }
        row(ReceiptTableField.LOCAL.displayName, ReceiptTableField.IMPORT.displayName)
    }
}

private fun buildFooterRow(tableSectionDsl: TableSectionDsl, field: String, value: BigDecimal) {
    tableSectionDsl.row {
        cell(field) {
            columnSpan = 5
        }
        cell("$ ${value.toPlainString()}") {
            alignment = TextAlignment.MiddleRight
        }
    }
}