package name.shokred.demo.vaadin.domain

import name.shokred.demo.vaadin.constant.CustomerStatus
import java.time.LocalDate

data class Customer(val id: Long = nextId++,
                    var firstName: String = "",
                    var lastName: String = "",
                    var birthDate: LocalDate? = null,
                    var status: CustomerStatus? = null,
                    var email: String = "") {

    companion object {
        var nextId: Long = 0L
    }

    override fun toString(): String {
        return "$firstName $lastName"
    }

    constructor(fullName: String) : this() {
        val split = fullName.split(" ")
        this.firstName = split[0]
        this.lastName = split[1]
    }
}