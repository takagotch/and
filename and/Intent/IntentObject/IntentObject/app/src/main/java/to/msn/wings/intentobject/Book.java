package to.msn.wings.intentobject;

import java.io.Serializable;

public class Book  implements Serializable {
    private String title;
    private String publish;
    private String price;

    public Book(String title, String publish, String price) {
        this.title = title;
        this.publish = publish;
        this.price = price;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getPublish() {
        return this.publish;
    }

    public void setPublish(String publish){
        this.publish = publish;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price){
        this.price = price;
    }

    @Override
    public String toString(){
        return "書名："  + this.title + "\n" + "出版社："+ this.publish + "\n" + "価格：" + this.price;
    }
}
