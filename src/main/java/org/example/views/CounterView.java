package org.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import org.example.models.Counter;
import org.example.repos.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class CounterView extends VerticalLayout {
    private CounterRepository counterRepository;
    private Span valueSpan;

    @Autowired
    public CounterView(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;

        Counter counter = getCounterFromDatabase(); // Получить объект Counter из базы данных

        valueSpan = new Span(String.valueOf(counter.getCounterValue()));

        Button incrementButton = new Button("Увеличить", event -> {
            int currentValue = counter.getCounterValue();
            counter.setCounterValue(currentValue + 1);
            counterRepository.save(counter);
            valueSpan.setText(String.valueOf(currentValue + 1));
            Notification.show("Значение сохранено в БД.");
        });

        add(valueSpan, incrementButton);
    }

    private Counter getCounterFromDatabase() {
        // Получить объект Counter из базы данных или создать новый объект с дефолтным значением
        return counterRepository.findById(1L).orElse(new Counter());
    }
}
