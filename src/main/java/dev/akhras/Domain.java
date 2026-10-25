package dev.akhras;
import jakarta.persistence.*;import jakarta.validation.constraints.*;
public class Domain{
 @Entity public static class Customer{@Id @GeneratedValue public Long id;@NotBlank public String name;@Email @NotBlank public String email;}
 @Entity public static class Policy{@Id @GeneratedValue public Long id;@ManyToOne(optional=false) public Customer customer;@NotBlank @Pattern(regexp="[A-Z]{2}-[0-9]{6}") public String number;@Positive public double coverage;}
 @Entity public static class Claim{@Id @GeneratedValue public Long id;@ManyToOne(optional=false) public Policy policy;@Positive public double amount;@Enumerated(EnumType.STRING) public Status status=Status.SUBMITTED;}
 public enum Status{SUBMITTED,REVIEW,APPROVED,DENIED}
 public static Status transition(Status from,Status to){
  if((from==Status.SUBMITTED&&to==Status.REVIEW)||(from==Status.REVIEW&&(to==Status.APPROVED||to==Status.DENIED)))return to;
  throw new IllegalArgumentException("invalid claim transition");
 }
}
