package org.pcsoft.framework.jrcp.support.spring.api.types;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class User {
    private String name;
    @EqualsAndHashCode.Include
    private String nick;
}
