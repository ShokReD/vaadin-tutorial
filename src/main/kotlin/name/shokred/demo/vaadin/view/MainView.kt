package name.shokred.demo.vaadin.view

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import com.vaadin.flow.router.Route
import name.shokred.demo.vaadin.domain.Customer
import name.shokred.demo.vaadin.service.CustomerService

@Route
class MainView() : VerticalLayout() {
    private val service = CustomerService.INSTANCE
    private val grid = Grid(Customer::class.java)
    private val filterText = TextField()

    init {
        filterText.placeholder = "Filter by name..."
        filterText.isClearButtonVisible = true
        filterText.valueChangeMode = ValueChangeMode.EAGER
        filterText.addValueChangeListener {
            updateList()
        }

        grid.setColumns("firstName", "lastName", "status")

        add(filterText, grid)

        setSizeFull()

        updateList()
    }

    private fun updateList() {
        grid.setItems(service.findAll(filterText.value))
    }
}