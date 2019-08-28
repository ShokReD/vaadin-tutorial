package name.shokred.demo.vaadin.service

import name.shokred.demo.vaadin.constant.CustomerStatus
import name.shokred.demo.vaadin.domain.Customer
import java.lang.Integer.min
import java.time.LocalDate
import java.util.*
import kotlin.collections.HashMap

class CustomerService {
    companion object {
        val INSTANCE = CustomerService()

        init {
            INSTANCE.ensureTestData()
        }
    }

    private val contacts = HashMap<Long, Customer>()

    @Synchronized
    fun findAll(): List<Customer> {
        return findAll("")
    }

    @Synchronized
    fun findAll(filter: String): List<Customer> {
        return contacts
                .values
                .filter {
                    it.toString().contains(filter.toLowerCase(), true)
                }
                .sortedBy {
                    it.id
                }
                .toList()
    }

    @Synchronized
    fun findAll(filter: String, start: Int, maxResults: Int): List<Customer> {
        val list = findAll(filter)

        return list.subList(start, min(list.size, start + maxResults))
    }

    @Synchronized
    fun count(): Long {
        return contacts.size.toLong()
    }

    @Synchronized
    fun save(entry: Customer) {
        val copy = entry.copy()
        contacts[copy.id] = copy
    }

    @Synchronized
    fun delete(entry: Customer) {
        contacts.remove(entry.id)
    }

    private fun ensureTestData() {
        val r = Random(0)

        listOf("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                "Jaydan Jackson", "Bernard Nilsen")
                .map {
                    Customer(it)
                }
                .onEach {
                    it.status = CustomerStatus.values()[r.nextInt(CustomerStatus.values().size)]
                    it.birthDate = LocalDate.now().minusDays(r.nextInt(365 * 100).toLong())
                }
                .forEach {
                    save(it)
                }
    }
}