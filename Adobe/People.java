package Jack2025.Adobe;

import java.util.HashSet;

public class People {

    private String firstName;
    private String lastName;

    public People(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "(" + firstName + ", " + lastName + ")";
    }

    public static void main(String[] args)
    {
        HashSet<People> hashSet = new HashSet<>();
        hashSet.add(new People("Jack", "Lian"));
        hashSet.add(new People("River", "Lian"));
        hashSet.add(new People("Jack", "Lian"));

        System.out.println(hashSet.toString());

    }
}
