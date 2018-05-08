package fr.cheron.antoine.reactivewebserver.domain;

public class Person {

  private final String id;
  private final String firstName;
  private final String familyName;
  private final int age;

  public Person(String id, String firstName, String familyName, int age) {
    this.id = id;
    this.firstName = firstName;
    this.familyName = familyName;
    this.age = age;
  }

  public String getId() {
    return this.id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getFamilyName() {
    return this.familyName;
  }

  public int getAge() {
    return this.age;
  }

}
