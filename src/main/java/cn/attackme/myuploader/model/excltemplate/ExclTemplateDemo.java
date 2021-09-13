package cn.attackme.myuploader.model.excltemplate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExclTemplateDemo {
    String name;
    int age;
}
