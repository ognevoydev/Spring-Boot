package com.ognevoydev.quisell.model.dto;

public record PostUpdateDTO(
        String title,
        String description,
        Integer price,
        Boolean used){}