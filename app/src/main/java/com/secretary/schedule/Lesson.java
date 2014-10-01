package com.secretary.schedule;

public class Lesson {
    private long id;
    private int number;
    private String name;
    private int teacher_id0;
    private int teacher_id1;
    private int slot0;
    private int slot1;
    private int slot2;
    private int slot3;
    private String turn;
    private String room;

    public Lesson () {}

    public Lesson (int number, String name, int[] teacher_id, int[] slots, String turn, String room) {
        this.number = number;
        this.name = name;
        this.teacher_id0 = teacher_id[0];
        this.teacher_id1 = teacher_id[1];
        this.slot0 = slots[0];
        this.slot1 = slots[1];
        this.slot2 = slots[2];
        this.slot3 = slots[3];
        this.turn = turn;
        this.room = room;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getTeacherId() {
        int teacher_id[] = {teacher_id0, teacher_id1};
        return teacher_id;
    }

    public void setTeacherId(int[] teacher_id) {
        this.teacher_id0 = teacher_id[0];
        this.teacher_id1 = teacher_id[1];

    }


    public int[] getSlots() {
        int slots[] = {slot0, slot1, slot2, slot3};
        return slots;
    }

    public int getSlot0() {
        return slot0;
    }

    public void setSlots(int[] slots) {
        this.slot0 = slots[0];
        this.slot1 = slots[1];
        this.slot2 = slots[2];
        this.slot3 = slots[3];

    }

    public void setSlot0(int slot){
        this.slot0 = slot;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

}