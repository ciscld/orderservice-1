package com.infosys.api.order.repository;

import com.infosys.api.order.events.ItemCreatedEvent;
import com.infosys.api.order.models.ItemsBean;
import com.infosys.api.order.queries.GetItemsQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ItemRepositoryProjector {
/*
	private final ItemRepository itemRepository;

	public ItemRepositoryProjector(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@EventHandler
	public void addBook(ItemCreatedEvent event) throws Exception {
		ItemEntity entity = new ItemEntity(
			event.getItemName(),
				event.getCartIdentifier(),
				event.getPrice(),
				event.getQuantity()
		);
		//entity.setItemName(event.getItemName());
		itemRepository.save(entity);
	}

	@QueryHandler
	public List<ItemsBean> getItems(GetItemsQuery query) {
		return itemRepository.findByCartIdentifier(query.getCartIdentifier()).stream().map(toItem()).collect(Collectors.toList());
	}

	private Function<ItemEntity, ItemsBean> toItem() {
		return e -> {
			ItemsBean itemsBean = new ItemsBean( e.getItemName(), e.getPrice(), e.getQuantity());
			return itemsBean;
		};

 */
}
