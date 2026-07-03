package academy.nouri.nimbus.presentation.utils.extensions

import java.time.format.DateTimeFormatter

val dateFormatter: DateTimeFormatter? =
    DateTimeFormatter.ofPattern("EEEE, dd MMMM HH:mm")