import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static List<List<Numbers>> findNumberGroups(HashSet<Numbers> numbers){
        class NewNumberElement{
            private Long number;
            private int column;

            private NewNumberElement(Long number, int column){
                this.column=column;
                this.number=number;
            }
        }
        if (numbers==null){
            return Collections.emptyList();
        }

        List<List<Numbers>> numberGroups = new ArrayList<>();

        if (numbers.size()<2){
            List<Numbers> num = new ArrayList();
            num.addAll(numbers);
            numberGroups.add(num);
            return numberGroups;
        }

        List<Map<Long, Integer>> columns = new ArrayList<>();
        Map<Integer, Integer> unitedGroups = new HashMap<>();
        Iterator iterator = numbers.iterator();
        while (iterator.hasNext()){
            TreeSet<Integer> groupsWithSameElements = new TreeSet<>();
            List<NewNumberElement> newElements = new ArrayList<>();
            Numbers num = (Numbers) iterator.next();
            Long[] numArray = new Long[3];
            if (num.getNum1()!=null) numArray[0]=num.getNum1();
            if (num.getNum2()!=null) numArray[1]=num.getNum2();
            if (num.getNum3()!=null) numArray[2]=num.getNum3();
            for (int i=0; i<numArray.length; i++){
                if (numArray[i]!=null){
                    Long n = numArray[i];
                    if (columns.size()==i) columns.add(new HashMap<>());
                    Map<Long, Integer> currCol = columns.get(i);
                    Integer elementGroupNumber = currCol.get(n);
                    if (elementGroupNumber != null){
                        while (unitedGroups.containsKey(elementGroupNumber)){
                            elementGroupNumber = unitedGroups.get(elementGroupNumber);
                        }
                        groupsWithSameElements.add(elementGroupNumber);
                    } else{
                        newElements.add(new NewNumberElement(n,i));
                    }
                }
            }
            int groupNumber;
            if (groupsWithSameElements.isEmpty()){
                numberGroups.add(new ArrayList<>());
                groupNumber = numberGroups.size()-1;
            } else {
                groupNumber=groupsWithSameElements.first();
            }

            for (NewNumberElement newNumberElement : newElements){
                columns.get(newNumberElement.column).put(newNumberElement.number, groupNumber);
            }

            for (int matchedGroupNumber : groupsWithSameElements){
                if (matchedGroupNumber!=groupNumber){
                    unitedGroups.put(matchedGroupNumber,groupNumber);
                    numberGroups.get(groupNumber).addAll(numberGroups.get(matchedGroupNumber));
                    numberGroups.set(matchedGroupNumber, null);
                }
            }
            numberGroups.get(groupNumber).add(num);
        }
        numberGroups.removeAll(Collections.singleton(null));
        return numberGroups;
    }

    public static int countChar(String str, char c)
    {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {
            char iChar = str.charAt(i);
            if (iChar==c)count++;
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("lng.csv"));
        String line;
        Scanner scanner;
        int index = 0;
        Set<Numbers> numbersList = new HashSet<>();
        while ((line = reader.readLine()) != null) {
            Numbers numbers = new Numbers();
            scanner = new Scanner(line);
            scanner.useDelimiter(";");
            try{
                boolean flag = true;
                while (scanner.hasNext()) {
                    String data = scanner.next();
                    int count = countChar(data,(char)34);
                    int index1, index2;
                    Long number;
                    if (count==2){
                        index1 = data.indexOf("\"");
                        index2 = data.indexOf("\"", index1+1);
                        if (index2-index1>1){
                            String parsedNumber = data.substring(index1+1,index2);
                            try {
                                number = Long.parseLong(parsedNumber);
                                if (index == 0){
                                    numbers.setNum1(number);
                                } else if (index == 1){
                                    numbers.setNum2(number);
                                } else if (index == 2){
                                    numbers.setNum3(number);
                                }
                            } catch (Exception e){
                                flag=false;
                            }
                        } else if (index2-index1==1){
                            if (!data.equals("\"\"")) flag=false;
                        }
                    } else flag=false;
                    index++;
                }
                if (index!=3) flag=false;
                index = 0;
                if (flag) numbersList.add(numbers);
            } catch (Exception e){

            }
        }
        reader.close();
        List<List<Numbers>> list;
        list = findNumberGroups((HashSet<Numbers>) numbersList);
        Comparator<List<Numbers>> comparator = (o1, o2) -> {
            List<Numbers> list1 = o1;
            List<Numbers> list2 = o2;
            return -list1.size()+list2.size();
        };
        Collections.sort(list,comparator);
        FileWriter writer = new FileWriter("a.csv", false);
        for (int i =0; i< list.size(); i++){
            writer.append("Группа "+i+"\n");
            for (int j=0; j<list.get(i).size(); j++){
                writer.append(list.get(i).get(j).toString()+"\n");
                writer.flush();
            }
        }
    }
}
