package extensions

fun List<String>.isElementEmpty(index: Int) = this.getOrNull(index).isNullOrEmpty()