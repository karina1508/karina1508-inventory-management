package com.ol.im.service;

import com.ol.im.domain.ItemEntity;
import com.ol.im.dto.ItemDto;
import com.ol.im.exception.EntityNotFoundException;
import com.ol.im.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<ItemDto> getAllItems() {
        logger.debug("get all items");
        List<ItemEntity> itemsEntity = itemRepository.findAll();
        return listEntityToListDto(itemsEntity);
    }

    public ItemDto getItemByItemNumber(Long itemNumber) throws EntityNotFoundException {
        logger.debug("get item by item number");
        return itemRepository.findById(itemNumber).map(ItemEntity::toDto)
                .orElseThrow(() -> new EntityNotFoundException("item not found by number " + itemNumber));
    }

    public ItemDto changeAmountOfItem(Long itemNumber, Double amount, String typeOfChange) throws EntityNotFoundException {
        logger.debug("change Amount Of Item");
        ItemEntity itemEntity = itemRepository.findById(itemNumber)
                .orElseThrow(() -> new EntityNotFoundException("item not found by number " + itemNumber));

        if (typeOfChange.toLowerCase().equals("deposit")) {
            itemEntity.setAmount(itemEntity.getAmount() + amount);
        } else {
            itemEntity.setAmount(itemEntity.getAmount() - amount);
        }
        itemEntity = itemRepository.save(itemEntity);
        return itemEntity.toDto();
    }

    public ItemDto addItem(ItemDto itemDto) {
        logger.debug("add item");
        ItemEntity item = itemRepository.save(itemDto.toEntity());
        return item.toDto();
    }

    public ItemDto deleteItem(Long itemNumber) throws EntityNotFoundException {
        logger.debug("delete item");
        ItemDto itemDto = getItemByItemNumber(itemNumber);
        itemRepository.deleteById(itemDto.getItemNo());
        return itemDto;
    }


    private List<ItemDto> listEntityToListDto(List<ItemEntity> itemsEntity) {
        logger.debug("list entity to list dto");
        return itemsEntity.stream().map(ItemEntity::toDto).collect(Collectors.toList());
    }
}
