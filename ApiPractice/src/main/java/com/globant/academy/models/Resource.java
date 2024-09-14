package com.globant.academy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    private String name;
    private String trademark;
    private int stock;
    private double price;
    private String description;
    private String tags;
    private boolean active;
    private String id;
    
    @Override
    public String toString() {
        return "Resource {\n" +
               "  name='" + name + "',\n" +
               "  trademark='" + trademark + "',\n" +
               "  stock=" + stock + ",\n" +
               "  price=" + price + ",\n" +
               "  description='" + description + "',\n" +
               "  tags='" + tags + "',\n" +
               "  active=" + active + ",\n" +
               "  id=" + id + "\n" +
               '}';
    }
    
}




