package bettinger.david.chaintask.service;

import bettinger.david.chaintask.model.ChainTask;
import bettinger.david.chaintask.repository.ChainTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ChainTaskService {

    private static final Logger LOGGER = Logger.getLogger(ChainTaskService.class.getName());
    private final ChainTaskRepository chainTaskRepository;

    public ChainTaskService(ChainTaskRepository chainTaskRepository) {
        this.chainTaskRepository = chainTaskRepository;
    }

    public List<ChainTask> findAll(){
        return chainTaskRepository.findAll();
    }

    public  List<ChainTask> findAll(String filterText){
        if(filterText == null || filterText.isEmpty()) {
            return chainTaskRepository.findAll();
        } else  {
            return  chainTaskRepository.search(filterText);
        }
    }

    public long count() {
        return chainTaskRepository.count();
    }

    public void delete(ChainTask chainTask) {
        chainTaskRepository.delete(chainTask);
    }

    public void save(ChainTask chainTask) {
        if (chainTask == null) {
            LOGGER.log(Level.SEVERE,
                    "ChainTask is null. Are you sure you have connected your form to the application?");
            return;
        }
        chainTaskRepository.save(chainTask);
    }

}
