package packages.service;

import org.springframework.stereotype.Service;
import packages.model.Dataset;

import java.util.List;

@Service
public class DatasetService {
    private Dataset dataset;

    public DatasetService(){
        this.dataset = new Dataset();
    }

    public void addNumber(int number){
        dataset.getInputs().add(number);
    }

    public void deleteNumber(int position){
        dataset.getInputs().remove(position);
    }

    public void clear(){
        dataset.getInputs().clear();
    }

    public List<Integer> getData(){
        return dataset.getInputs();
    }

    public boolean update(int number, int position){
        try{
            dataset.getInputs().set(position,number);
            return true;
        } catch (Exception e){
            return false;
        }

    }


}
