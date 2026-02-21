package org.example.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "idcard")
public class IDCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cardNumber;

    public IDCard(){}

    public IDCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @OneToOne(mappedBy = "idcard")
    private Student student;

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
