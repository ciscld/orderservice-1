package com.infosys.api.order.events.listeners;

import com.infosys.api.order.events.ItemCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class ItemsCreatedEventListener {

    @Value("${inventory.url}")
    private String inventoryUrl;


    @EventHandler
    public void onEvent(ItemCreatedEvent event) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON );
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        restTemplate.exchange(inventoryUrl, HttpMethod.POST, entity, String.class);

        //Raise item reserve command.
        System.out.println("Received ItemAddedEvent id:" + event.getItemName() + " on thread named "
                + Thread.currentThread().getName());
    }
}
