package com.example.eformation.dtos.PlayList ;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistRequest {
    private Long id;          
    private Long profId;     
    private String title;
    private String description;
}
