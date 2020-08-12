package com.infosys.api.order.repository;

import com.infosys.api.order.aggregate.Cart;
import com.infosys.api.order.queries.GetCartQuery;
import org.axonframework.modelling.command.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class CartProjector {

	private final Repository<Cart> repository;

	public CartProjector(Repository<Cart> repository) {
		this.repository = repository;
	}

	@QueryHandler
	public Cart getCart(GetCartQuery query) throws InterruptedException, ExecutionException {
		CompletableFuture<Cart> future = new CompletableFuture<Cart>();
		repository.load("" + query.getCartIdentifier()).execute(future::complete);
		return future.get();
	}

}
