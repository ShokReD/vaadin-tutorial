package name.shokred.demo.vaadin.view

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import com.vaadin.flow.router.Route
import name.shokred.demo.vaadin.domain.Customer
import name.shokred.demo.vaadin.form.CustomerForm
import name.shokred.demo.vaadin.service.CustomerService

@Route
class MainView : VerticalLayout() {
    private val service = CustomerService.INSTANCE
    private val grid = Grid(Customer::class.java)
    private val filterText = TextField()
    private val form = CustomerForm(this)
    private val addCustomer = Button("Add new customer")

    init {
        filterText.placeholder = "Filter by name..."
        filterText.isClearButtonVisible = true
        filterText.valueChangeMode = ValueChangeMode.EAGER
        filterText.addValueChangeListener {
            updateList()
        }
        addCustomer.addClickListener {
            grid.asSingleSelect().clear()
            form.setCustomer(Customer())
        }
        val toolbar = HorizontalLayout(filterText, addCustomer)

        grid.setColumns("firstName", "lastName", "status")
        grid.asSingleSelect().addValueChangeListener {
            form.setCustomer(grid.asSingleSelect().value)
        }
        grid.setSizeFull()

        val mainContent = HorizontalLayout(grid, form)
        mainContent.setSizeFull()

        add(toolbar, mainContent)

        setSizeFull()

        updateList()

        form.setCustomer(null)
    }

    fun updateList() {
        grid.setItems(service.findAll(filterText.value))
    }
}