package com.ua.leatherbags.controller;

import com.ua.leatherbags.dao.PageWrapper;
import com.ua.leatherbags.data.Bag;
import com.ua.leatherbags.data.OrderStatus;
import com.ua.leatherbags.service.BagService;
import com.ua.leatherbags.service.TelegramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bags")
@RequiredArgsConstructor
@Tag(
		name = "Bag",
		description = "Bag Order info"
)
public class BagController {
	private final BagService bagService;
	private final TelegramService telegramService;

	@GetMapping
	@Operation(
			description = "All bag orders info with pagination and filtering by status. All status codes can be retrieved using /statuses",
			summary = "All bag orders info",
			parameters = {
					@Parameter(
							in = ParameterIn.QUERY,
							name = "status",
							description = "Status value of orders to be retrieved",
							example = "1"),
					@Parameter(
							in = ParameterIn.QUERY,
							name = "page",
							description = "Page number, starts from 1",
							example = "1"),
					@Parameter(
							in = ParameterIn.QUERY,
							name = "size",
							description = "Size of a page",
							example = "15")
			},
			responses = {
					@ApiResponse(
							description = "Page of bag info",
							responseCode = "200",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(implementation = PageWrapper.class)
							)
					),
					@ApiResponse(
							responseCode = "403",
							ref = "#/components/responses/403Response"
					)
			}
	)
	public PageWrapper<Bag> getBags(@RequestParam byte status,
									@RequestParam int page,
									@RequestParam int size) {
		return bagService.findBagsByStatus(status, page, size);
	}

	@PostMapping
	@Operation(
			description = "Request a bag",
			summary = "Bag order request",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Order's bag and customer info",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Bag.class)
					)
			),
			responses = {
					@ApiResponse(
							description = "Created Order",
							responseCode = "200",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(implementation = Bag.class)
							)
					),
					@ApiResponse(
							responseCode = "403",
							ref = "#/components/responses/403Response"
					)
			}
	)
	@SecurityRequirements()
	public Bag addBag(@RequestBody Bag bag) {
		bag = bagService.saveBag(bag);
		telegramService.sendMessage(bag);
		return bag;
	}

	@PutMapping("/proceed")
	@Operation(
			description = "Proceed a certain order by id",
			summary = "Proceed order",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Id of a request to proceed",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Integer.class, example = "1")
					)
			),
			responses = {
					@ApiResponse(
							description = "Proceeded order",
							responseCode = "200",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(implementation = Bag.class)
							)
					),
					@ApiResponse(
							responseCode = "403",
							ref = "#/components/responses/403Response"
					)
			}
	)
	public Bag proceedBag(@RequestBody long id) {
		return bagService.proceedOrder(id);
	}

	@PutMapping("/cancel")
	@Operation(
			description = "Cancel a certain order by id",
			summary = "Cancel order",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Id of a request to canceled",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Integer.class, example = "1")
					)
			),
			responses = {
					@ApiResponse(
							description = "Canceled order",
							responseCode = "200",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(implementation = Bag.class)
							)
					),
					@ApiResponse(
							responseCode = "403",
							ref = "#/components/responses/403Response"
					)
			}
	)
	public Bag cancelBag(@RequestBody long id) {
		return bagService.cancelOrder(id);
	}

	@GetMapping("/statuses")
	@Operation(
			description = "All order statuses",
			summary = "All order statuses",
			responses = {
					@ApiResponse(
							description = "All order statuses",
							responseCode = "200",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(implementation = OrderStatus[].class)
							)
					),
					@ApiResponse(
							responseCode = "403",
							ref = "#/components/responses/403Response"
					)
			}
	)
	public OrderStatus[] getOrderStatuses() {
		return OrderStatus.values();
	}
}
