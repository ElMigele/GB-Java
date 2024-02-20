import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

class PhoneBook {
  private Boolean ConsistNumCheck; // для дополнительной проверки ConsistNum
  private static HashMap<String, ArrayList<String>> phoneBook = new HashMap<>();
  
  public PhoneBook(){
  ConsistNumCheck = false;
  }
  public PhoneBook(Boolean noDublicate){
    ConsistNumCheck = noDublicate;
  }
    
  // Метод на наличие телефона в книге (на случай, если для книги действует правило:
  // один номер телефона может быть только у одного пользователя)
  private Boolean ConsistNum(String phoneNum){
      for (var entry : phoneBook.entrySet()){
          if (entry.getValue().contains(phoneNum)){
              System.out.println("Данный телефон уже есть в списке контактов! (контакт:" + entry.getKey() + ")");
              return true;
          }
      }
      return false;
  }

  public void add(String name, String phoneNum) {
    if (ConsistNumCheck)
      if (ConsistNum(phoneNum))
        return;

    for (var entry : phoneBook.entrySet()){
      if (entry.getKey().equals(name)) {
        if (entry.getValue().contains(phoneNum)){
          System.out.println("Попытка добавить пользователю номер, который уже у него есть!");
          return;
        }
        ArrayList<String> x = entry.getValue();
        x.add(phoneNum);
        phoneBook.put(name, x);
        return;
      }
    }
    ArrayList<String> x = new ArrayList<String>();
    x.add(phoneNum);
    phoneBook.put(name, x);
  }

  public ArrayList<String> find(String name) {
    return phoneBook.get(name) == null
      ? new ArrayList<String>()
      : phoneBook.get(name);
  }

  public static HashMap<String, ArrayList<String>> getPhoneBook() {
    List<Entry<String, ArrayList<String>>> list = new ArrayList<>(phoneBook.entrySet());
    int stop = 0;      
    Entry<String, ArrayList<String>> temp;

    for (int i = 0; i < list.size(); i++){
      stop = 0;
      for(int j=1; j < (list.size() - i); j++){  
        if(list.get(j - 1).getValue().size() > list.get(j).getValue().size()){  
          temp = list.get(j - 1);  
          list.set(j - 1, list.get(j));
          list.set(j, temp);  
        } else {
          stop++; 
            if (stop == list.size() - i - 1) {
            //list.stream().collect(Collectors.toMap(, item -> item));
          }
        }
      }
    }
    HashMap<String, ArrayList<String>> res = new HashMap<>();
    for (int i = 0; i < list.size(); i++){
      res.put(list.get(i).getKey(), list.get(i).getValue());
    }
    return res;
  }
}

public class TeleTest {
    public static void main(String[] args) {
        PhoneBook myPhoneBook = new PhoneBook();
        
        // PhoneBook myPhoneBook = new PhoneBook(true); // случай с недопустимостью дубликатов номеров

        myPhoneBook.add("Lorens", "0000000");
        myPhoneBook.add("Lorens", "0000000");

        myPhoneBook.add("Mama", "0000001");

        myPhoneBook.add("Papa", "0000002");
        myPhoneBook.add("Papa", "0000003");
        myPhoneBook.add("Papa", "0000003");

        myPhoneBook.add("Vera", "0000004");
        myPhoneBook.add("Vera", "0000005");
        myPhoneBook.add("Vera", "0000006");
        myPhoneBook.add("Vera", "0000007");


        System.out.println(myPhoneBook.find("Lorens"));
        System.out.println(PhoneBook.getPhoneBook());
        System.out.println(myPhoneBook.find("Me"));
    }
}