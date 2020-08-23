package bettinger.david.taskchains.model.data;

import lombok.Data;


@Data
public class BaseData {
    private Long id;

    public BaseData(){
        id = null;
    }

    public BaseData(Long id) {
        this.id = id;
    }
}
