package name.shokred.demo.vaadin.view

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import name.shokred.demo.vaadin.domain.Customer
import name.shokred.demo.vaadin.service.CustomerService

@Route
class MainView() : VerticalLayout() {
    private val service = CustomerService.INSTANCE
    private val grid = Grid(Customer::class.java)

    init {
        grid.setColumns("firstName", "lastName", "status")
        add(grid)
        setSizeFull()

        updateList()
    }

    private fun updateList() {
        grid.setItems(service.findAll())
    }
}