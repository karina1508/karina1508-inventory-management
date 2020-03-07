package com.ol.im.service;

import com.ol.im.domain.ItemEntity;
import com.ol.im.dto.ItemDto;
import com.ol.im.exception.EntityNotFoundException;
import com.ol.im.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    private ItemService itemService;
    private MockMvc mockMvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        itemService = new ItemService(itemRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(itemService).build();

    }

    @Test
    public void getAllItems() {
        ItemEntity item = new ItemEntity(2L, "firstItem", 25.0, "452353GF");
        List<ItemEntity> listOfItems = Arrays.asList(item);
        Mockito.when(itemRepository.findAll()).thenReturn(listOfItems);
        List<ItemDto> result = itemService.getAllItems();
        Assertions.assertThat(result.get(0).getItemNo()).isEqualTo(2L);
    }

    @Test
    public void getItemByItemNumber() throws EntityNotFoundException {
        ItemEntity item = new ItemEntity(2L, "firstItem", 25.0, "452353GF");
        Mockito.when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        ItemDto result = itemService.getItemByItemNumber(2L);
        Assertions.assertThat(result.getItemNo()).isEqualTo(2L);
    }

    @Test
    public void changeAmountOfItemDeposit() throws EntityNotFoundException {
        ItemEntity item = new ItemEntity(2L, "firstItem", 25.0, "452353GF");
        Mockito.when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        ItemEntity itemForSave = new ItemEntity(2L, "firstItem", 35.0, "452353GF");
        Mockito.when(itemRepository.save(itemForSave)).thenReturn(itemForSave);
        ItemDto result = itemService.changeAmountOfItem(2L, 10.0, "deposit");
        Assertions.assertThat(result.getAmount()).isEqualTo(35.0);
    }

    @Test
    public void changeAmountOfItemWithdrawal() throws EntityNotFoundException {
        ItemEntity item = new ItemEntity(2L, "firstItem", 25.0, "452353GF");
        Mockito.when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        ItemEntity itemForSave = new ItemEntity(2L, "firstItem", 15.0, "452353GF");
        Mockito.when(itemRepository.save(itemForSave)).thenReturn(itemForSave);
        ItemDto result = itemService.changeAmountOfItem(2L, 10.0, "withdrawal");
        Assertions.assertThat(result.getAmount()).isEqualTo(15.0);
    }

    @Test
    public void addItem() {
        ItemEntity item = new ItemEntity(2L, "firstItem", 25.0, "452353GF");
        ItemDto itemDto = item.toDto();
        Mockito.when(itemRepository.save(item)).thenReturn(item);
        ItemDto result = itemService.addItem(itemDto);
        Assertions.assertThat(result).isEqualTo(itemDto);
    }

    @Test
    public void deleteItem() throws EntityNotFoundException {
        ItemEntity item = new ItemEntity(2L, "firstItem", 25.0, "452353GF");
        ItemDto itemDto = item.toDto();
        Mockito.when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        ItemDto result = itemService.deleteItem(2L);
        Assertions.assertThat(result).isEqualTo(itemDto);
    }

    @Test
    public void listEntityToListDto() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ItemEntity item = new ItemEntity(2L, "firstItem", 25.0, "452353GF");
        List<ItemEntity> listOfItems = Arrays.asList(item);
        ItemDto itemDto = new ItemDto(2L, "firstItem", 25.0, "452353GF");
        List<ItemDto> listOfItemsDto = Arrays.asList(itemDto);
        Method declaredMethod = ItemService.class.getDeclaredMethod("listEntityToListDto",
                List.class);
        declaredMethod.setAccessible(true);
        List<ItemDto> result = (List<ItemDto>) declaredMethod.invoke(itemService, listOfItems);
        Assertions.assertThat(result).isEqualTo(listOfItemsDto);
    }

}
