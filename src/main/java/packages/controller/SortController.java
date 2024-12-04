package packages.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import packages.service.DatasetService;
import packages.service.SortService;

import java.util.List;

@RestController
@RequestMapping("/sort")
public class SortController {

    public SortController() {
    }

    @Autowired
    private SortService sortService;
    public SortController(SortService sortService) {
        this.sortService = sortService;
    }

    @Autowired
    private DatasetService service;
    public SortController(DatasetService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Integer>> getData() {
        List<Integer> data = service.getData();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addNumber(@RequestBody String input) {
        String [] numbers = input.trim().split(",");
        for (String num : numbers) {
            try {
                int number = Integer.parseInt(num);
                service.addNumber(number);
            } catch (NumberFormatException e) {
                e.getMessage();
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/clear")
    public ResponseEntity<Void> clearData(){
        service.clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/remove/{pos}")
    public ResponseEntity<Void> delete(@PathVariable("pos")  Integer pos){
        service.deleteNumber(pos);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/update/{pos}")
    public ResponseEntity<Void> update (@PathVariable("pos")  Integer pos, @RequestBody Integer number){
        service.update(number,pos);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/heap")
    public ResponseEntity<List<Integer>> heapsort(){
        sortService.heapSort(service.getData());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/radix")
    public ResponseEntity<List<Integer>> radix(){
        return new ResponseEntity<>( sortService.radixSort(service.getData()), HttpStatus.OK);
    }

    @PostMapping("/bucket")
    public ResponseEntity<List<Integer>> bucket (){
        return new ResponseEntity<>( sortService.bucketSort(service.getData()), HttpStatus.OK);
    }

    @PostMapping("/merge")
    public ResponseEntity<List<Integer>> merge(){
        sortService.mergeSort(service.getData());
        return new ResponseEntity<>( sortService.mergeSort(service.getData()), HttpStatus.OK);
    }


    @PostMapping("/quick")
    public ResponseEntity<List<Integer>> quick(){
        return new ResponseEntity<>( sortService.quickSort(service.getData()), HttpStatus.OK);
    }

}