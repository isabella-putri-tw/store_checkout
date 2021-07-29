package validators

import exceptions.ItemException
import extensions.isElementEmpty

private val DECIMAL_REGEX = "^\\d*\\.?\\d{1,2}\$".toRegex()
private val INT_REGEX = "^\\d+\$".toRegex()

fun String.validateRawItem() {
    if (this.isEmpty())
        throw ItemException("Item shouldn't be empty")
    val splittedInput = this.split("|")

    if (!INT_REGEX.matches(splittedInput[0]))
        throw ItemException("Quantity should be a rounded number")

    validateEmptyField(splittedInput, 1, "Name")
    validateEmptyField(splittedInput, 2, "Price")

    if (!DECIMAL_REGEX.matches(splittedInput[2]))
        throw ItemException("Price should be a number")
}

private fun validateEmptyField(splittedInput: List<String>, index: Int, fieldName: String) {
    if (splittedInput.isElementEmpty(index))
        throw ItemException("$fieldName shouldn't be empty")
}