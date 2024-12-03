package packages;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Dataset {
    private List<Integer> inputs = new ArrayList<>();

}
