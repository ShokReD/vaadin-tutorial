package name.shokred.demo.vaadin.form

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.data.binder.PropertyId
import name.shokred.demo.vaadin.constant.CustomerStatus
import name.shokred.demo.vaadin.domain.Customer
import name.shokred.demo.vaadin.service.CustomerService
import name.shokred.demo.vaadin.view.MainView

class CustomerForm(private val mainView: MainView) : FormLayout() {
    @PropertyId("firstName")
    private val firstName = TextField("First name")

    @PropertyId("lastName")
    private val lastName = TextField("Last name")

    @PropertyId("status")
    private val status = ComboBox<CustomerStatus>("Status")

    @PropertyId("birthDate")
    private val birthDate = DatePicker("Birthdate")

    private val save = Button("Save")
    private val delete = Button("Delete")

    private val binder = Binder(Customer::class.java)
    private val service = CustomerService.INSTANCE

    init {
        status.setItems(CustomerStatus.values().asList())

        val buttons = HorizontalLayout(save, delete)
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        add(firstName, lastName, status, birthDate, buttons)

        binder.bean = Customer()
        binder.bindInstanceFields(this)

        save.addClickListener { save() }
        delete.addClickListener { delete() }
    }

    fun setCustomer(entry: Customer?) {
        binder.bean = entry

        if (entry == null) {
            isVisible = false
        } else {
            isVisible = true
            firstName.focus()
        }
    }

    private fun save() {
        val customer = binder.bean
        service.save(customer)
        mainView.updateList()
        setCustomer(null)
    }

    private fun delete() {
        val customer = binder.bean
        service.delete(customer)
        mainView.updateList()
        setCustomer(null)
    }
}