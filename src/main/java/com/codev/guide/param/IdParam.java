package com.codev.guide.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class IdParam implements Serializable {

    @NotNull
    private Long id;
}
