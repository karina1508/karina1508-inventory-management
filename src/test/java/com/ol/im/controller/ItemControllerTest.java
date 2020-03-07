package com.ol.im.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ol.im.dto.ItemDto;
import com.ol.im.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ItemControllerTest {
    @Mock
    private ItemService itemService;
    private ItemController itemController;
    private MockMvc mockMvc;
    private WebTestClient webClient;
    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        itemController = new ItemController(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();

    }

    @Test
    public void getAllItems() throws Exception {
        ItemDto item = new ItemDto(2L, "firstItem", 25.0, "452353GF");
        List<ItemDto> listOfItems = Arrays.asList(item);
        Mockito.when(itemService.getAllItems()).thenReturn(listOfItems);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemNo").value(item.getItemNo()));
    }

    @Test
    public void getItemByItemNumber() throws Exception {
        ItemDto item = new ItemDto(2L, "firstItem", 25.0, "452353GF");
        Mockito.when(itemService.getItemByItemNumber(2L)).thenReturn(item);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/item-by-number/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemNo").value(item.getItemNo()));
    }

    @Test
    public void withdrawalItemToStock() throws Exception {
        ItemDto item = new ItemDto(2L, "firstItem", 25.0, "452353GF");
        Mockito.when(itemService.changeAmountOfItem(2L, 10.0, "withdrawal")).thenReturn(item);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/withdrawal-item/2/10.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemNo").value(item.getItemNo()));
    }

    @Test
    public void depositItemToStock() throws Exception {
        ItemDto item = new ItemDto(2L, "firstItem", 25.0, "452353GF");
        Mockito.when(itemService.changeAmountOfItem(2L, 10.0, "deposit")).thenReturn(item);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/deposit-item/2/10.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemNo").value(item.getItemNo()));
    }

    @Test
    public void addItem() throws Exception {
        ItemDto item = new ItemDto(2L, "firstItem", 25.0, "452353GF");
        Mockito.when(itemService.addItem(item)).thenReturn(item);
        String itemForPost = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(item);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/item")
                        .content(itemForPost)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemNo").value(item.getItemNo()));
    }

    @Test
    public void deleteItem() throws Exception {
        ItemDto item = new ItemDto(2L, "firstItem", 25.0, "452353GF");
        Mockito.when(itemService.deleteItem(2L)).thenReturn(item);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/item/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemNo").value(item.getItemNo()));
    }

}
