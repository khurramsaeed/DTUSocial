package grp21.dtusocial.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author 1234
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class BaseDTO {

    @Id
    @JsonIgnore
    ObjectId objectId;

    public String getId(){
        return this.objectId==null?null:objectId.toHexString();
    }
    public void setId(String id){
        if(id!=null) this.objectId=new ObjectId(id);
    }
}
