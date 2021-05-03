package com.example.online_test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogRequest {

    private String titleUz;

    private String titleRu;

    private String contentUz;

    private String contentRu;

    private String hashId;

}
