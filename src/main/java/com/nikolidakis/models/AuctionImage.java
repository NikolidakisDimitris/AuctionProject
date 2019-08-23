package com.nikolidakis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "Images")
@NoArgsConstructor
@AllArgsConstructor
public class AuctionImage {

    private static final String FILE_PATH = "/images";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_name")
    @NotBlank
    private String imageName;

    @Column(name = "image_path")
    @NotBlank
    private String imagePath;

}
