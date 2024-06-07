package com.luizalabs.fileconverter.infrastructure.mongodb.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "User")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDocument {
    private Long userId;
    private String name ;
}
