package starships

import java.time.LocalDate

data class Starship(
    val serialNumber: String,
    val name: String,
    val location: String,
)

data class StarshipRecord(
    val starship: Starship,
    val currentDate: LocalDate,
    val expirationDate: LocalDate?
)