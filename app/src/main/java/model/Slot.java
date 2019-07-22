package model;

public class Slot {
    public static final String TABLE_NAME = "slots";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_ENTRY_TIMESTAMP = "entry_timestamp";
    public static final String COLUMN_EXIT_TIMESTAMP = "exit_timestamp";
    public static final String COLUMN_VEHICLE_TYPE = "vehicle_type";
    public static final String COLUMN_FLOOR_TYPE = "floor_type";
    public static final String COLUMN_SLOT = "slot";

    private int id;
    private String number;
    private String entry_timestamp;
    private String exit_timestamp;
    private String vehicle_type;
    private String floor_type;
    private String slot;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NUMBER + " TEXT,"
                    + COLUMN_ENTRY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_EXIT_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_VEHICLE_TYPE + " TEXT,"
                    + COLUMN_FLOOR_TYPE + " TEXT,"
                    + COLUMN_SLOT + " TEXT"
                    + ")";

    public Slot() {
    }

    public Slot(int id, String number, String entry_timestamp,String exit_timestamp,String vehicle_type,String floor_type,String slot) {
        this.id = id;
        this.number = number;
        this.entry_timestamp = entry_timestamp;
        this.exit_timestamp=exit_timestamp;
        this.vehicle_type=vehicle_type;
        this.floor_type=floor_type;
        this.slot=slot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEntry_timestamp() {
        return entry_timestamp;
    }

    public void setEntry_timestamp(String entry_timestamp) {
        this.entry_timestamp = entry_timestamp;
    }

    public String getExit_timestamp() {
        return exit_timestamp;
    }

    public void setExit_timestamp(String exit_timestamp) {
        this.exit_timestamp = exit_timestamp;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getFloor_type() {
        return floor_type;
    }

    public void setFloor_type(String floor_type) {
        this.floor_type = floor_type;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

}

