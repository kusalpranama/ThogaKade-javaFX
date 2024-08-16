package model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString

public class Customer {

    private String id;
    private String name;
    private String address;
    private String number;
    @Getter
    private LocalDate date;

    public Customer(String id,String name,String address,String number,String title,LocalDate date){
        this.id=id;
        this.name=title+name;
        this.address=address;
        this.number=number;
        this.date=date;

    }
}
