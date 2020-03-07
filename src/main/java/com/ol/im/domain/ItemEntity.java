package com.ol.im.domain;

import com.ol.im.dto.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "item")
public class ItemEntity {
    @Id
    @GeneratedValue//(strategy = GenerationType.AUTO)
    private Long itemNo;
    private String name;
    private Double amount;
    private String inventoryCode;


    public ItemDto toDto() {
        return new ItemDto(itemNo, name, amount, inventoryCode);
    }

}

