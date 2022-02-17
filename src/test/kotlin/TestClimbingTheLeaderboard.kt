package tests

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

/**
 * Climbing the Leaderboard
 * https://www.hackerrank.com/challenges/climbing-the-leaderboard
 */
class TestClimbingTheLeaderboard {
    private fun splitToNumbers(input: String): Array<Int> = input.trim().split(" ").map { it.toInt() }.toTypedArray()

    @Test
    fun mainTest() {
        val ranked = this.splitToNumbers("100 100 50 40 40 20 10")
        val player = this.splitToNumbers("5 25 50 120")
        val checked = this.splitToNumbers("6 4 2 1")
        val result = this.climbingLeaderboard(ranked, player)
        assertContentEquals(
            checked,
            result
        )
    }

    /**
     * Модифицированный бинарный поиск
     * Модификация заключается в том что при не нахождении элемента возвращается не -1, а ближайший элемент
     */
    fun binarySearch(value: Int, list: Array<Int>, min: Int, max: Int): Int {
        return if (max < min) {
            max
        } else {
            val index = (min + max) / 2
            if (list[index] > value) {
                binarySearch(value, list, index + 1, max)
            } else if (list[index] < value) {
                binarySearch(value, list, min, index - 1)
            } else {
                index
            }
        }
    }


    fun climbingLeaderboard(ranked: Array<Int>, player: Array<Int>): Array<Int> {
        // Так как число результатов = кол-ву элементов в массиве player то заранее создаем массив и заполняем нулями
        val result: Array<Int> = Array(player.size) { 0 }
        // Берем только уникальные значения
        val uniq = ranked.distinct().toTypedArray()
        for (i in 0..player.lastIndex) {
            // Находим ближайший индекс
            val index = binarySearch(player[i], uniq, 0, uniq.lastIndex)
            // Если индекс вышел за пределы массива, то значит это первое место
            if (index < 0) {
                result[i] = 1
                continue
            }
            // Иначе берем значение и сравниваем
            val uniqValue = uniq[index]
            if (uniqValue == player[i]) {
                // Если значения равные значит будет то же самое место
                result[i] = index + 1
            } else if (uniqValue < player[i]) {
                // Если значение игрока больше, то он заменит ближайшего сверху в рейтинге
                result[i] = index
            } else {
                // Иначе он будет ближайшего снизу в рейтинге
                result[i] = index + 2
            }
        }
        return result
    }
}