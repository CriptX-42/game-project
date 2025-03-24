package ZZJcrud.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Game {
    Integer id;
    String name;
    int episodes;
    Producer producer;
}
