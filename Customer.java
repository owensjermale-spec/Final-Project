
public class Customer
{
    private String name;
    private String address;
    private long phone;

    // Constructors
    public Customer() { this("", "", 0000000000); }
    public Customer(String name, String address, long phone)
    {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Getters and Setters
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }
    public long getPhone() { return this.phone; }
    public void setPhone(long phone) { this.phone = phone; }
}