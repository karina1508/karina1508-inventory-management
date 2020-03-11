package com.ol.im.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ol.im.domain.ItemEntity;
import lombok.Data;


@Data
public class ItemDto {
    private Long itemNo;
    private String name;
    private Double amount;
    private String inventoryCode;

    public ItemEntity toEntity() {
        return new ItemEntity(itemNo, name, amount, inventoryCode);
    }

    @JsonCreator
    public ItemDto(@JsonProperty("itemNo") Long itemNo,
                   @JsonProperty("name") String name,
                   @JsonProperty("amount") Double amount,
                   @JsonProperty("inventoryCode") String inventoryCode
    ) {
        this.itemNo = itemNo;
        this.name = name;
        this.amount = amount;
        this.inventoryCode = inventoryCode;

    }
}
