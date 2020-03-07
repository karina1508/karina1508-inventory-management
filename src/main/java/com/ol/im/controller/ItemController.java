package com.ol.im.controller;

import com.ol.im.dto.ItemDto;
import com.ol.im.exception.EntityNotFoundException;
import com.ol.im.service.ItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "get all items")
    @GetMapping(value = "/items")
    private ResponseEntity<List<ItemDto>> getAllItems() {
        return ResponseEntity.ok().body(itemService.getAllItems());
    }

    @ApiOperation(value = "get item by item number")
    @GetMapping("/item-by-number/{itemNumber}")
    private ResponseEntity<ItemDto> getItemByItemNumber(@PathVariable Long itemNumber) throws EntityNotFoundException {
        return ResponseEntity.ok().body(itemService.getItemByItemNumber(itemNumber));
    }

    @ApiOperation(value = "withdrawal item to stock")
    @PutMapping("/withdrawal-item/{itemNumber}/{amount}")
    private ResponseEntity<ItemDto> withdrawalItemToStock(@PathVariable Long itemNumber, @PathVariable Double amount) throws EntityNotFoundException {
        return ResponseEntity.ok().body(itemService.changeAmountOfItem(itemNumber, amount, "withdrawal"));
    }

    @ApiOperation(value = "deposit item to stock")
    @PutMapping("/deposit-item/{itemNumber}/{amount}")
    private ResponseEntity<ItemDto> depositItemToStock(@PathVariable Long itemNumber, @PathVariable Double amount) throws EntityNotFoundException {
        return ResponseEntity.ok().body(itemService.changeAmountOfItem(itemNumber, amount, "deposit"));
    }

    @ApiOperation(value = "add item to stock")
    @PostMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ItemDto> addItem(@RequestBody ItemDto itemDto) {
        return ResponseEntity.ok().body(itemService.addItem(itemDto));
    }

    @ApiOperation(value = "delete item from stock")
    @DeleteMapping("/item/{itemNumber}")
    private ResponseEntity<ItemDto> deleteItem(@PathVariable Long itemNumber) throws EntityNotFoundException {
        return ResponseEntity.ok().body(itemService.deleteItem(itemNumber));
    }

}
