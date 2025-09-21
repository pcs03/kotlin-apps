package nl.pcstet.scratch

fun main() {
    val morningNotification = 51
    val eveningNotification = 135

    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)
}


fun printNotificationSummary(numberOfMessages: Int) {
    println(if (numberOfMessages < 100) {
        "You have $numberOfMessages messages."
    } else {
        "Your phone is blowing up! You have 99+ messages."
    })
}
