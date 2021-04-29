package com.example.online_test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqCourse {

    private String titleUz;

    private String titleRu;

    private Integer durationTime;

    private String descriptionUz;

    private String descriptionRu;

    private MultipartFile multipartFile;



}
