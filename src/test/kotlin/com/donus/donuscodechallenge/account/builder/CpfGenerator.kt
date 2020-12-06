package com.donus.donuscodechallenge.account.builder

fun cpf(comPontos: Boolean): String {
    val n = 9
    val n1: Int = randomiza(n)
    val n2: Int = randomiza(n)
    val n3: Int = randomiza(n)
    val n4: Int = randomiza(n)
    val n5: Int = randomiza(n)
    val n6: Int = randomiza(n)
    val n7: Int = randomiza(n)
    val n8: Int = randomiza(n)
    val n9: Int = randomiza(n)
    var d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10
    d1 = 11 - mod(d1, 11)
    if (d1 >= 10) d1 = 0
    var d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11
    d2 = 11 - mod(d2, 11)
    var retorno: String? = null
    if (d2 >= 10) d2 = 0
    retorno = ""
    retorno = if (comPontos) "$n1$n2$n3.$n4$n5$n6.$n7$n8$n9-$d1$d2" else "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2
    return retorno
}
private fun mod(dividendo: Int, divisor: Int): Int {
    return Math.round(dividendo - Math.floor((dividendo / divisor).toDouble()) * divisor).toInt()
}

private fun randomiza(n: Int): Int {
    return (Math.random() * n).toInt()
}
