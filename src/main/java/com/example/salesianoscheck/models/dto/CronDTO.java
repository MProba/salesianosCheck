package com.example.salesianoscheck.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CronDTO implements Serializable {
    private Long id;
    private String name;
    private Integer status;
}
