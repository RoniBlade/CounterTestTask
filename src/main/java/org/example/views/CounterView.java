package org.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;
import org.example.models.Counter;
import org.example.repos.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class CounterView extends VerticalLayout {
    private CounterRepository counterRepository;
    private Binder<Counter> binder;
    private IntegerField valueField;

    @Autowired
    public CounterView(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;

        binder = new Binder<>(Counter.class);

        valueField = new IntegerField("Значение");
        valueField.setMin(0);

        binder.forField(valueField)
            .bind(Counter::getCounterValue, Counter::setCounterValue);

        Button incrementButton = new Button("Увеличить", event -> {
            int currentValue = valueField.getValue() + 1;
            valueField.setValue(currentValue);
            saveCounterValue(currentValue);
        });

        add(valueField, incrementButton);

        loadCounterValue();
    }

    private void loadCounterValue() {
        Counter counter = counterRepository.findById(1L).orElseGet(() -> {
            Counter newCounter = new Counter();
            newCounter.setCounterValue(0); // Устанавливаем дефолтное значение
            return newCounter;
        });
        binder.setBean(counter);
    }

    private void saveCounterValue(int value) {
        Counter counter = binder.getBean();
        counter.setCounterValue(value);
        counterRepository.save(counter);
        Notification.show("Значение сохранено в БД.");
    }
}