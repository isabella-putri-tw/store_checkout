package utils

fun List<String>.isElementEmpty(index: Int) = this.getOrNull(index).isNullOrEmpty()