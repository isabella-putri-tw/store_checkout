package extensions

import com.jakewharton.picnic.*
import model.Receipt
import model.ReceiptItem
import java.math.BigDecimal


fun Receipt.buildReceiptTable() = table {
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
    buildBody(this, this@buildReceiptTable)
    buildFooter(this, this@buildReceiptTable)
}

private fun buildFooter(tableDsl: TableDsl, receipt: Receipt) {
    tableDsl.footer {
        cellStyle {
            border = true
            alignment = TextAlignment.MiddleRight
        }
        buildFooterRow(this, Receipt.Field.SALES_TAX.displayName, receipt.salesTax)
        buildFooterRow(this, Receipt.Field.TOTAL.displayName, receipt.total)
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
            cell(ReceiptItem.Field.QTY.displayName) { rowSpan = 2 }
            cell(ReceiptItem.Field.NAME.displayName) { rowSpan = 2 }
            cell(ReceiptItem.Field.PRICE.displayName) { rowSpan = 2 }
            cell(ReceiptItem.Field.TAX.displayName) { columnSpan = 2 }
            cell(ReceiptItem.Field.TOTAL.displayName) { rowSpan = 2 }
        }
        row(ReceiptItem.Field.LOCAL.displayName, ReceiptItem.Field.IMPORT.displayName)
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