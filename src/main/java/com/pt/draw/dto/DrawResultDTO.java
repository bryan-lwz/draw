package com.pt.draw.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DrawResultDTO {
    Long userId;
    String userName;
    Long prizeId;
    String prizeName;
}
