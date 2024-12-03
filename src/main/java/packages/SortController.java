package packages;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/sort")
public class SortController {
    private final DatasetService service;

    @GetMapping()
    public ResponseEntity<Dataset> getData() {
        service.getData();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addNumber(@RequestBody String input) {
        String[] numbers = input.split(",");
        for (String num : numbers) {
            try {
                int number = Integer.parseInt(num.trim());
                service.addNumber(number);
            } catch (NumberFormatException e) {
                e.getMessage();
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }
}