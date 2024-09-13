package com.globant.academy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Resource {
    private String name;
    private String tradeMark;
    private int stock;
    private int price;
    private String description;
    private String tags;
    private boolean active;
}
